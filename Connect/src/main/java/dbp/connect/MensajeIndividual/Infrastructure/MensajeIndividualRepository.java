package dbp.connect.MensajeIndividual.Infrastructure;

import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.User.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MensajeIndividualRepository extends JpaRepository<MensajeIndividual, Long> {
    Page<MensajeIndividual> findByChatId(Long chatId, Pageable pageable);
    Optional<MensajeIndividual> findByAutor(User user);

}

