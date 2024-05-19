package dbp.connect.MultimediaMensajeIndividual.Domain;

import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class MultimediaMensajeIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "mensaje_id")
    private MensajeIndividual mensaje;
    @Lob
    private byte[] contenido;
    private Tipo tipo;
}
