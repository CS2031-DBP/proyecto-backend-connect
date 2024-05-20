package dbp.connect.Comentarios.Aplication;


import dbp.connect.Comentarios.DTOS.ComentarioDto;
import dbp.connect.Comentarios.DTOS.ComentarioRespuestaDTO;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.Comentarios.Domain.ComentarioService;
import jakarta.validation.Valid;
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

    @PostMapping("/{publicacionId}")
    public ResponseEntity<Comentario> agregarComentario(@PathVariable Long publicacionId,@Valid @RequestBody ComentarioDto comentarioDTO) {
        Comentario comentario = comentarioService.createNewComentario(publicacionId,comentarioDTO);
        return ResponseEntity.created(URI.create("/comentarios/" + comentario.getId())).build();
    }
    @PostMapping("/{publicacionId}/comments/{parentId}/respuestas")
    public ResponseEntity<Page<Comentario>> agregarRespuesta(@PathVariable Long publicacionId,
                                                             @PathVariable Long parentId,
                                                             @RequestBody ComentarioDto comentarioDTO) {
        Comentario comentario = comentarioService.createNewComentarioHijo(publicacionId, parentId,comentarioDTO);
        return ResponseEntity.created(URI.create(parentId+"/comentarios/" + comentario.getId())).build();

    }
    @GetMapping("/{publicacionId}/comentario")
    public ResponseEntity<Page<ComentarioRespuestaDTO>> getComentario(
                                                           @PathVariable Long publicacionId,
                                                          @RequestParam int page,
                                                          @RequestParam int size) {
        return ResponseEntity.ok(comentarioService.getComentario(publicacionId, page, size));
    }

    @GetMapping("/{publicacionId}/comentario/{parentId}/respuestas")
    public ResponseEntity<Page<ComentarioRespuestaDTO>> getRespuestas(@PathVariable Long publicacionId,
                                                                   @PathVariable Long parentId,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size) {
        return ResponseEntity.ok(comentarioService.getResponseComentarios(publicacionId,parentId, page, size));
    }
    @DeleteMapping("{publicacionID}/comentarios/{ComentarioId}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long publicacionID,@PathVariable Long ComentarioId) {
        comentarioService.deleteComentarioById(publicacionID,ComentarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{publicacionID}/comentarios/{parentID}/respuestas/{comentarioId}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long publicacionID,
                                                    @PathVariable Long parentID,
                                                    @PathVariable Long comentarioId) {
        comentarioService.deleteComentarioRespuestaById(publicacionID,parentID,comentarioId);
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
