package dbp.connect.AlojamientoMultimedia.DTOS;

import dbp.connect.Tipo;
import lombok.Data;

@Data
public class ResponseMultimediaDTO {
    private Long id;
    private String url_contenido;
    private Tipo tipo;
}
