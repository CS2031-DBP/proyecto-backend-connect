package com.example.Connect.Mensajes.Infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Connect.Mensajes.Domain.Mensajes;

import com.example.Connect.Mensajes.Domain.Chats;

import java.util.List;

public interface MensajesRepository extends JpaRepository<Mensajes, Long> {
  List<Mensajes> findByChat(Chats chat);


}
