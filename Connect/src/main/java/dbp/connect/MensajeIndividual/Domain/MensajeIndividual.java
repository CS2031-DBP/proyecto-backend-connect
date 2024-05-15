package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MensajeIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatIndividual chat;

    @ManyToOne
    private User sender;

    private String content;
    private Date timestamp;

}
