package dbp.connect.PublicacionInicioMultimedia.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PublicacionInicioMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
