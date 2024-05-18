package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name="chat_id")
    private ChatIndividual chat;
    @ManyToOne
    @JoinColumn(name="autor_id")
    private User autor;
    private String cuerpo;
    private StatusMensaje statusMensaje;
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL)
    private List<MultimediaMensajeIndividual> archivosMultimedia;




}
