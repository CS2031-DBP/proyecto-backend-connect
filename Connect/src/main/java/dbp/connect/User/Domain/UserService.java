package dbp.connect.User.Domain;

import dbp.connect.User.Infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void saveUser(User user) {
        user.setStatus((Status.ONLINE));
        userRepository.save(user);

    }
    public void disconnect(User user){
        var storeUser= userRepository.findById(user.getId()).
                orElse(null);
        if(storeUser != null){
            storeUser.setStatus((Status.OFFLINE));
            userRepository.save(storeUser);
        }
    }
    public List<User> findfriendsUsers(User user){

        return null;
    }
    public List<User> findConnectedUsers() {
        return userRepository.findAllByStatus(Status.ONLINE);
    }

    public Optional<User> getUserById( Long id) {
        return userRepository.findById(id);
    }
}
