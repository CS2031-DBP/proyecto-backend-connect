package dbp.connect.MensajeIndividual.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MensajeIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @NonNull
    private String chatId;
    @NonNull
    private String senderId;
    @NonNull
    private String recipientId;
    private String content;
    private Date timestamp;

}
