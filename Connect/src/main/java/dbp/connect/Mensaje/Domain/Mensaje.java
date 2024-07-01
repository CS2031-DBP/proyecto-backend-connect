package dbp.connect.Mensaje.Domain;

import dbp.connect.Chat.Domain.Chat;
import dbp.connect.MultimediaMensaje.Domain.MultimediaMensaje;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="chat_id", nullable = false)
    private Chat chat;
    @ManyToOne
    @JoinColumn(name="autor_id", nullable = false)
    private User autor;
    @Column(name="cuerpo", nullable = false)
    private String cuerpo;
    @Column(name="status")
    private StatusMensaje status;
    @Column(name="fecha_mensaje")
    private ZonedDateTime fecha_mensaje;
    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL,orphanRemoval = true )
    private List<MultimediaMensaje> multimediaMensaje = new ArrayList<>();



}
