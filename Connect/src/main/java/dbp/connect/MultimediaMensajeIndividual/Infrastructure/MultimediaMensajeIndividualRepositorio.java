package dbp.connect.MultimediaMensajeIndividual.Infrastructure;

import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaMensajeIndividualRepositorio extends JpaRepository<MultimediaMensajeIndividual, Long> {
}
