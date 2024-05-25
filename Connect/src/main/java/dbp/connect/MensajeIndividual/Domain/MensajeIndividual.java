package dbp.connect.MensajeIndividual.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.Likes.Domain.Like;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @JsonIgnoreProperties("mensajes")
    private ChatIndividual chat;
    @ManyToOne
    @JoinColumn(name="autor_Id", nullable = false)
    private User autor;
    @JoinColumn(name="cuerpo", nullable = false)
    private String cuerpo;
    @JoinColumn(name="status")
    private StatusMensaje statusMensaje;

    private LocalDateTime fechaCreacion;
    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL,orphanRemoval = true )
    private List<MultimediaMensajeIndividual> archivosMultimedia = new ArrayList<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MensajeIndividual mensaje = (MensajeIndividual) o;
        if (mensaje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mensaje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
    public MensajeIndividual addMultimedia(MultimediaMensajeIndividual multimedia) {
        this.archivosMultimedia.add(multimedia);
        multimedia.setMensaje(this);
        return this;
    }
    public MensajeIndividual removeMultimedia(MultimediaMensajeIndividual mensaje) {
        this.archivosMultimedia.remove(mensaje);
        mensaje.setMensaje(null);
        return this;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + getId() +
                ", texto='" + getCuerpo() + "'" +
                ", fechaCreacion='" + getFechaCreacion() + "'" +
                "}";
    }

}
