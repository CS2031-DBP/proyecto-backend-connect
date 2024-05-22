package dbp.connect.PublicacionInicioMultimedia.Infrastructure;

import dbp.connect.PublicacionInicioMultimedia.Domain.PublicacionInicioMultimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionInicioMultimediaRepositorio extends JpaRepository<PublicacionInicioMultimedia, Long> {
}
