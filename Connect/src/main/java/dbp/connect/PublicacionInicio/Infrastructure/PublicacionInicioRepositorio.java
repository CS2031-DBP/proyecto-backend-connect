package dbp.connect.PublicacionInicio.Infrastructure;

import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionInicioRepositorio extends JpaRepository<PublicacionInicio, Long> {

    Page<PublicacionInicio> findByAutorP_Id(Long usuarioId, Pageable pageable);
}
