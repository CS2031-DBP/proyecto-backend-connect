package dbp.connect.PublicacionInicioMultimedia.Domain;

import dbp.connect.ComentariosMultimedia.Domain.Multimedia;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PublicacionInicioMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] contenido;
    private Multimedia tipo;
    private String tipoConte;
    @ManyToOne
    @JoinColumn(name="publicacionInicio_id")
    private PublicacionInicio publicacionInicio;


}
