package dbp.connect.Comentarios.Aplication;


import dbp.connect.Comentarios.DTOS.ComentarioDto;
import dbp.connect.Comentarios.Domain.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/{id}")
    public ComentarioDto getComentario(@PathVariable Long id) {
        return comentarioService.getComentarioDTO(id);
    }

    @PostMapping
    public ResponseEntity<ComentarioDto> agregarComentario(@RequestBody ComentarioDto comentarioDTO) {
        ComentarioDto coment = comentarioService.agregarComentario(comentarioDTO);
        return ResponseEntity.ok(coment); ;
    }

    @PostMapping("/{parentId}/respuestas")
    public ComentarioDto agregarRespuesta(@PathVariable Long parentId, @RequestBody ComentarioDTO comentarioDTO) {
        return comentarioService.agregarRespuesta(parentId, comentarioDTO);
    }

    @GetMapping("/{id}/respuestas")
    public List<ComentarioDto> getRespuestas(@PathVariable Long id) {
        return comentarioService.getRespuestas(id);
    }

}
