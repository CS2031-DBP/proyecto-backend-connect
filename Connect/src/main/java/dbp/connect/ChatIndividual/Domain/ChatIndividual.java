package dbp.connect.ChatIndividual.Domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
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
public class ChatIndividual implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name= "fecha_creacion")
    private ZonedDateTime fechaCreacion;

    @Column(name="ultima_vez_visto")
    private ZonedDateTime ultimaVezVisto;

    @ManyToMany(mappedBy = "chats")
    @JsonIgnoreProperties("chats")
    private Set<User> usuarios= new HashSet<>();

    @OneToMany(mappedBy = "chatIndividual", cascade = CascadeType.REMOVE)
    private Set<MensajeIndividual> mensajes = new HashSet<>();

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public ZonedDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public ChatIndividual fechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ZonedDateTime getUltimaVezVisto() {
        return ultimaVezVisto;
    }

    public ChatIndividual ultimaVezVisto(ZonedDateTime ultimaVezVisto) {
        this.ultimaVezVisto = ultimaVezVisto;
        return this;
    }

    public void setUltimaVezVisto(ZonedDateTime ultimaVezVisto) {
        this.ultimaVezVisto = ultimaVezVisto;
    }

    public Set<MensajeIndividual> getMensajes() {
        return mensajes;
    }

    public ChatIndividual mensajes(Set<MensajeIndividual> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public ChatIndividual addMensaje(MensajeIndividual mensaje) {
        this.mensajes.add(mensaje);
        mensaje.setChat(this);
        return this;
    }

    public ChatIndividual removeMensaje(MensajeIndividual mensaje) {
        this.mensajes.remove(mensaje);
        mensaje.setChat(null);
        return this;
    }

    public void setMensajes(Set<MensajeIndividual> mensajes) {
        this.mensajes = mensajes;
    }

    public Set<User> getUsuarios() {
        return usuarios;
    }

    public ChatIndividual usuarios(Set<User> usuarios) {
        this.usuarios = usuarios;
        return this;
    }

    public ChatIndividual addUsuario(User usuario) {
        this.usuarios.add(usuario);
        usuario.getChats().add(this);
        return this;
    }

    public ChatIndividual removeUsuario(User usuario) {
        this.usuarios.remove(usuario);
        usuario.getChats().remove(this);
        return this;
    }

    public void setUsuarios(Set<User> usuarios) {
        this.usuarios = usuarios;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatIndividual chat = (ChatIndividual) o;
        if (chat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + getId() +
                ", fechaCreacion='" + getFechaCreacion() + "'" +
                ", ultimaVezVisto='" + getUltimaVezVisto() + "'" +
                "}";
    }
}
