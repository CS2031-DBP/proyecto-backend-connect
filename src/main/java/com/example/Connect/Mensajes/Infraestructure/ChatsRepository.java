package com.example.forutec2.Mensajes.Infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.forutec2.Mensajes.Domain.Chats;

public interface ChatsRepository extends JpaRepository<Chats, Long> {
}
