package dbp.connect.Comentarios.DTOS;

import dbp.connect.MultimediaInicio.Domain.MultimediaInicio;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ComentarioDto {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 2, max = 255)
    private String message;
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    private List<MultimediaInicio> multimedio;
    private List<ComentarioDto> respuestas;
}
