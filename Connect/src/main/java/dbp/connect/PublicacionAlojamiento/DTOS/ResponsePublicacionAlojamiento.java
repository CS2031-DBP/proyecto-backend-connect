package dbp.connect.PublicacionAlojamiento.DTOS;

import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    @Lob
    private List<ResponseMultimediaDTO> alojamientoMultimedia;
    @NotNull
    private String autorFullName;
    private String autorPhotoUrl;
    private int cantidadReviews;
    private Double promedioRating;
    @NotNull
    private Double latitue;
    @NotNull
    private Double longitud;
    private String direccion;
    private String ciudad;
    private String pais;
    @NotNull
    private ZonedDateTime fechaPublicacion;
}
