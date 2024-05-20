package dbp.connect.Comentarios.DTOS;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ComentarioRespuestaDTO {
    @NotNull
    private String autorNombreCompleto;
    @NotEmpty()
    @Size(min=0, max = 500)
    private String message;
    private byte[] autorImagen;
    private Integer likes;
    private byte[] mulimedia;
}
