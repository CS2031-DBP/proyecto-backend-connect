package dbp.connect.Mensaje.Aplication;

import dbp.connect.Mensaje.DTOS.ContentDTO;
import dbp.connect.Mensaje.DTOS.DTOMensajePost;
import dbp.connect.Mensaje.DTOS.MensajeResponseDTO;
import dbp.connect.Mensaje.Domain.MensajeServicio;

import dbp.connect.User.DTO.UserProfileDTO;
import dbp.connect.User.Domain.UserService;
import dbp.connect.User.Exceptions.BadCredentialException;
import dbp.connect.User.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController("api/messages")
public class MensajeController {
    @Autowired
    private MensajeServicio mensajeServicio;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<MensajeResponseDTO> createMensaje(@RequestBody DTOMensajePost mensaje,
                                                            @RequestHeader ("Authorization") String token)
            throws URISyntaxException, BadCredentialException, UserException {
        UserProfileDTO profileDTO = userService.finddUserProfile(token);
        mensaje.setUserId(profileDTO.getId());
        MensajeResponseDTO result = mensajeServicio.sendMessage(mensaje);
        return ResponseEntity.created(new URI("/api/mensajes/" + result.getId())).build();
    }

    @PatchMapping("/{chatId}/modificar")
    public ResponseEntity<Void> updateMensaje(@PathVariable Long chatId, @RequestBody ContentDTO contenido) throws URISyntaxException {

        mensajeServicio.modificarMensajeContenido(chatId, contenido);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{chatId}/mensajes/{mensajeId}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long chatId, @PathVariable Long mensajeId) {
        mensajeServicio.deleteMensajeById(chatId,mensajeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{charId}/mensajes")
    public ResponseEntity<Page<MensajeResponseDTO>> getAllMensajes(@PathVariable Long chatId,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        return ResponseEntity.ok(mensajeServicio.obtenerTodosLosMensajesDeUnChat(chatId,page,size));

    }

    @GetMapping("/{charId}/{mensajeId}")
    public ResponseEntity<MensajeResponseDTO> getMensaje(@PathVariable Long charId,
                                                         @PathVariable Long mensajeId) {

        return ResponseEntity.ok(mensajeServicio.obtenerMensajePorchatIdYMensajeId(charId,mensajeId));
    }


}
