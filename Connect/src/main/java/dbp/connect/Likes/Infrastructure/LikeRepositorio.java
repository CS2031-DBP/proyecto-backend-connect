package dbp.connect.Likes.Infrastructure;

import dbp.connect.Likes.Domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface LikeRepositorio extends JpaRepository<Like, Long> {


        List<Like> findByFechaLikeBetween(ZonedDateTime inicio, ZonedDateTime fin);


}
