package com.example.forutec2.Mensajes.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.forutec2.Mensajes.Domain.Chats;
import com.example.forutec2.Mensajes.Dto.GroupChatCreateDto;
import com.example.forutec2.Mensajes.Dto.SingleChatCreateDto;

import com.example.forutec2.Mensajes.Dto.ChatDto;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/group")
    public ResponseEntity<ChatDto> createGroupChat(
        @RequestBody GroupChatCreateDto groupChatCreateDto
    ) {
      ChatDto chat = chatService.createGroupChat(groupChatCreateDto.getNameChat(), groupChatCreateDto.getUserIds());
      return ResponseEntity.ok(chat);
    }

    @PostMapping("/single")
    public ResponseEntity<ChatDto> createSingleChat(
        @RequestBody SingleChatCreateDto singleChatCreateDto
    ) {
      ChatDto chat = chatService.createSingleChat(singleChatCreateDto.getUserId1(), singleChatCreateDto.getUserId2());
      return ResponseEntity.ok(chat);
    }
}
