package dbp.connect.Likes.Infrastructure;

import dbp.connect.Likes.Domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepositorio extends JpaRepository<Like, Long> {
}
