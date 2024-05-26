package com.example.Connect.Mensajes.Infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Connect.Mensajes.Domain.MembersChat;
import com.example.Connect.Mensajes.Domain.MembersChatId;

public interface MembersChatRepository extends JpaRepository<MembersChat, MembersChatId> {
}
