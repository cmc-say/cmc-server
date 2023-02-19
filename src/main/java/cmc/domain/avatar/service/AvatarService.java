package cmc.domain.avatar.service;

import cmc.domain.avatar.entity.Avatar;
import cmc.domain.avatar.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public List<Avatar> getCharactersByUserId(Long userId) {
        return avatarRepository.findAllByUser(userId);
    }
}
