package dbp.connect.Alojamiento.DTOS;

import dbp.connect.Alojamiento.Domain.Estado;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Data
public class AlojamientoRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @NotNull
    private Long propietarioId;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    @Size(min = 1, max = 255)
    private String descripcion;
    @NotNull
    private double precio;
    private List<MultipartFile> multimedia = new ArrayList<>();

}
