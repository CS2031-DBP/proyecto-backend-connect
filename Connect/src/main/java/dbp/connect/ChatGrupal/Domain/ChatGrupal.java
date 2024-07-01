package dbp.connect.ChatGrupal.Domain;

import dbp.connect.MensajeGrupal.Domain.MensajeGrupal;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ChatGrupal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private ZonedDateTime fechaCreacion;
    @ManyToMany
    private Set<User> admins = new HashSet<>();
    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;
    @ManyToMany
    private Set<User> users = new HashSet<>();
    @OneToMany(mappedBy = "chatGrupal", cascade = CascadeType.ALL)
    private List<MensajeGrupal> mensajes = new ArrayList<>();
}
