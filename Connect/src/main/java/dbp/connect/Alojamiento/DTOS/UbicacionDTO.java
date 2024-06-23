package dbp.connect.Alojamiento.DTOS;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class UbicacionDTO {
    private Double latitude;
    private Double longitude;
    @NotNull
    private String ubicacion;
}
