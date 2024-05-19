package dbp.connect.Alojamiento.Aplication;


import dbp.connect.Alojamiento.DTOS.AlojamientoRequest;
import dbp.connect.Alojamiento.DTOS.ContenidoDTO;
import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Domain.AlojamientoServicio;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimediaServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alojamiento")
public class AlojamientoController {
    @Autowired
    AlojamientoServicio alojamientoServicio;
    @Autowired
    private AlojamientoMultimediaServicio alojamientoMultimediaServicio;

    @PostMapping("/crear")
    public ResponseEntity<Alojamiento> crearAlojamiento(@Valid @RequestBody AlojamientoRequest alojamientoRequest) {
        Alojamiento createdAlojamiento = alojamientoServicio.guardarAlojamiento(alojamientoRequest);
        return ResponseEntity.created(URI.create("/alojamiento/"+createdAlojamiento.getId())).body(createdAlojamiento);
    }
    @GetMapping("/{alojamientoId}")
    public ResponseEntity<Alojamiento> getAlojamiento(@PathVariable Long alojamientoId) {
        Alojamiento alojamiento= alojamientoServicio.obtenerAlojamiento(alojamientoId);
        return ResponseEntity.ok().body(alojamiento);
    }
    @PatchMapping("/imagen/{alojamientoId}/{imagenId}")
    public ResponseEntity<Void> actualizarImagen(@PathVariable Long alojamientoId, @PathVariable Long imagenId, @RequestBody byte[] imagen) {
        alojamientoMultimediaServicio.modificarImagen(alojamientoId, imagenId, imagen);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/eliminar/{alojamientoId}")
    public ResponseEntity<Void> eliminarAlojamiento(@PathVariable Long alojamientoId) {
        alojamientoServicio.eliminarById(alojamientoId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/alojamientos/{alojamientoId}")
    public ResponseEntity<Void> actualizarPrecio(@PathVariable Long alojamientoId, @RequestBody Double precio) {
        alojamientoServicio.modificarPrecio(alojamientoId,precio);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/disponibilidad/{alojamientoId}")
    public ResponseEntity<Void> actualizarEstado(@PathVariable Long alojamientoId) {
        alojamientoServicio.actualizarEstadoAlojamiento(alojamientoId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/alojamientos/descripcion/{alojamientoId}")
    public ResponseEntity<Void> actualizarDescripcion(@PathVariable Long alojamientoId,
                                                      @Valid @RequestBody ContenidoDTO contenidoDTO) {
        alojamientoServicio.actualizarDescripcionAlojamiento(alojamientoId, contenidoDTO);
        return ResponseEntity.ok().build();
    }
}
