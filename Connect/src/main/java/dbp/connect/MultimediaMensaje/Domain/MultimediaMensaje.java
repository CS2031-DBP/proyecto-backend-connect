package dbp.connect.MultimediaMensaje.Domain;

import dbp.connect.Mensaje.Domain.Mensaje;
import dbp.connect.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class MultimediaMensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    @ManyToOne
    @JoinColumn(name = "mensaje_id")
    private Mensaje mensaje;

    private String url;
    private String tipoContenido;
    private Tipo tipo;
}
