package dbp.connect.ComentariosMultimedia.Domain;

import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.MultimediaMensajeIndividual.Domain.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ComentarioMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long Id;
    @Lob
    private byte[] contenido;
    private MultimediaComentario tipo;
    private String tipoConte;

}
