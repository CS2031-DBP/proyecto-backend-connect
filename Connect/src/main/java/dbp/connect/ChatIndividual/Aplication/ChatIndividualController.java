package dbp.connect.ChatIndividual.Aplication;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;
import dbp.connect.ChatIndividual.Domain.ChatIndividualService;
import dbp.connect.MensajeIndividual.DTOS.DTOMensajePost;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chatIndividual")
public class ChatIndividualController {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private final ChatIndividualService chatIndividualService;
    public ChatIndividualController(SimpMessagingTemplate messagingTemplate, ChatIndividualService chatIndividualService) {
        this.messagingTemplate = messagingTemplate;
        this.chatIndividualService = chatIndividualService;
    }
    @PostMapping("/crear")
    public ResponseEntity<ChatIndividual> crearChat(@RequestParam Long usuario1Id,
                                                    @RequestParam Long usuario2Id) {
        ChatIndividual chat =  chatIndividualService.crearChat(usuario1Id, usuario2Id);
        if (chat != null) {
            return ResponseEntity.ok(chat);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarChat(@PathVariable Long id) {
        chatIndividualService.eliminarChat(id);
        return ResponseEntity.noContent().build();
    }
    @MessageMapping("/{chatId}")
    public void sendToChat(@DestinationVariable Long chatId, DTOMensajePost message) {

        ChatIndividual chat = chatIndividualService.obtenerChatPorId(chatId);
        if (chat != null) {
            chatIndividualService.guardarChat(chat);
            String mensaje = chatIndividualService.obtenerMensajeDTO(message);
            messagingTemplate.convertAndSend("/topic/chat/" + chatId, mensaje);
        }
    }
    @GetMapping("/{chatId}/mensajes")
    public ResponseEntity<List<MensajeIndividual>> obtenerMensajesDeChatPaginados(
            @PathVariable Long chatId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        List<MensajeIndividual> mensajes = chatIndividualService.obtenerMensajesDeChatPaginados(chatId, pageNumber, pageSize);
        return ResponseEntity.ok(mensajes);
    }
}
