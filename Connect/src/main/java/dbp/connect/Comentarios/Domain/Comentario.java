package dbp.connect.Comentarios.Domain;

import dbp.connect.ComentariosMultimedia.Domain.ComentarioMultimedia;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comentario {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private Long id;
    @JsonIgnore
    @Column()
    private Long parentId;

    @Column(name = "message")
    private String message;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name ="autorM_id")
    private User Autor;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name="parent_id")
    private List<Comentario> replies;

    public void addCommentReplies(Comentario comment) {
        if(replies == null) {
            replies = new ArrayList<>();
        }
        replies.add(comment);
    }
    @ManyToOne
    @JoinColumn(name="publicacion_id",nullable = false)
    private PublicacionInicio publicacion;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "multimedia_id")
    private ComentarioMultimedia multimedia;
    @JoinColumn(name="likes")
    private Integer likes;
    @JoinColumn(name = "date")
    private LocalDateTime date;



}
