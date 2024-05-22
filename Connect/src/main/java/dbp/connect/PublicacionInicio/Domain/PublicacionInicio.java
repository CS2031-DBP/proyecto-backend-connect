package dbp.connect.PublicacionInicio.Domain;

import dbp.connect.Comentarios.Domain.Comentario;

import dbp.connect.PublicacionInicioMultimedia.Domain.PublicacionInicioMultimedia;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name = "autorP_Id", nullable = false)
    private User autor;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublicacionInicioMultimedia> publicacionMultimedia = new ArrayList<>();
    private String cuerpo;
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime fechaPublicacion;
    @JoinColumn(name = "cantidadLikes")
    private Integer cantidadLikes;
    @JoinColumn(name = "cantidadComentarios")
    private Integer cantidadComentarios;

}
