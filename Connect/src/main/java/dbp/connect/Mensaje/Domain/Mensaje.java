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
    private List<MultimediaMensaje> archivosMultimedia = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mensaje mensaje = (Mensaje) o;
        if (mensaje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mensaje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
    public Mensaje addMultimedia(MultimediaMensaje multimedia) {
        this.archivosMultimedia.add(multimedia);
        multimedia.setMensaje(this);
        return this;
    }
    public Mensaje removeMultimedia(MultimediaMensaje mensaje) {
        this.archivosMultimedia.remove(mensaje);
        mensaje.setMensaje(null);
        return this;
    }

    @Override
    public String toString() {
        return "MensajeController{" +
                "id=" + getId() +
                ", texto='" + getCuerpo() + "'" +
                ", fechaCreacion='" + getFecha_mensaje() + "'" +
                "}";
    }

}
