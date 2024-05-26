package com.example.Connect.Mensajes.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.Connect.Usuario.Domain.Usuario;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "members_chat")
@IdClass(MembersChatId.class)
public class MembersChat {

  @Id
  @ManyToOne
  @JoinColumn(name = "chat_id", nullable = false)
  private Chats chat;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario user;
}
