package dbp.connect.User.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Chat.Domain.Chat;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.FriendRequest.Domain.FriendshipRequest;
import dbp.connect.Friendship.Domain.Friendship;
import dbp.connect.Likes.Domain.Like;
import dbp.connect.Mensaje.Domain.Mensaje;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.Review.Domain.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="primer_nombre")
    private String primerNombre;

    @Column(name="segundo_nombre")
    private String segundoNombre;

    @Column(name="primer_apellido")
    private String primerApellido;

    @Column(name="segundo_apellido")
    private String segundoApellido;

    @Column(name="edad", nullable = false)
    private Integer edad;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="genero", nullable = false)
    private String genero;

    @Column(name="foto")
    private String fotoUrl;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="telefono")
    private Integer telefono;

    @Column(name = "fecha_Nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name ="ciudad")
    private String ciudad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "role", nullable = false)
    private Rol role;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private Set<Mensaje> mensaje = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chats_id", referencedColumnName = "id")
    )
    private Set<Chat> chats = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendshipsInicializados = new HashSet<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendOf = new HashSet<>();

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

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FriendshipRequest> sentFriendRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FriendshipRequest> receivedFriendRequests = new HashSet<>();
    public User removeMensaje(Mensaje mensaje) {
        this.mensaje.remove(mensaje);
        mensaje.setAutor(null);
        return this;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensaje = mensajes;
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
    public User chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public User addChat(Chat chat) {
        this.chats.add(chat);
        chat.getUsers().add(this);
        return this;
    }

    public User removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.getUsers().remove(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return edad == user.edad && Objects.equals(id, user.id)
                && Objects.equals(username, user.username) &&
                Objects.equals(primerNombre, user.primerNombre) &&
                Objects.equals(segundoNombre, user.segundoNombre) &&
                Objects.equals(primerApellido, user.primerApellido) &&
                Objects.equals(segundoApellido, user.segundoApellido) &&
                Objects.equals(email, user.email) && Objects.equals(password,
                user.password) && Objects.equals(genero, user.genero) &&
                Objects.equals(fotoUrl, user.fotoUrl) &&
                Objects.equals(descripcion, user.descripcion) &&
                Objects.equals(telefono, user.telefono) &&
                Objects.equals(fechaNacimiento, user.fechaNacimiento) &&
                Objects.equals(ciudad, user.ciudad) && Objects.equals(pais, user.pais)
                && Objects.equals(direccion, user.direccion) && Objects.equals(mensaje, user.mensaje)
                && Objects.equals(chats, user.chats) &&
                Objects.equals(friendshipsInicializados, user.friendshipsInicializados)
                && Objects.equals(friendOf, user.friendOf) && Objects.equals(alojamientos,
                user.alojamientos) && Objects.equals(publicacionInicio, user.publicacionInicio)
                && Objects.equals(comentarios, user.comentarios) && Objects.equals(reviews, user.reviews)
                && Objects.equals(likes, user.likes) && Objects.equals(sentFriendRequests,
                user.sentFriendRequests) && Objects.equals(receivedFriendRequests,
                user.receivedFriendRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, primerNombre, segundoNombre, primerApellido,
                segundoApellido, edad, email, password, genero, fotoUrl, descripcion,
                telefono, fechaNacimiento, ciudad, pais, direccion, mensaje, chats,
                friendshipsInicializados, friendOf, alojamientos, publicacionInicio,
                comentarios, reviews, likes, sentFriendRequests, receivedFriendRequests);
    }

    @Transient
    String role_prefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role_prefix + role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
