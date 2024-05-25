package dbp.connect.User.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.ChatGrupal.Domain.ChatGrupal;
import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.MensajeGrupal.Domain.MensajeGrupal;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.Review.Domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    private String username;
    private String fullname;
    private int edad;
    private String email;
    private String password;
    private Status status;
    @Lob
    private byte[] foto;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    private List<MensajeIndividual> mensajeIndividual= new ArrayList<>();
   // @OneToMany(mappedBy = "autorG",cascade = CascadeType.ALL)
    //private List<MensajeGrupal> mensajeGrupal = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "usuario_chatgrupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private List<ChatGrupal> grupos = new ArrayList<>();
    @ManyToMany(mappedBy = "usuarios")
    private List<ChatIndividual> chats;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "amigos",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<User> amigos = new ArrayList<>();
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL)
    private List<Alojamiento> alojamientos = new ArrayList<>();
    @OneToMany(mappedBy = "autorP", cascade = CascadeType.ALL)
    private List<PublicacionInicio> publicacionInicio = new ArrayList<>();
    @OneToMany(mappedBy = "autorComentario", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();
    @OneToMany(mappedBy = "autorR", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
