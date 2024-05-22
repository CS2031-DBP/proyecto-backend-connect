package dbp.connect.PublicacionInicio.DTOS;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Getter
@Setter
@Data
public class PublicacionInicioResponseDTO {
    private String contenido;
    private byte[] fotPerfil;
    @NotNull
    private String userFullName;
    private List<byte[]> multimedia;
    private Integer cantidadLikes;
    private Integer cantidadComentarios;
}
