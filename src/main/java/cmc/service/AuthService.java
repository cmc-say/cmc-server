package cmc.service;

import cmc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
