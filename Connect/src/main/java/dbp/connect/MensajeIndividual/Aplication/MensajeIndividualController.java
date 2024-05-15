package dbp.connect.MensajeIndividual.Aplication;

import dbp.connect.MensajeIndividual.Domain.ChatNotificacion;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividualService;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MensajeIndividualController {

    @Autowired
    private final MensajeIndividualService mensajeIndividualService;
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload MensajeIndividual mensajeIndividual) {
        MensajeIndividual savedMsg= mensajeIndividualService.save(mensajeIndividual);
        messagingTemplate.convertAndSendToUser(
                mensajeIndividual.getRecipientId(), "/queue/messages",
                new ChatNotificacion(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
    }
    @GetMapping("{senderId}/{recipientId}")
    public ResponseEntity<List<MensajeIndividual>> findchatMessage(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(mensajeIndividualService.findChatMessages(senderId, recipientId));
    }

}
