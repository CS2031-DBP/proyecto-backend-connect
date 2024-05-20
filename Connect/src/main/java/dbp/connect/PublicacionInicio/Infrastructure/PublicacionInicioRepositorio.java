package dbp.connect.PublicacionInicio.Infrastructure;

import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionInicioRepositorio extends JpaRepository<PublicacionInicio, Long> {
}
