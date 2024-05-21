package dbp.connect.Review.Domain;

import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private User autor;
    @ManyToOne
    @JoinColumn(name = "publicacionA_id",nullable = false)
    private PublicacionAlojamiento publicacionAlojamiento;
    @Column(nullable = false)
    private int calificacion;
    @Column(nullable = false)
    private String comentario;
    @Column(nullable = false)
    private LocalDateTime fecha;
    public void setCalificacion(int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        this.calificacion = calificacion;
    }
}
