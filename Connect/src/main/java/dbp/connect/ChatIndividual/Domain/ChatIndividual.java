package dbp.connect.ChatIndividual.Domain;


import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToMany
    @JoinTable(
            name = "chat_usuarios",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<User> usuarios;

    @OneToMany(mappedBy = "chat")
    private List<MensajeIndividual> mensajes = new ArrayList<>();

    public void agregarMensaje(MensajeIndividual mensaje) {
        mensajes.add(mensaje);
        mensaje.setChat(this);
    }

    public void agregarUsuario(User user) {
        usuarios.add(user);
    }
}
