package dbp.connect.MensajeIndividual.Infrastructure;

import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeIndividualRepository extends JpaRepository<MensajeIndividual, String> {
    List<MensajeIndividual> findByChatId(String Id);
}
