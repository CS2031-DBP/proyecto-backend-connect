package dbp.connect.ComentariosMultimedia.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ComentarioMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Lob
    private byte[] contenido;
    private Multimedia tipo;
    private String tipoConte;
}
