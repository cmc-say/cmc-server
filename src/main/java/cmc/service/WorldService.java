package cmc.service;

import cmc.domain.model.OrderType;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import cmc.repository.*;
import cmc.domain.Hashtag;
import cmc.domain.World;
import cmc.domain.WorldHashtag;
import cmc.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorldService {
    private final UserRepository userRepository;
    private final WorldRepository worldRepository;
    private final HashtagRepository hashtagRepository;
    private final WorldHashtagRepository worldHashtagRepository;
    private final RecommendedWorldRepository recommendedWorldRepository;
    private final WorldAvatarRepository worldAvatarRepository;
    private final S3Util s3Util;

    // 세계관 등록
    @Transactional
    public void saveWorld(
            Long tokenUserId,
            MultipartFile file,
            String worldName,
            String worldNotice,
            Integer worldUserLimit,
            String worldPassword,
            List<String> hashtagNames,
            List<String> todoContents) {

        String worldImgUri = s3Util.upload(file, "world");

        List<Hashtag> hashtags = saveHashtagsIfNotExistsByHashtagNames(hashtagNames);

        //TODO: todos 저장
        //Todo todo

        World world = World.builder()
                .worldName(worldName)
                .worldNotice(worldNotice)
                .worldUserLimit(worldUserLimit)
                .worldPassword(worldPassword)
                .worldHostUserId(tokenUserId)
                .worldImg(worldImgUri)
                .worldStartDate(LocalDateTime.now())
                .worldEndDate(LocalDateTime.now().plusMonths(1L))
                .build();

        World savedWorld = worldRepository.save(world);

        List<WorldHashtag> worldHashtagList = hashtags.stream().map(savedHashtag ->
                WorldHashtag.builder()
                        .world(savedWorld)
                        .hashtag(savedHashtag)
                        .build())
                .collect(Collectors.toList());

        worldHashtagRepository.saveAll(worldHashtagList);
    }

    public List<World> getWorldsWithOrder(OrderType orderType) {

        if(orderType == OrderType.RECENT) {
            return worldRepository.getWorldsWithOrderRecent();
        }

        // recent가 아니라면 default로 id asc
        return worldRepository.findAll();
    }

    @Transactional
    public void deleteWorld(Long worldId) {
        worldRepository.deleteById(worldId);
    }

    public World getWorldByWorldId(Long worldId) {
        return worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));
    }

    public List<World> searchWorldByKeyword(String keyword) {
        return worldRepository.searchWorldByWorldNameAndHashtagName(keyword);
    }

    @Transactional
    public void updateWorldInfo(
            Long userId,
            Long worldId,
            String worldName,
            Integer worldUserLimit,
            LocalDateTime worldStartDate,
            LocalDateTime worldEndDate,
            String worldNotice,
            String worldPassword,
            Long worldHostUserId) {

        World world = worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        // 토큰의 유저가 방장이 아닐 경우 Unauthorized
        if (!world.getWorldHostUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        // 설정하려는 user limit가 현재의 세계관 유저 count보다 작을 경우 Bad Request
        if (worldUserLimit < world.getWorldAvatars().size()){
            throw new BusinessException(ErrorCode.WORLD_USER_LIMIT_ERROR);
        }

        World updateWorldInfo = World.builder()
                .worldName(worldName)
                .worldUserLimit(worldUserLimit)
                .worldImg(world.getWorldImg()) // 사진은 원래 그대로
                .worldStartDate(worldStartDate)
                .worldEndDate(worldEndDate)
                .worldNotice(worldNotice)
                .worldPassword(worldPassword)
                .worldHostUserId(worldHostUserId)
                .build();

        worldRepository.save( world.updateWorld(updateWorldInfo) );
    }

    @Transactional
    public void updateWorldImg(Long userId, Long worldId, MultipartFile file) {

        World world = worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        // 토큰의 유저가 방장이 아닐 경우 Unauthorized
        if (!world.getWorldHostUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        String newWorldImgUri = s3Util.upload(file, "world");

        World updateWorldInfo = World.builder()
                .worldName(world.getWorldName())
                .worldUserLimit(world.getWorldUserLimit())
                .worldImg(newWorldImgUri) // 사진만 변경
                .worldStartDate(world.getWorldStartDate())
                .worldEndDate(world.getWorldEndDate())
                .worldNotice(world.getWorldNotice())
                .worldPassword(world.getWorldPassword())
                .worldHostUserId(world.getWorldHostUserId())
                .build();

        worldRepository.save(world.updateWorld(updateWorldInfo));
    }

    @Transactional
    public void updateNewWorldHashtags(Long userId, Long worldId, List<String> hashtagNames) {

        World world = worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        // 토큰의 유저가 방장이 아닐 경우 Unauthorized
        if (!world.getWorldHostUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        List<Hashtag> hashtags = saveHashtagsIfNotExistsByHashtagNames(hashtagNames);

        List<WorldHashtag> worldHashtagList = hashtags.stream().map(savedHashtag ->
                        WorldHashtag.builder()
                                .world(world)
                                .hashtag(savedHashtag)
                                .build())
                .collect(Collectors.toList());

        worldHashtagRepository.saveAll(worldHashtagList);
    }

    // 해시태그가 존재하지 않는다면 저장을 해주고
    // 이미 저장되어 있는 해시태그와 합쳐서 반환
    private List<Hashtag> saveHashtagsIfNotExistsByHashtagNames(List<String> hashtagNames) {

        // savedHashtags : 이미 저장되어 있는 해시태그
        // newHashtags : 저장되어 있지 않은 새로운 해시태그
        List<Hashtag> savedHashtags = hashtagRepository.findHashtagByNameIn(hashtagNames);
        List<Hashtag> newHashtags = hashtagNames.stream()
                .filter(hashtag -> savedHashtags.stream().noneMatch(h -> h.getHashtagName().equals(hashtag)))
                .map(h -> Hashtag.builder()
                        .hashtagName(h)
                        .build())
                .collect(Collectors.toList());

        // 새로운 해시태그 이름이라면 모두 저장
        List<Hashtag> savedNewhashtags = hashtagRepository.saveAll(newHashtags);

        // savedHashtags에 필요한 해시태그 모두 합치기
        savedHashtags.addAll(savedNewhashtags);
        return savedHashtags;
    }

    @Transactional
    public void updateDeletedWorldHashtags(Long userId, Long worldId, List<Long> worldhashtagIds) {

        World world = worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        // 토큰의 유저가 방장이 아닐 경우 Unauthorized
        if (!world.getWorldHostUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        worldHashtagRepository.deleteWorldHashtagByWorldHashtagId(worldhashtagIds);
    }
}
