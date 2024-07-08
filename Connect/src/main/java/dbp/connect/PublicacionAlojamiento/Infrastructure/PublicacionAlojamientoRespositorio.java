package dbp.connect.PublicacionAlojamiento.Infrastructure;

import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PublicacionAlojamientoRespositorio extends JpaRepository<PublicacionAlojamiento, Long> {
    @Query("SELECT p FROM PublicacionAlojamiento p WHERE p.promedioRating BETWEEN :minRating AND :maxRating")
    List<PublicacionAlojamiento> findByCalificacionBetween(@Param("minRating") Integer minRating, @Param("maxRating") Integer maxRating);

    /*Page<PublicacionAlojamiento> findByH3IndexIn(List<Long> h3Indices, Pageable pageable);
*/}
