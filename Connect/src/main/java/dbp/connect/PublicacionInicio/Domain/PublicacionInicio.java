package dbp.connect.PublicacionInicio.Domain;

import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.MultimediaInicio.Domain.MultimediaInicio;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class PublicacionInicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private User autor;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultimediaInicio> multimediaList;
    private String cuerpo;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;
}
