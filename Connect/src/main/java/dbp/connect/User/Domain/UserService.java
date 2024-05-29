package dbp.connect.User.Domain;

import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public void addFriend(Long userId, Long  friendId) {
        User user = userRepository.findById(userId).orElseThrow();
        User friend = userRepository.findById(friendId).orElseThrow();
        user.addFriend(friend);
        userRepository.save(user);
        userRepository.save(friend); // Guardar explícitamente el amigo también
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow();
        User friend = userRepository.findById(friendId).orElseThrow();
        user.removeFriend(friend);
        userRepository.save(user);
        userRepository.save(friend); // Guardar explícitamente el amigo también
    }
}
