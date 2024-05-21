package dbp.connect.PublicacionAlojamiento.DTOS;

import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Data
public class ResponsePublicacionAlojamiento {
    @NotNull
    private Long Id;
    @NotNull
    @Size(min = 1, max = 200)
    private String Titulo;
    @NotNull
    @Size(min = 1, max = 1000)
    private String Descripcion;
    private List<AlojamientoMultimedia> alojamientoMultimedia;
    @NotNull
    private String autorFullName;
    private byte[] autorPhoto;
    private int cantidadReviews;
    private Double promedioRating;
    @NotNull
    private Double latitue;
    @NotNull
    private Double longitud;
    @NotNull
    private ZonedDateTime fechaPublicacion;
}
