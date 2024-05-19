package dbp.connect.ChatIndividual.Domain;

import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MensajeIndividual.DTOS.DTOMensajePost;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import dbp.connect.MultimediaInicio.Domain.MultimediaInicioServicio;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividualServicio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Domain.UserService;

import dbp.connect.User.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ChatIndividualService {
    @Autowired
    ChatIndividualRepository chatIndividualRepository;
    @Autowired
    UserService userService;
    @Autowired
    MensajeIndividualRepository mensajeIndividualRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MultimediaMensajeIndividualServicio multimediaMensajeIndividualServicio;


    public ChatIndividual crearChat(Long usuario1, Long usuario2) {
        ChatIndividual existingChat = chatIndividualRepository.findByUsuarios(usuario1, usuario2);
        if (existingChat != null) {
            return existingChat;
        }
        User user1 = userService.getUserById(usuario1)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario1));

        User user2 = userService.getUserById(usuario2)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario2));
        ChatIndividual chat = new ChatIndividual();
        List<User> usuariostempo = new ArrayList<>();
        usuariostempo.add(user1);
        usuariostempo.add(user2);
        chat.setUsuarios(usuariostempo);
        return chatIndividualRepository.save(chat);
    }

    public void eliminarChat(Long id) {
        if (chatIndividualRepository.existsById(id)) {

            chatIndividualRepository.deleteById(id);
        } else {
            throw new RuntimeException("Chat individual no encontrado con el ID: " + id);
        }
    }
    public ChatIndividual obtenerChatPorId(Long id) {
        return chatIndividualRepository.findById(id).orElse(null);
    }

    public ChatIndividual guardarChat(ChatIndividual chat) {
        return chatIndividualRepository.save(chat);
    }
    public List<MensajeIndividual> obtenerMensajesDeChatPaginados(Long chatId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<MensajeIndividual> page = mensajeIndividualRepository.findByChatId(chatId, pageRequest);
        return page.getContent();
    }
    public String obtenerMensajeDTO(DTOMensajePost mensajePost) {
        if (mensajePost.getAutorId() == null || mensajePost.getChatId() == null
        || mensajePost.getId() == null) {
            throw new IllegalArgumentException("Argumentos no deben de ser nulos");
        }
        else{

            User currentautor= userRepository.findById(mensajePost.getAutorId()).orElseThrow(()
                -> new RuntimeException("Autor no encontrado"));
            ChatIndividual currentChatIndividual = chatIndividualRepository.findById(
                mensajePost.getChatId()).orElseThrow(()->
                new RuntimeException("Chat individual no encontrado"));
            MensajeIndividual mensajeIndividual = new MensajeIndividual();
            if(!currentChatIndividual.getUsuarios().contains(currentautor)){
                throw new IllegalArgumentException("Usuario no es pertenece a esta conversacion");
            }
            currentChatIndividual.setId(mensajePost.getChatId());
            mensajeIndividual.setId(mensajePost.getId());
            mensajeIndividual.setAutor(currentautor);
            mensajeIndividual.setChat(currentChatIndividual);
            mensajeIndividual.setCuerpo(mensajePost.getContenido());
            List<MultimediaMensajeIndividual> multimedia = new ArrayList<>();
            for(MultimediaMensajeIndividual multi:mensajePost.getMultimedia()){
                multimediaMensajeIndividualServicio.guardarMultimediaMensaje(multi);
                multimedia.add(multi);
            }
            mensajeIndividual.setArchivosMultimedia(multimedia);
            mensajeIndividual.setCuerpo(mensajePost.getContenido());
            mensajeIndividual.setTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
            mensajeIndividual.setStatusMensaje(StatusMensaje.ENVIADO);
            mensajeIndividualRepository.save(mensajeIndividual);
            List<MensajeIndividual> mensajeTemporal = new ArrayList<>();
            currentChatIndividual.setMensajes(mensajeTemporal);
        return mensajePost.getContenido();
    }
}}