package dbp.connect.User.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.ChatGrupal.Domain.ChatGrupal;
import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.MensajeGrupal.Domain.MensajeGrupal;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
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
    @OneToMany(mappedBy = "autor")
    private List<MensajeIndividual> mensajeIndividual= new ArrayList<>();
    @OneToMany(mappedBy = "autorG")
    private List<MensajeGrupal> mensajeGrupal = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "usuario_chatgrupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private List<ChatGrupal> grupos = new ArrayList<>();
    @ManyToMany(mappedBy = "usuarios")
    private List<ChatIndividual> chats;
    @ManyToMany
    @JoinTable(
            name = "amigos",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<User> amigos = new ArrayList<>();
    @OneToMany(mappedBy = "propietario")
    private List<Alojamiento> alojamientos = new ArrayList<>();

}
