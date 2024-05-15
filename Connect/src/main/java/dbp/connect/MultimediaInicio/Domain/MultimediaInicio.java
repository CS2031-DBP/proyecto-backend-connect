package dbp.connect.MultimediaInicio.Domain;

import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MultimediaInicio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @Lob
    private byte[] archivo;

}
