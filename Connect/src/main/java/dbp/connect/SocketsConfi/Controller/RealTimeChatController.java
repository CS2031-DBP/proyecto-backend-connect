package dbp.connect.SocketsConfi.Controller;

import dbp.connect.Mensaje.Domain.Mensaje;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RealTimeChatController {
    private SimpMessagingTemplate SimpMessagingTemplate;
    @MessageMapping("/mensaje")
    @SendTo("/group/public")
    public Mensaje recibirMensaje(@Payload Mensaje mensaje) {
        SimpMessagingTemplate.convertAndSend("/group/" + mensaje.getChat().getId().toString(), mensaje);
        return mensaje;
    }
}
