package cmc.service;


import cmc.domain.*;
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

        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        avatar.setAvatarImg(imgUrl);

        avatarRepository.save(avatar);
    }

    @Transactional
    public void updateAvatarInfo(Long avatarId, String avatarName, String avatarMessage) {

        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        avatar.setAvatarName(avatarName);
        avatar.setAvatarMessage(avatarMessage);

        avatarRepository.save(avatar);
    }

    public Avatar getAvatarByAvatarId(Long avatarId) {
        return avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));
    }

    @Transactional
    public void deleteAvatarById(Long avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));
        avatarRepository.delete(avatar);
    }

    @Transactional
    public void enterWorld(Long avatarId, Long worldId) {

        // 중복된 캐릭터 세계관 참여가 존재하는지 확인
        if(worldAvatarRepository.findByAvatarIdAndWorldId(avatarId, worldId).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATED_AVATAR_WORLD_ENTER);
        }

        World world = worldRepository.findById(worldId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        Avatar avatar = avatarRepository.findById(avatarId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        WorldAvatar worldAvatar = WorldAvatar.builder()
                .world(world)
                .avatar(avatar)
                .build();

        worldAvatarRepository.save(worldAvatar);
    }

    @Transactional
    public void quitWorld(Long avatarId, Long worldId) {
        WorldAvatar worldAvatar = worldAvatarRepository.findByAvatarIdAndWorldId(avatarId, worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_AVATAR_NOT_FOUND));

        worldAvatarRepository.delete(worldAvatar);
    }

    @Transactional
    public void checkTodo(Long avatarId, Long worldId, Long todoId) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TODO_NOT_FOUND));

        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        World world = worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        WorldAvatar worldAvatar = worldAvatarRepository.findByAvatarIdAndWorldId(avatarId, worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_AVATAR_NOT_FOUND));

        // 중복 체크 확인
        if(checkedTodoRepository.findByTodoIdAndWorldAvatarId(todo.getTodoId(), worldAvatar.getWorldAvatarId()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATED_TODO_CHECK);
        }

        CheckedTodo checkedTodo = CheckedTodo.builder()
                .todo(todo)
                .worldAvatar(worldAvatar)
                .build();

        checkedTodoRepository.save(checkedTodo);
    }

    @Transactional
    public void uncheckTodo(Long avatarId, Long worldId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TODO_NOT_FOUND));

        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        World world = worldRepository.findById(worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_NOT_FOUND));

        WorldAvatar worldAvatar = worldAvatarRepository.findByAvatarIdAndWorldId(avatarId, worldId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORLD_AVATAR_NOT_FOUND));

        CheckedTodo checkedTodo = checkedTodoRepository.findByTodoIdAndWorldAvatarId(todo.getTodoId(), worldAvatar.getWorldAvatarId())
                .orElseThrow(() -> new BusinessException(ErrorCode.TODO_CHECKED_NOT_FOUND));

        checkedTodoRepository.delete(checkedTodo);
    }


//    public void getTodosOfAvatarToday(Long avatarId, Long worldId) {
//        return
//    }
}
