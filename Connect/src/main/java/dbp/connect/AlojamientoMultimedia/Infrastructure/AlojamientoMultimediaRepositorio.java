package dbp.connect.AlojamientoMultimedia.Infrastructure;

import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AlojamientoMultimediaRepositorio extends JpaRepository<AlojamientoMultimedia, Long> {
}
