package dbp.connect.PublicacionAlojamiento.Aplication;


import dbp.connect.PublicacionAlojamiento.DTOS.PostPublicacionAlojamientoDTO;
import dbp.connect.PublicacionAlojamiento.DTOS.ResponsePublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamientoServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/publicacionAlojamiento")
public class PublicacionAlojamientoController {
    @Autowired
    private PublicacionAlojamientoServicio publicacionAlojamientoServicio;
    @PostMapping()
    public ResponseEntity<ResponsePublicacionAlojamiento> crearPublicacionAlojamiento(@Valid @RequestBody PostPublicacionAlojamientoDTO publicacionAlojamientoDTO) {
        ResponsePublicacionAlojamiento createdPublicacionAlojamiento = publicacionAlojamientoServicio.guardarPublicacionAlojamiento(publicacionAlojamientoDTO);
        return ResponseEntity.created(URI.create("/alojamiento/"+createdPublicacionAlojamiento.getId())).body(createdPublicacionAlojamiento);
    }
    @GetMapping("/{publicacionId}")
    public ResponseEntity<ResponsePublicacionAlojamiento> consultarPublicacionAlojamiento(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(publicacionAlojamientoServicio.getPublicacionId(publicacionId));
    }
   /* @GetMapping("{userId}")
    public ResponseEntity<Page<ResponsePublicacionAlojamiento>> consultarPorPublicacionParaUsuario(@PathVariable Long userId,
                                                                                           @RequestParam int page, int size) {

        return ResponseEntity.ok(publicacionAlojamientoServicio.getPublicacionRecomendadas(userId,page,size));
    }
    @GetMapping("/{propietarioId}")
    public ResponseEntity<Page<ResponsePublicacionAlojamiento>> consultarPorPublicacionParaPropietario(@PathVariable Long propietarioId,
                                                                                           @RequestParam int page, int size) {

        return ResponseEntity.ok(publicacionAlojamientoServicio.getPublicacionRecomendadas(userId,page,size));
        }
     @GetMapping("/ubicacion/{propietarioId}")
    public ResponseEntity<Page<ResponsePublicacionAlojamiento>> consultarPorPublicacionParaPropietario(@PathVariable Long propietarioId, @RequestParam double latitude, @RequestParam Double longitud,
                                                                                           @RequestParam int page, int size) {

        return ResponseEntity.ok(publicacionAlojamientoServicio.getPublicacionRecomendadas(userId,page,size));
        }
    */
    @PatchMapping("/publicacionId")
    public ResponseEntity<Void> actualizarTItulo(@PathVariable Long publicacionId, @RequestBody  String titulo){
        publicacionAlojamientoServicio.actualizarTituloAlojamiento(publicacionId, titulo);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{publicacionId}")
    public ResponseEntity<Void> eliminarPublicacionAlojamiento(@PathVariable Long publicacionId) {
        publicacionAlojamientoServicio.eliminarPublicacion(publicacionId);
        return ResponseEntity.noContent().build();
    }
}
