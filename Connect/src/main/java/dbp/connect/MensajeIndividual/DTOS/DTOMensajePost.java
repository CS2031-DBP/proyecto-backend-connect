package dbp.connect.MensajeIndividual.DTOS;

import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@Data
public class DTOMensajePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long autorId;
    @NotNull
    private Long chatId;
    @NotNull
    @Size(min = 2, max=1000)
    private String contenido;
    private List<MultipartFile> multimedia;
}
