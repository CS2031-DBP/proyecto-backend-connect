package dbp.connect.Alojamiento.Infrastructure;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlojamientoRepositorio extends JpaRepository<Alojamiento, Long> {
}
