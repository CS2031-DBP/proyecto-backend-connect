package dbp.connect.User.Domain;

import dbp.connect.ChatGrupal.Domain.ChatGrupal;
import dbp.connect.MensajeGrupal.Domain.MensajeGrupal;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "mensajeI_recividos")
    private List<MensajeIndividual> mensajeIndividualRecividos = new ArrayList<>();
    @OneToMany(mappedBy = "menssajeI_enviado")
    private List<MensajeIndividual> menssajeIndividualIEnviados = new ArrayList<>();
    @OneToMany(mappedBy = "mensaje_recividos")
    private List<MensajeGrupal> mensajeGrupalRecividos = new ArrayList<>();
    @OneToMany(mappedBy = "mensaje_enviados")
    private List<MensajeGrupal> mensajeGrupalEnviados = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "usuario_chatgrupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private List<ChatGrupal> grupos = new ArrayList<>();
    @OneToMany(mappedBy = "chats_individuales")
    private List<ChatGrupal> chatsIndividuales = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "amigos",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<User> amigos =new ArrayList<>();

}
