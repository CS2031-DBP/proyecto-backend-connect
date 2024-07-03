package dbp.connect.FriendRequest.Infrastructure;

import dbp.connect.FriendRequest.Domain.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRequestRepositorio extends JpaRepository<FriendshipRequest, Long> {

}
