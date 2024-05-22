package dbp.connect.PublicacionInicio.Aplication;


import dbp.connect.PublicacionInicio.DTOS.PostInicioDTO;
import dbp.connect.PublicacionInicio.DTOS.PublicacionInicioResponseDTO;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("/publicacionInicio")
public class PublicacionInicioController {
    @Autowired
    private  PublicacionInicioServicio publicacionInicioServicio;
    @PostMapping()
    public ResponseEntity<Void> crearPublicacionInicio(@Valid @RequestBody PostInicioDTO postInicioDTO) {
        publicacionInicioServicio.createPostInicioDTO(postInicioDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{publicacionId}")
    public ResponseEntity<PublicacionInicioResponseDTO> obtenerPublicacionInicio(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublciacionesInicio(publicacionId));
    }
    @DeleteMapping("/{publicacionId}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long publicacionId) {
        publicacionInicioServicio.eliminarPublicacionInicio(publicacionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<PublicacionInicioResponseDTO>> obtenerPublicacionesUsuario(@PathVariable Long usuarioId,
                                                                                          @RequestParam Integer page,
                                                                                          @@RequestParam Integer size){
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublicacionByUsuario(usuarioId,page,size));
    }
    @PatchMapping("/{usuarioId}/{publicacionId}/contenido")
    public ResponseEntity<PublicacionInicioResponseDTO> cambiarContenido(@PathVariable Long usuarioId,
                                                 @PathVariable Long publicacionId,
                                                 @RequestParam String contenido){

        return ResponseEntity.accepted(publicacionInicioServicio.actualizarContenido(usuarioId, publicacionId, contenido));
    }
    @PatchMapping("/{usuarioId}/{publicacionId}/multimedia")
    public ResponseEntity<PublicacionInicioResponseDTO> cambiarMultimedia(@PathVariable Long usuarioId,
                                                  @PathVariable Long publicacionId,
                                                  @RequestParam List<MultipartFile> multimedia){
        return ResponseEntity.accepted(publicacionInicioServicio.actualizarMultimedia(usuarioId, publicacionId, multimedia));
    }
    //Algunos endpoints para obtener por palabras que buscan.
    //Algunos para obtener por la lsita de amigos para la pagina de inicio.
    //Falta avanzar mas.
}
