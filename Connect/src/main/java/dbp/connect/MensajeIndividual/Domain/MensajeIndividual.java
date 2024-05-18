package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name="chat_id", nullable = false)
    private ChatIndividual chat;
    @ManyToOne
    @JoinColumn(name="autor_id", nullable = false)
    private User autor;
    @JoinColumn(name="cuerpo")
    private String cuerpo;
    @JoinColumn(name="status",nullable = false)
    private StatusMensaje statusMensaje;
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL,orphanRemoval = true )
    private List<MultimediaMensajeIndividual> archivosMultimedia = new ArrayList<>();




}
