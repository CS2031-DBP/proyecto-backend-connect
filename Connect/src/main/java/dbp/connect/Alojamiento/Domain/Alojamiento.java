package dbp.connect.Alojamiento.Domain;

import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import dbp.connect.TipoMoneda;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name ="estado", nullable = false)
    private Estado estado;
    @Column(name="latitude",nullable = false)
    private Double latitude;
    @Column(name="longitud",nullable = false)
    private Double longitude;
    @Column(name="ubicacion",nullable = false)
    private String ubicacion;
    @Column(name ="fechaPublicacion", nullable = false)
    private LocalDateTime fechaPublicacion;
    @Column(name="descripcion", nullable = false)
    private String descripcion;
    @Column(name="precio", nullable = false)
    private Double precio;
    @Column(name="tipoMoneda", nullable = false)
    private TipoMoneda tipoMoneda;
    @OneToMany(mappedBy = "alojamiento", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AlojamientoMultimedia> alojamientoMultimedia = new ArrayList<>();

    @OneToOne(mappedBy = "alojamientoP")
    private PublicacionAlojamiento publicacionAlojamiento;

    @ManyToOne
    @JoinColumn(name = "propietario_id", referencedColumnName = "id")
    private User propietario;
}
