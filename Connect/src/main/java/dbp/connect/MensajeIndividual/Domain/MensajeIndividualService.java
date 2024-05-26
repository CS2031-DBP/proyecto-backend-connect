package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.Alojamiento.DTOS.ContenidoDTO;
import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.ChatIndividual.Domain.ChatIndividualService;
import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.Comentarios.DTOS.ComentarioRespuestaDTO;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.Excepciones.NoEncontradoException;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MensajeIndividual.DTOS.ContentDTO;
import dbp.connect.MensajeIndividual.DTOS.DTOMensajePost;
import dbp.connect.MensajeIndividual.DTOS.MensajeResponseDTO;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividualServicio;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MensajeIndividualService {
    @Autowired
    private ChatIndividualService chatIndividualService;
    @Autowired
    private ChatIndividualRepository chatIndividualRepository;
    @Autowired
    private MensajeIndividualRepository mensajeIndividualRepository;
    private UserRepository userRepository;
    @Autowired
    private MultimediaMensajeIndividualServicio multimediaMensajeIndividualServicio;

    public MensajeResponseDTO save(DTOMensajePost mensaje) {
        if (mensaje.getId() != null) {
            Optional<MensajeIndividual> existingMessage = mensajeIndividualRepository.findById(mensaje.getId());
            if (existingMessage.isPresent()) {
                throw new IllegalArgumentException("El mensaje ya ha sido creado.");
            }
        }
        MensajeIndividual newMessage = new MensajeIndividual();
        newMessage.setAutor(userRepository.findById(mensaje.getAutorId()).get());
        newMessage.setChat(chatIndividualRepository.findById(mensaje.getChatId()).get());
        newMessage.setCuerpo(mensaje.getContenido());
        newMessage.setStatusMensaje(StatusMensaje.ENVIADO);
        for (MultipartFile file : mensaje.getMultimedia()) {
            MultimediaMensajeIndividual multimedia = multimediaMensajeIndividualServicio.saveMultimedia(file);
            newMessage.addMultimedia(multimedia);
        }
        newMessage.setFechaCreacion(ZonedDateTime.now(ZoneId.systemDefault()));
        return toDTOResponse(newMessage);
    }

    public void modificarMensajeContenido(Long chatId, ContentDTO contenido) {
        ChatIndividual chat = chatIndividualRepository.findById(chatId).orElseThrow(
                () -> new EntityNotFoundException("Chat no encontrado"));

        for (MensajeIndividual mensaje : chat.getMensajes()) {
            if (mensaje.getId().equals(contenido.getMensajeId())) {
                MensajeIndividual mensajeI = mensajeIndividualRepository.findById(contenido.getMensajeId()).orElseThrow(
                        () -> new EntityNotFoundException("Mensaje no encontrado"));
                mensajeI.setCuerpo(contenido.getMensaje());
                mensajeIndividualRepository.save(mensajeI);
            }
        }
    }

    public void deleteMensajeById(Long chatId, Long id) {
        ChatIndividual chat = chatIndividualRepository.findById(chatId).orElseThrow(
                () -> new EntityNotFoundException("Chat no encontrado"));

        for (MensajeIndividual mensaje : chat.getMensajes()) {
            if (mensaje.getId().equals(id)) {
                mensajeIndividualRepository.deleteById(id);
                chat.removeMensaje(mensaje);
            }
        }
    }

    public Page<MensajeResponseDTO> obtenerTodosLosMensajesDeUnChat(Long chatId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        ChatIndividual chat = chatIndividualRepository.findById(chatId).orElseThrow(
                ()->new EntityNotFoundException("Chat no encontrado"));
        Page<MensajeIndividual> mensajesPage = mensajeIndividualRepository.findByChatId(chatId, pageable);


        Set<MensajeResponseDTO> mensajesDTO = mensajesPage.getContent().stream()
                .map(this::toDTOResponse)
                .collect(Collectors.toSet());

        return new PageImpl<>(mensajesDTO.stream().collect(Collectors.toList()), pageable, mensajesPage.getTotalElements());    }

    private MensajeResponseDTO toDTOResponse(MensajeIndividual mensaje){
        MensajeResponseDTO mensajeResponseDTO = new MensajeResponseDTO();
        mensajeResponseDTO.setId(mensaje.getId());
        mensajeResponseDTO.setContenido(mensaje.getCuerpo());
        mensajeResponseDTO.setStatusMensaje(mensaje.getStatusMensaje());
        mensajeResponseDTO.setUsername(mensaje.getAutor().getUsername());
        mensajeResponseDTO.setChatId(mensaje.getChat().getId());
        mensajeResponseDTO.setFecha(mensaje.getFechaCreacion());
        return mensajeResponseDTO; //Falta terminar
    }

}
