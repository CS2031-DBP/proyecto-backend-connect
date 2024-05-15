package dbp.connect.ChatIndividual.Domain;


import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatIndividual {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
}
