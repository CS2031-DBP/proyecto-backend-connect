package dbp.connect.ChatIndividual.Domain;


import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
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
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "usuario1_id", nullable = false)
    private User usuario1;
    @ManyToOne
    @JoinColumn(name = "usuario2_id", nullable = false)
    private User usuario2;
    private Date fechaCreacion;

    @OneToMany(mappedBy = "chat")
    private List<MensajeIndividual> mensajes = new ArrayList<>();

}
