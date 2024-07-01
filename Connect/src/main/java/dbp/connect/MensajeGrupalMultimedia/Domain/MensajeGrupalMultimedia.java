package dbp.connect.MensajeGrupalMultimedia.Domain;


import dbp.connect.Tipo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class MensajeGrupalMultimedia {
    @Id
    private String id;
    private Tipo tipo;
    private String contenidoUrl;
    private ZonedDateTime fechaEnvio;
}
