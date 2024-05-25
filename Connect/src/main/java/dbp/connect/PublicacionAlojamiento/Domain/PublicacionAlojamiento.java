package dbp.connect.PublicacionAlojamiento.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Review.Domain.Review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@Entity
public class PublicacionAlojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "alojamientoP_id")
    private Alojamiento alojamientoP;

    @Column(nullable = false)
    private ZonedDateTime fecha;
    @Column(nullable = false)
    private String Titulo;
    @Column
    private Double promedioRating;
    @Column
    private Integer cantidadReseñas;
    @OneToMany(mappedBy = "publicacionAlojamiento",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Review> reviews;
}
