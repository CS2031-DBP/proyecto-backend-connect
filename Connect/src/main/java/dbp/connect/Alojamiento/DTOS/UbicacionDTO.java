package dbp.connect.Alojamiento.DTOS;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UbicacionDTO {
    private Double latitude;
    private Double longitude;
    @NotNull
    private String ubicacion;
}
