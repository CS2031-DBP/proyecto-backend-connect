package dbp.connect.MensajeIndividual.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeIndividual {
    @Id
    private String id;

    private String chatId;

    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;

}
