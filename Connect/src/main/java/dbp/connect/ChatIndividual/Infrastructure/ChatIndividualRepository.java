package dbp.connect.ChatIndividual.Infrastructure;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatIndividualRepository extends JpaRepository<ChatIndividual, String> {
    Optional<ChatIndividual> findBySenderIdAndRecipientId(String senderId, String recipientId);


}
