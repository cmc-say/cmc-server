package cmc.domain.world.service;

import cmc.domain.world.dto.response.GetWorldsByAvatarResponse;
import cmc.domain.avatar.repository.WorldAvatarRepository;
import cmc.domain.world.entity.Hashtag;
import cmc.domain.world.entity.World;
import cmc.domain.world.entity.WorldHashtag;
import cmc.domain.world.repository.HashtagRepository;
import cmc.domain.world.repository.RecommendedWorldRepository;
import cmc.domain.world.repository.WorldHashtagRepository;
import cmc.domain.world.repository.WorldRepository;
import cmc.global.utils.S3Util;
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

        // savedHashtags : 이미 저장되어 있는 해시태그
        // newHashtags : 저장되어 있지 않은 새로운 해시태그
        List<Hashtag> savedHashtags = hashtagRepository.findByHashtagNameIn(hashtagNames);
        List<Hashtag> newHashtags = hashtagNames.stream()
                .filter(hashtag -> savedHashtags.stream().noneMatch(h -> h.getHashtagName().equals(hashtag)))
                .map(h ->
                        Hashtag.builder()
                                .hashtagName(h)
                                .build())
                .collect(Collectors.toList());

        // 새로운 해시태그 이름이라면 모두 저장
        List<Hashtag> savedNewhashtags = hashtagRepository.saveAll(newHashtags);

        // savedHashtags에 필요한 해시태그 모두 합치기
        savedHashtags.addAll(savedNewhashtags);

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

        List<WorldHashtag> worldHashtagList = savedHashtags.stream().map(savedHashtag ->
                WorldHashtag.builder()
                        .world(savedWorld)
                        .hashtag(savedHashtag)
                        .build())
                .collect(Collectors.toList());

        worldHashtagRepository.saveAll(worldHashtagList);
    }

    public List<World> getWorldsByAvatar(Long avatarId) {
        return worldAvatarRepository.findWorldWithHashtag(avatarId);
    }
}