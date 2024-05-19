package dbp.connect.AlojamientoMultimedia.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.MultimediaMensajeIndividual.Domain.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AlojamientoMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "alojamiento_id")
    private Alojamiento alojamiento;
    @Lob
    private byte[] contenido;
    private Tipo tipo;
    private String tipoContenido;
}
