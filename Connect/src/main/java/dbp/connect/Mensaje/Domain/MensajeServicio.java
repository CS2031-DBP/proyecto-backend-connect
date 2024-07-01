package dbp.connect.Mensaje.Domain;

import com.amazonaws.services.connect.model.UserNotFoundException;
import dbp.connect.Chat.Domain.Chat;
import dbp.connect.Chat.Domain.ChatService;
import dbp.connect.Chat.Infrastructure.ChatRepository;
import dbp.connect.Mensaje.DTOS.ContentDTO;
import dbp.connect.Mensaje.DTOS.DTOMensajePost;
import dbp.connect.Mensaje.DTOS.MensajeResponseDTO;
import dbp.connect.Mensaje.Infrastructure.MensajeRepository;
import dbp.connect.MultimediaMensaje.Domain.MultimediaMensaje;
import dbp.connect.MultimediaMensaje.Domain.MultimediaMensajeServicio;
import dbp.connect.MultimediaMensaje.Infrastructure.MultimediaMensajeRepositorio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MensajeServicio {
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MultimediaMensajeServicio multimediaMensajeIndividualServicio;
    @Autowired
    private MultimediaMensajeRepositorio multimediaMensajeRepositorio;

    public MensajeResponseDTO sendMessage(DTOMensajePost mensaje) {
        User user = userRepository.findById(mensaje.getUserId()).orElseThrow(
                () -> new UserNotFoundException("Usuario no encontrado"));
        Chat chat = chatRepository.findById(mensaje.getChatId()).orElseThrow(
                () -> new EntityNotFoundException("Chat no encontrado"));

        Mensaje newMessage = new Mensaje();
        newMessage.setUser(user);
        newMessage.setChat(chat);
        newMessage.setCuerpo(mensaje.getContenido());
        newMessage.setStatus(StatusMensaje.ENVIADO);
        newMessage.setCuerpo(mensaje.getContenido());
        newMessage.setFecha_mensaje(ZonedDateTime.now(ZoneId.systemDefault()));
        for (MultipartFile file : mensaje.getMultimedia()) {
            MultimediaMensaje multimedia = multimediaMensajeIndividualServicio.saveMultimedia(file);
            multimedia.setMensaje(newMessage);
            multimediaMensajeRepositorio.save(multimedia);
            newMessage.getMultimediaMensaje().add(multimedia);
        }
        mensajeRepository.save(newMessage);
        return toDTOResponse(newMessage);
    }

    public void modificarMensajeContenido(Long chatId, ContentDTO contenido) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                () -> new EntityNotFoundException("Chat no encontrado"));

        for (Mensaje mensaje : chat.getMensajes()) {
            if (mensaje.getId().equals(contenido.getMensajeId())) {
                Mensaje mensajeI = mensajeRepository.findById(contenido.getMensajeId()).orElseThrow(
                        () -> new EntityNotFoundException("MensajeController no encontrado"));
                mensajeI.setCuerpo(contenido.getMensaje());
                mensajeRepository.save(mensajeI);
            }
        }
    }

    public void deleteMensajeById(Long chatId, Long id) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                () -> new EntityNotFoundException("Chat no encontrado"));

        for (Mensaje mensaje : chat.getMensajes()) {
            if (mensaje.getId().equals(id)) {
                mensajeRepository.deleteById(id);
                chat.removeMensaje(mensaje);
            }
        }
    }

    public Page<MensajeResponseDTO> obtenerTodosLosMensajesDeUnChat(Long chatId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                ()->new EntityNotFoundException("Chat no encontrado"));
        Page<Mensaje> mensajesPage = mensajeRepository.findByChatId(chatId, pageable);


        Set<MensajeResponseDTO> mensajesDTO = mensajesPage.getContent().stream()
                .map(this::toDTOResponse)
                .collect(Collectors.toSet());

        return new PageImpl<>(mensajesDTO.stream().collect(Collectors.toList()), pageable, mensajesPage.getTotalElements());
    }

    public MensajeResponseDTO obtenerMensajePorchatIdYMensajeId(Long chatId, Long mensajeiD){

        Mensaje mensaje = mensajeRepository.findByChatIdAndId(chatId, mensajeiD)
                .orElseThrow(()->new EntityNotFoundException("No se encontro el mensaje para el chat especificado"));
        return toDTOResponse(mensaje);
    }
    public MensajeResponseDTO findMessageById(Long id){
        Mensaje mensaje = mensajeRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Mensaje no encontrado"));
        return toDTOResponse(mensaje);
    }
    private MensajeResponseDTO toDTOResponse(Mensaje mensaje){
        MensajeResponseDTO mensajeResponseDTO = new MensajeResponseDTO();
        mensajeResponseDTO.setId(mensaje.getId());
        mensajeResponseDTO.setContenido(mensaje.getCuerpo());
        mensajeResponseDTO.setStatusMensaje(mensaje.getStatus());
        mensajeResponseDTO.setUsername(mensaje.getUser().getUsername());
        mensajeResponseDTO.setChatId(mensaje.getChat().getId());
        mensajeResponseDTO.setFecha(mensaje.getFecha_mensaje());
        return mensajeResponseDTO; //Falta terminar
    }



}
