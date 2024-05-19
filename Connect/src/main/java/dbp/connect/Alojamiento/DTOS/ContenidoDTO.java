package dbp.connect.Alojamiento.DTOS;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ContenidoDTO {
    @NotNull
    @Size(min = 1, max = 255)
    private String descripcion;
}
