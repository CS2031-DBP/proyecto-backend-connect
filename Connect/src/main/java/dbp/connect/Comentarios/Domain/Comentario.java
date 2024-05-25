package dbp.connect.Comentarios.Domain;

import dbp.connect.ComentariosMultimedia.Domain.ComentarioMultimedia;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comentario {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "message")
    private String message;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name ="autorComentario_id")
    private User autorComentario;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comentario parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Comentario> replies;

    public void addCommentReplies(Comentario comment) {
        if(replies == null) {
            replies = new ArrayList<>();
        }
        replies.add(comment);
        comment.setParent(this);  //establecer el parent del comentario hijo

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
