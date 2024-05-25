package dbp.connect.MensajeIndividual.Aplication;

import dbp.connect.MensajeIndividual.DTOS.ContentDTO;
import dbp.connect.MensajeIndividual.DTOS.DTOMensajePost;
import dbp.connect.MensajeIndividual.DTOS.MensajeResponseDTO;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividualService;
import jdk.jfr.Timespan;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController("mensaje")
public class MensajeIndividualController {
    @Autowired
    private MensajeIndividualService  mensajeIndividualService;
    @PostMapping("/crear")
    public ResponseEntity<MensajeResponseDTO> createMensaje(@RequestBody DTOMensajePost mensaje) throws URISyntaxException {

        MensajeResponseDTO result = mensajeIndividualService.save(mensaje);
        return ResponseEntity.created(new URI("/api/mensajes/" + result.getId());
    }

    @PatchMapping("/{chatId}/modificar")
    public ResponseEntity<Void> updateMensaje(@PathVariable Long chatId, @RequestBody ContentDTO contenido) throws URISyntaxException {

        mensajeIndividualService.modificarMensajeContenido(chatId, contenido);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{chatId}/mensajes/{mensajeId}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long chatId, @PathVariable Long mensajeId) {
        mensajeIndividualService.deleteMensajeById(chatId,mensajeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{charId}/mensajes")
    public ResponseEntity<Page<MensajeResponseDTO>> getAllMensajes(@PathVariable Long chatId,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        return ResponseEntity.ok(mensajeIndividualService.obtenerTodosLosMensajesDeUnChat(chatId,page,size));

    }


    @GetMapping("/{charId}/{mensajeId}")
    public ResponseEntity<MensajeResponseDTO> getMensaje(@PathVariable Long charId,
                                                         @PathVariable Long mensajeId) {

        return ResponseEntity.ok(mensajeIndividualService.obtenerMensajePorchatIdYMensajeId(charId,mensajeId));
    }


}
