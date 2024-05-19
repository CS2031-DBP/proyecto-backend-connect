package dbp.connect.ChatIndividual.Infrastructure;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ChatIndividualRepository extends JpaRepository<ChatIndividual, Long> {

    ChatIndividual findByUsuarios(Long usuario1, Long usuario2);
    Page<MensajeIndividual> findAllMensajesByChatId(Long chatId, Pageable pageable);
}
