package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividualService;
import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.MensajeGrupal.Domain.StatusMensaje;
import dbp.connect.MensajeIndividual.DTOS.DTOMensajePost;
import dbp.connect.MensajeIndividual.DTOS.MensajeResponseDTO;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import dbp.connect.User.Infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public MensajeResponseDTO save(DTOMensajePost mensaje){
        if (mensaje.getId() != null) {
            Optional<MensajeIndividual> existingMessage = mensajeIndividualRepository.findById(mensaje.getId());
            if (existingMessage.isPresent()) {
                throw new IllegalArgumentException("El mensaje ya ha sido creado.");
            }
        }

        // Crear y guardar el nuevo mensaje
        MensajeIndividual newMessage = new MensajeIndividual();
        newMessage.setAutor(userRepository.findById(mensaje.getAutorId());
        newMessage.setChat(chatIndividualRepository.findById(mensaje.getChatId());
        newMessage.setCuerpo(mensaje.getContenido());
        newMessage.setStatusMensaje(StatusMensaje.ENVIADO);
        newMessage.setMultimedia(mensaje.getMultimedia());


        // Convertir la entidad guardada a DTO
        MensajeResponseDTO responseDTO = new MensajeResponseDTO();
        responseDTO.setId(savedMessage.getId());
        responseDTO.setAutorId(savedMessage.getAutorId());
        responseDTO.setChatId(savedMessage.getChatId());
        responseDTO.setContenido(savedMessage.getContenido());
        responseDTO.setStatusMensaje(savedMessage.getStatusMensaje());
        responseDTO.setMultimedia(savedMessage.getMultimedia());
    }


}
