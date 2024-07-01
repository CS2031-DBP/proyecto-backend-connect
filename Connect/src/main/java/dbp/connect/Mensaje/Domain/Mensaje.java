package dbp.connect.Mensaje.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dbp.connect.Chat.Domain.Chat;
import dbp.connect.MultimediaMensaje.Domain.MultimediaMensaje;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    private User user;
    @Column(name="cuerpo", nullable = false)
    private String cuerpo;
    @Column(name="status")
    private StatusMensaje status;
    @Column(name="fecha_mensaje")
    private ZonedDateTime fecha_mensaje;
    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL,orphanRemoval = true )
    private List<MultimediaMensaje> multimediaMensaje = new ArrayList<>();



}
