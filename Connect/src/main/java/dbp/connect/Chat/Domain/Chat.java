package dbp.connect.Chat.Domain;


import dbp.connect.Mensaje.Domain.Mensaje;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Chat implements Serializable {
    @Id
    private Long Id;

    @Column(name= "fecha_creacion")
    private ZonedDateTime fechaCreacion;

    @Column(name = "chat_name")
    private String chat_name;

    @Column(name = "chat_image")
    private String chat_image;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes = new ArrayList<>();


    public Chat addMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
        mensaje.setChat(this);
        return this;
    }

    public Chat removeMensaje(Mensaje mensaje) {
        this.mensajes.remove(mensaje);
        mensaje.setChat(null);
        return this;
    }


    public Chat addUsuario(User usuario) {
        this.users.add(usuario);
        usuario.getChats().add(this);
        return this;
    }

    public Chat removeUsuario(User usuario) {
        this.users.remove(usuario);
        usuario.getChats().remove(this);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chat chat = (Chat) o;
        if (chat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
