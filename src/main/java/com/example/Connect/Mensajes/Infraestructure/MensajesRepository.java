package com.example.forutec2.Mensajes.Infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.forutec2.Mensajes.Domain.Mensajes;

import com.example.forutec2.Mensajes.Domain.Chats;

import java.util.List;

public interface MensajesRepository extends JpaRepository<Mensajes, Long> {
  List<Mensajes> findByChat(Chats chat);


}
