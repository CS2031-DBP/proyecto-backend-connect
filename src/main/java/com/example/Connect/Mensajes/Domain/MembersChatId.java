package com.example.Connect.Mensajes.Domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersChatId implements Serializable {

  private Long chat;
  private Long user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MembersChatId that = (MembersChatId) o;
    return Objects.equals(chat, that.chat) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chat, user);
  }
}
