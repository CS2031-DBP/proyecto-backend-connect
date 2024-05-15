package dbp.connect.PublicacionInicio.DTOS;

import dbp.connect.MultimediaInicio.Domain.MultimediaInicio;
import dbp.connect.User.Domain.User;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PostInicioDTO {
    @Size(min=1, max=255)
    private String Cuerpo;
    private List<MultimediaInicio> multimediaList;
    private User autorId;
}
