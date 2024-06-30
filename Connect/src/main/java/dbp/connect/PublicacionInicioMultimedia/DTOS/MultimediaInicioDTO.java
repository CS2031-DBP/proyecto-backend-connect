package dbp.connect.PublicacionInicioMultimedia.DTOS;

import dbp.connect.Tipo;
import lombok.Data;

@Data
public class MultimediaInicioDTO {
    private String id;
    private String contenidoUrl;
    private Tipo tipo;

}
