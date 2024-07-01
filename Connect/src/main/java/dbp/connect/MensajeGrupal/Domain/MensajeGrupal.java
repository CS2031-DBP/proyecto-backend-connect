package dbp.connect.MensajeGrupal.Domain;

import dbp.connect.ChatGrupal.Domain.ChatGrupal;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MensajeGrupal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    /*private String mensaje;
    @ManyToOne
    private User autor;
    @ManyToOne
    @JoinColumn(name="chat_id", nullable = false)
    private ChatGrupal chat;
    private ZonedDateTime fecha;
*/}
