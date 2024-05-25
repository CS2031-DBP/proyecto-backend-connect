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
    @JoinColumn(name = "autorR_id", nullable = false)
    private User autorR;

    @ManyToOne
    @JoinColumn(name = "publicacionAlojamiento_id",nullable = false)
    private PublicacionAlojamiento publicacionAlojamiento;

    @Column(name="calificacion",nullable = false)
    private Integer calificacion;
    @Column(name="comentario",nullable = false)
    private String comentario;
    @Column(name="fecha",nullable = false)
    private LocalDateTime fecha;
}
