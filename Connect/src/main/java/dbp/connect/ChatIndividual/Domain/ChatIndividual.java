package dbp.connect.ChatIndividual.Domain;


import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @OneToMany(mappedBy = "chat")
    private List<MensajeIndividual> mensajes = new ArrayList<>();

}
