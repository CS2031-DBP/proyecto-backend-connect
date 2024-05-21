package dbp.connect.PublicacionAlojamiento.Infrastructure;

import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionAlojamientoRespositorio extends JpaRepository<PublicacionAlojamiento, Long> {
}
