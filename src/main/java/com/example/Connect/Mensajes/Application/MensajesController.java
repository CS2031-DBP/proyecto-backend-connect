package com.example.forutec2.Mensajes.Application;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.forutec2.Mensajes.Domain.Mensajes;
import com.example.forutec2.Mensajes.Dto.MensajeDto;
import com.example.forutec2.Mensajes.Infraestructure.MensajesRepository;
import com.example.forutec2.Mensajes.Infraestructure.ChatsRepository;
import com.example.forutec2.Usuario.Infraestructure.UsuarioRepository;
import com.example.forutec2.Mensajes.Domain.Chats;
import com.example.forutec2.Usuario.Domain.Usuario;

import com.example.forutec2.Exception.CustomException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;


import java.util.List;


@Controller
public class MensajesController {

  @Autowired
  private MensajesRepository mensajesRepository;

  @Autowired
  private ChatsRepository chatsRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @MessageMapping("/send")
  @SendTo("/topic/mensajes")
  public MensajeDto enviarMensaje(MensajeDto mensajeDto) {
    System.out.println("Mensaje recibido: " + mensajeDto.getBody());
      Chats chat = chatsRepository.findById(mensajeDto.getChatId()).orElseThrow(() -> new CustomException(404, "Chat no encontrado"));
      Usuario usuario = usuarioRepository.findById(mensajeDto.getUserId()).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
      Mensajes mensaje = new Mensajes(mensajeDto.getBody(), chat, usuario);

      mensajesRepository.save(mensaje);
      return mensajeDto;
  }

  @GetMapping("/mensajes")
  @ResponseBody
  public List<MensajeDto> getMensajes(@RequestParam Long chatId) {
      Chats chat = chatsRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat no encontrado"));
      List<Mensajes> mensajes = mensajesRepository.findByChat(chat);
      return mensajes.stream()
              .map(mensaje -> new MensajeDto(mensaje.getChat().getId(), mensaje.getUser().getId(),mensaje.getBody()))
              .collect(Collectors.toList());
  }
}
