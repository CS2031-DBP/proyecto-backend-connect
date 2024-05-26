package com.example.Connect.Mensajes.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.Connect.Usuario.Domain.Usuario;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mensajes")
public class Mensajes {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chat_id", nullable = false)
  private Chats chat;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario user;

  @Column(name = "body", nullable = false)
  private String body;

  @Column(name = "fecha_envio", nullable = false)
  private ZonedDateTime fechaEnvio;

  @PrePersist
  public void prePersist() {
    this.fechaEnvio = ZonedDateTime.now();
  }

  public Mensajes(String body, Chats chat, Usuario user) {
    this.body = body;
    this.chat = chat;
    this.user = user;
    this.fechaEnvio = ZonedDateTime.now();
  }
}
