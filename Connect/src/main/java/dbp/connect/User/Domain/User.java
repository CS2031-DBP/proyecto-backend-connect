package dbp.connect.User.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.ChatGrupal.Domain.ChatGrupal;
import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.Friends.Domain.Friends;
import dbp.connect.Likes.Domain.Like;
import dbp.connect.MensajeGrupal.Domain.MensajeGrupal;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.Review.Domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="usuario")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    @Column(name="usuario")
    private String username;
    @Column(name="primer_nombre")
    private String primerNombre;
    @Column(name="segundo_nombre")
    private String segundoNombre;
    @Column(name="primer_apellido")
    private String primerApellido;
    @Column(name="segundo_nombre")
    private String segundoApellido;
    @Column(name="edad")
    private int edad;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="latitude")
    private Double latitude;
    @Column(name="longitud")
    private Double longitud;
    @Lob
    @Column(name="foto")
    private byte[] foto;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    private Set<MensajeIndividual> mensajeIndividual = new HashSet<>();
   // @OneToMany(mappedBy = "autorG",cascade = CascadeType.ALL)
    //private List<MensajeGrupal> mensajeGrupal = new ArrayList<>();
   // @ManyToMany
    //@JoinTable(
      //      name = "usuario_chatgrupo",
        //    joinColumns = @JoinColumn(name = "usuario_id"),
          //  inverseJoinColumns = @JoinColumn(name = "grupo_id")
    //)
    //private List<ChatGrupal> grupos = new ArrayList<>();
   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   @JoinTable(
           name = "usuario_chat",
           joinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "chats_id", referencedColumnName = "id")
   )
   private Set<ChatIndividual> chats = new HashSet<>();

    //@LazyCollection(LazyCollectionOption.FALSE)
    //@OneToMany(mappedBy = "friend")
   // @JsonIgnore
   // private List<Friends> friends;
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL)
    private List<Alojamiento> alojamientos = new ArrayList<>();

    @OneToMany(mappedBy = "autorP", cascade = CascadeType.ALL)
    private Set<PublicacionInicio> publicacionInicio = new HashSet<>();

    @OneToMany(mappedBy = "autorComentario", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "autorR", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "usuarioLike", cascade = CascadeType.REMOVE)
    private Set<Like> likes = new HashSet<>();


    public User removeMensaje(MensajeIndividual mensaje) {
        this.mensajeIndividual.remove(mensaje);
        mensaje.setAutor(null);
        return this;
    }

    public void setMensajes(Set<MensajeIndividual> mensajes) {
        this.mensajeIndividual = mensajes;
    }
    public User likes(Set<Like> likes) {
        this.likes = likes;
        return this;
    }
    public User removeLike(Like like) {
        this.likes.remove(like);
        like.setUsuarioLike(null);
        return this;
    }

    public User addLikes(Like like) {
        this.likes.add(like);
        like.setUsuarioLike(this);
        return this;
    }
    public User chats(Set<ChatIndividual> chats) {
        this.chats = chats;
        return this;
    }

    public User addChat(ChatIndividual chat) {
        this.chats.add(chat);
        chat.getUsuarios().add(this);
        return this;
    }

    public User removeChat(ChatIndividual chat) {
        this.chats.remove(chat);
        chat.getUsuarios().remove(this);
        return this;
    }

}
