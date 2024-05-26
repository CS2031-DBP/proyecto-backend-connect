package com.example.Connect.Mensajes.Infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Connect.Mensajes.Domain.Chats;

public interface ChatsRepository extends JpaRepository<Chats, Long> {
}
