package dbp.connect.Friendship.DTO;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmigosDTO {
    @NotNull
    Long amigoId;
    @NotNull
    String nombreCompleto;
    @NotNull
    String apellidoCompleto;
    String fotoPerfilUrl;

}
