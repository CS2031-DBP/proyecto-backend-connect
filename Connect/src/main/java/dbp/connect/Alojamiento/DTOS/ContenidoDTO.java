package dbp.connect.Alojamiento.DTOS;

import jakarta.validation.constraints.NotNull;
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
