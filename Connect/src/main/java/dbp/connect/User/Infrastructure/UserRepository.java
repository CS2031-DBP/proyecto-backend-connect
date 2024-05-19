package dbp.connect.User.Infrastructure;

import dbp.connect.User.Domain.Status;
import dbp.connect.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByStatus(Status status);

}
