package com.example.forutec2.Mensajes.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.forutec2.Mensajes.Domain.Chats;
import com.example.forutec2.Mensajes.Domain.MembersChat;
import com.example.forutec2.Mensajes.Infraestructure.ChatsRepository;
import com.example.forutec2.Mensajes.Infraestructure.MembersChatRepository;
import com.example.forutec2.Usuario.Domain.Usuario;
import com.example.forutec2.Usuario.Infraestructure.UsuarioRepository;
import com.example.forutec2.Exception.CustomException;

import java.util.List;

import com.example.forutec2.Mensajes.Dto.ChatDto;  

@Service
public class ChatService {

  @Autowired
  private ChatsRepository chatsRepository;

  @Autowired
  private MembersChatRepository membersChatRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public ChatDto createGroupChat(String nameChat, List<Long> userIds) {
      Chats chat = new Chats();
      chat.setIsGroup(true);
      chat.setNameChat(nameChat);
      chatsRepository.save(chat);

      for (Long userId : userIds) {
          Usuario user = usuarioRepository.findById(userId)
                  .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
          MembersChat membersChat = new MembersChat();
          membersChat.setChat(chat);
          membersChat.setUser(user);
          membersChatRepository.save(membersChat);
      }
      
      return new ChatDto(chat.getId(), chat.getIsGroup(), chat.getNameChat());
  }

  public ChatDto createSingleChat(Long userId1, Long userId2) {
    Chats chat = new Chats();
    chat.setIsGroup(false);
    chat.setNameChat("Chat between " + userId1 + " and " + userId2);
    chatsRepository.save(chat);

    Usuario user1 = usuarioRepository.findById(userId1)
            .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
    Usuario user2 = usuarioRepository.findById(userId2)
            .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    MembersChat membersChat1 = new MembersChat();
    membersChat1.setChat(chat);
    membersChat1.setUser(user1);
    membersChatRepository.save(membersChat1);

    MembersChat membersChat2 = new MembersChat();
    membersChat2.setChat(chat);
    membersChat2.setUser(user2);
    membersChatRepository.save(membersChat2);
    
    return new ChatDto(chat.getId(), chat.getIsGroup(), chat.getNameChat());
  }
}
