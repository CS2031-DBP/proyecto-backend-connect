package dbp.connect.MultimediaMensajeIndividual.Domain;

import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class MultimediaMensajeIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    @ManyToOne
    @JoinColumn(name = "mensaje_id")
    private MensajeIndividual mensaje;
    @Lob
    private byte[] contenido;
    private String tipoContenido;
    private Tipo tipo;
}
