package cmc.service;


import cmc.domain.*;
import cmc.dto.response.AvatarCheckedTodoResponseDto;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import cmc.repository.*;
import cmc.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvatarService {
    private final TodoRepository todoRepository;
    private final CheckedTodoRepository checkedTodoRepository;
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final WorldRepository worldRepository;
    private final WorldAvatarRepository worldAvatarRepository;
    private final WordtodayRepository wordtodayRepository;
    private final S3Util s3Util;

    @Transactional
    public void saveAvatar(Long userId, String avatarName, String avatarMessage, MultipartFile file) {

        String imgUrl = s3Util.upload(file, "avatar");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Avatar avatar = Avatar.builder()
                .avatarName(avatarName)
                .avatarMessage(avatarMessage)
                .avatarImg(imgUrl)
                .user(user)
                .build();

        avatarRepository.save(avatar);
    }

    public List<World> getWorldsByAvatar(Long avatarId) {
        return worldRepository.findWorldWithAvatar(avatarId);
    }

    @Transactional
    public void updateAvatarImg(Long avatarId, MultipartFile file) {

        String imgUrl = s3Util.upload(file, "avatar");

        Avatar avatar = getAvatarByAvatarId(avatarId);

        avatar.setAvatarImg(imgUrl);
    }

    @Transactional
    public void updateAvatarInfo(Long avatarId, String avatarName, String avatarMessage) {

        Avatar avatar = getAvatarById(avatarId);

        avatar.setAvatarName(avatarName);
        avatar.setAvatarMessage(avatarMessage);
    }

    public Avatar getAvatarByAvatarId(Long avatarId) {
        return getAvatarById(avatarId);
    }

    @Transactional
    public void deleteAvatarById(Long avatarId) {
        Avatar avatar = getAvatarById(avatarId);
        avatarRepository.delete(avatar);
    }

    @Transactional
    public void enterWorld(Long avatarId, Long worldId) {

        worldAvatarRepository.findByAvatarIdAndWorldId(avatarId, worldId).ifPresent(s -> {
            throw new BusinessException(ErrorCode.DUPLICATED_AVATAR_WORLD_ENTER);
        });

        World world = getWorldById(worldId);

        Avatar avatar = getAvatarById(avatarId);

        WorldAvatar worldAvatar = WorldAvatar.builder()
                .world(world)
                .avatar(avatar)
                .build();

        worldAvatarRepository.save(worldAvatar);
    }

    @Transactional
    public void quitWorld(Long avatarId, Long worldId) {
        WorldAvatar worldAvatar = getWorldAvatarByAvatarIdAndWorldId(avatarId, worldId);

        worldAvatarRepository.delete(worldAvatar);
    }

    @Transactional
    public void checkTodo(Long avatarId, Long worldId, Long todoId) {

        Todo todo = getTodoById(todoId);

        Avatar avatar = getAvatarById(avatarId);

        // 중복 체크 확인
        checkedTodoRepository.findByTodoIdAndAvatarIdToday(todo.getTodoId(), avatar.getAvatarId()).ifPresent(s -> {
            throw new BusinessException(ErrorCode.DUPLICATED_TODO_CHECK);
        });


        CheckedTodo checkedTodo = CheckedTodo.builder()
                .todo(todo)
                .avatar(avatar)
                .build();

        avatar.getCheckedTodos().add(checkedTodo);
        checkedTodo.setAvatar(avatar);
    }

    @Transactional
    public void uncheckTodo(Long avatarId, Long worldId, Long todoId) {
        Todo todo = getTodoById(todoId);

        Avatar avatar = getAvatarById(avatarId);

        World world = getWorldById(worldId);

        WorldAvatar worldAvatar = getWorldAvatarByAvatarIdAndWorldId(avatarId, worldId);

        CheckedTodo checkedTodo = getCheckedTodoByTodoAndAvatar(todo, avatar);

        checkedTodoRepository.delete(checkedTodo);
    }

    @Transactional
    public void createWordtoday(Long avatarId, Long worldId, String wordtodayContent) {
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow(() -> {
            throw new BusinessException(ErrorCode.AVATAR_NOT_FOUND);
        });

        Wordtoday wordtoday = Wordtoday.builder()
                .avatar(avatar)
                .wordtodayContent(wordtodayContent)
                .build();

        wordtodayRepository.save(wordtoday);
    }

    public Wordtoday getWordtoday(Long avatarId, Long worldId) {

        return wordtodayRepository.findWordtodayByAvatarIdAndWorldId(avatarId, worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORDTODAY_NOT_FOUND));
    }

    private Avatar getAvatarById(Long avatarId) {
        return avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));
    }

    private World getWorldById(Long worldId) {
        return worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));
    }

    private Todo getTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TODO_NOT_FOUND));
    }

    private WorldAvatar getWorldAvatarByAvatarIdAndWorldId(Long avatarId, Long worldId) {
        return worldAvatarRepository.findByAvatarIdAndWorldId(avatarId, worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_AVATAR_NOT_FOUND));
    }

    private CheckedTodo getCheckedTodoByTodoAndAvatar(Todo todo, Avatar avatar) {
        return checkedTodoRepository.findByTodoIdAndAvatarIdToday(todo.getTodoId(), avatar.getAvatarId())
                .orElseThrow(() -> new BusinessException(ErrorCode.TODO_CHECKED_NOT_FOUND));
    }

    public List<AvatarCheckedTodoResponseDto> getCheckedTodoOfAvatar(Long avatarId, Long worldId) {
        return checkedTodoRepository.getCheckedTodoTodayByWorldIdAndAvatarId(avatarId, worldId);
    }

    public List<CheckedTodo> getAllCheckedTodoOfAvatarForMonth(Long avatarId) {
        return checkedTodoRepository.getAllCheckedTodoOfAvatarForMonth(avatarId);
    }
}
