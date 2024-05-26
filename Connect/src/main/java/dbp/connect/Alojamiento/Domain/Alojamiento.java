package dbp.connect.Alojamiento.Domain;

import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne()
    @JoinColumn(name="propietario_Id", nullable = false)
    private User propietario;
    @JoinColumn(name ="estado", nullable = false)
    private Estado estado;
    @Column(name="latitude",nullable = false)
    private Double latitude;
    @Column(name="longitud",nullable = false)
    private Double longitude;
    @Column(name ="fechaPublicacion", nullable = false)
    private LocalDateTime fechaPublicacion;
    @Column(name="descripcion", nullable = false)
    private String descripcion;
    @Column(name="precio", nullable = false)
    private Double precio;
    @OneToMany(mappedBy = "alojamiento", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AlojamientoMultimedia> alojamientoMultimedia = new ArrayList<>();
    public void agregarArchivoMultimedia(AlojamientoMultimedia archivoMultimedia) {
        archivoMultimedia.setAlojamiento(this);
        this.alojamientoMultimedia.add(archivoMultimedia);
    }
    @OneToOne(mappedBy = "alojamientoP")
    private PublicacionAlojamiento publicacionAlojamiento;



    public void removerArchivoMultimedia(AlojamientoMultimedia archivoMultimedia) {
        if (archivoMultimedia != null) {
            archivoMultimedia.setAlojamiento(null);
            this.alojamientoMultimedia.remove(archivoMultimedia);
        }
    }

}
