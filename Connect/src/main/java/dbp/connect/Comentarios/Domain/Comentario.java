package dbp.connect.Comentarios.Domain;

import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

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
    @Column(nullable = true)
    private Long parentId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PublicacionInicio post;

    @Column(name = "message")
    private String message;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name ="user_id")
    private User user;

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


}
