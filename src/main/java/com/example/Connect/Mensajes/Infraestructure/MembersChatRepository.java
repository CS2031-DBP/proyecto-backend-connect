package com.example.forutec2.Mensajes.Infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.forutec2.Mensajes.Domain.MembersChat;
import com.example.forutec2.Mensajes.Domain.MembersChatId;

public interface MembersChatRepository extends JpaRepository<MembersChat, MembersChatId> {
}
