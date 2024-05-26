package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividualService;
import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MensajeIndividual.DTOS.DTOMensajePost;
import dbp.connect.MensajeIndividual.DTOS.MensajeResponseDTO;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividual;
import dbp.connect.MultimediaMensajeIndividual.Domain.MultimediaMensajeIndividualServicio;
import dbp.connect.User.Infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;


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

    public MensajeResponseDTO save(DTOMensajePost mensaje){
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
        for(MultipartFile file: mensaje.getMultimedia()){
            MultimediaMensajeIndividual multimedia = multimediaMensajeIndividualServicio.saveMultimedia(file);
            newMessage.addMultimedia(multimedia);
        }
        newMessage.setFechaCreacion(ZonedDateTime.now(ZoneId.systemDefault()));
        return toDTOResponse(newMessage);
    }
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
