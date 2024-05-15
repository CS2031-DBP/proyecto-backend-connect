package dbp.connect.Comentarios.Aplication;


import dbp.connect.Comentarios.DTOS.ComentarioDto;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.Comentarios.Domain.ComentarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    ModelMapper modelMapper;
    @GetMapping("/{id}")
    public ResponseEntity<Page<ComentarioDto>> getComentario(
                                                           @PathVariable Long comentarioId,
                                                          @RequestParam int page,
                                                          @RequestParam int size) {
        return ResponseEntity.ok(comentarioService.getComentario(comentarioId, page, size));
    }

    @PostMapping
    public ResponseEntity<Void> agregarComentario(@RequestBody ComentarioDto comentarioDTO) {
        Long ComentId = comentarioService.createNewComentario(comentarioDTO);
        URI uriLocation = URI.create("/comentario/" + ComentId);
        return ResponseEntity.created(uriLocation).build(); ;
    }

    @PostMapping("/{parentId}/respuestas")
    public ResponseEntity<Void> agregarRespuesta(@PathVariable Long parentId,
                                                 @RequestBody ComentarioDto comentarioDTO) {
        Long ComentId = comentarioService.createNewResponseComentaryId(parentId,comentarioDTO);
        URI uriLocation = URI.create("/comentario/response/" + ComentId);
        return ResponseEntity.created(uriLocation).build();
    }

    @GetMapping("/{id}/respuestas")
    public ResponseEntity<Page<List<ComentarioDto>>> getRespuestas(@PathVariable Long id,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size) {
        return ResponseEntity.ok(comentarioService.getResponseComentarios(id, page, size));
    }
    @DeleteMapping("{ComentarioId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable Long ComentarioId) {
        comentarioService.deleteComentarioById(ComentarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{parentID}/respuestas/{comentarioId}")
    public ResponseEntity<String> eliminarRespuesta(@PathVariable Long parentID,
                                                    @PathVariable Long comentarioId) {
        comentarioService.deleteComentarioRespuestaById(parentID,comentarioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{parentID}/respuestas/{comentarioId}")
    public ResponseEntity<Void> actualizarRespuesComentarioId(@PathVariable Long parentID,
                                                                       @PathVariable Long comentarioId,
                                                                       @RequestBody ComentarioDto comentarioDTO) {
        comentarioService.actualizarContenidoDeComentarioRespuesta(parentID,comentarioId,comentarioDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{comentarioId}")
    public ResponseEntity<Void> actualizarComentario(@PathVariable Long comentarioId,
                                                     @RequestBody ComentarioDto comentarioDTO) {
        comentarioService.actualizarComentario(comentarioId,comentarioDTO);
        return ResponseEntity.ok().build();
    }
}
