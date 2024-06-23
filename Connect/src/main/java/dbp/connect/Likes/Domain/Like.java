package dbp.connect.Likes.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandomParameters;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Like implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_like")
    private ZonedDateTime fechaLike;

    @ManyToOne
    @JsonIgnoreProperties("likes")
    private PublicacionInicio publicacionInicio;
    @ManyToOne
    @JsonIgnoreProperties("usuarioLikes")
    private User usuarioLike;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Like like = (Like) o;
        if (like.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), like.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + getId() +
                ", fechaLike='" + getFechaLike() + "'" +
                "}";
    }

}
