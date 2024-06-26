package dbp.connect.MultimediaMensaje.Infrastructure;

import dbp.connect.MultimediaMensaje.Domain.MultimediaMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaMensajeIndividualRepositorio extends JpaRepository<MultimediaMensaje, Long> {
}
