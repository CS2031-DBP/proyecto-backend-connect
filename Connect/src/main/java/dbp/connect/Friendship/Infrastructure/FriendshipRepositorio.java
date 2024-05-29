package dbp.connect.Friendship.Infrastructure;

import dbp.connect.Friendship.Domain.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepositorio extends JpaRepository<Friendship, Long> {

}
