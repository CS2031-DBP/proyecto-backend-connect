package dbp.connect.Alojamiento.Aplication;


import dbp.connect.Alojamiento.DTOS.*;
import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Domain.AlojamientoServicio;
import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimediaServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alojamiento")
public class AlojamientoController {
    @Autowired
    AlojamientoServicio alojamientoServicio;
    @Autowired
    AlojamientoMultimediaServicio alojamientoMultimediaServicio;

    @PostMapping()
    public ResponseEntity<ResponseAlojamientoDTO> crearAlojamiento(@Valid @RequestBody AlojamientoRequest alojamientoRequest) {
         ResponseAlojamientoDTO createdAlojamiento =alojamientoServicio.guardarAlojamiento(alojamientoRequest);
        return ResponseEntity.created(URI.create("/alojamiento/"+createdAlojamiento.getId())).body(createdAlojamiento);
    }

    @GetMapping("/{alojamientoId}")
    public ResponseEntity<ResponseAlojamientoDTO> getAlojamiento(@PathVariable Long alojamientoId) {
        ResponseAlojamientoDTO alojamiento= alojamientoServicio.obtenerAlojamiento(alojamientoId);
        return ResponseEntity.ok().body(alojamiento);
    }

    @GetMapping("/multimedia/{alojamientoId}/{imagenId}")
    public ResponseEntity<ResponseMultimediaDTO> getMultimedia(@PathVariable Long alojamientoId, @PathVariable Long imagenId) {
        ResponseMultimediaDTO multimediaDTO= alojamientoMultimediaServicio.obtenerMultimedia(alojamientoId, imagenId);
        return ResponseEntity.ok().body(multimediaDTO);
    }

    @GetMapping("/{alojamientoId}/multimedia")
    public ResponseEntity<Page<ResponseMultimediaDTO>> getMultimedia(@PathVariable Long alojamientoId,
                                                                     @RequestParam int page,
                                                                     @RequestParam int size) {
        alojamientoMultimediaServicio.obtenerMultimediaPaginacion(alojamientoId, page, size);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/alojamientos/{propietarioId}")
    public ResponseEntity<Page<ResponseAlojamientoDTO>> getAlojamientos(@PathVariable Long propietarioId,
                                                                        @RequestParam int page,
                                                                        @RequestParam int size) {
        alojamientoServicio.obtenerAlojamientoPaginacion(propietarioId, page, size);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/imagen/{alojamientoId}/{imagenId}")
    public ResponseEntity<Void> actualizarImagen(@PathVariable Long alojamientoId, @PathVariable String imagenId, @RequestBody byte[] imagen) {
        alojamientoMultimediaServicio.modificarImagen(alojamientoId, imagenId, imagen);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{alojamientoId}")
    public ResponseEntity<Void> eliminarAlojamiento(@PathVariable Long alojamientoId) {
        alojamientoServicio.eliminarById(alojamientoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/alojamientos/{alojamientoId}")
    public ResponseEntity<Void> actualizarPrecio(@PathVariable Long alojamientoId,
                                                 @Valid @RequestBody PriceDTO precio) {
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

    @PatchMapping("/alojamientos/ubicacion/{alojamientoId}")
    public ResponseEntity<Void> actualizarUbicacion(@PathVariable Long alojamientoId,
                                                      @Valid @RequestBody UbicacionDTO ubicacionDTO) {
        alojamientoServicio.actualizarUbicacionAlojamiento(alojamientoId, ubicacionDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{alojamientoId}")
    public ResponseEntity<ResponseAlojamientoDTO> actualizarAlojamiento(@PathVariable Long alojamientoId,
                                                                        @Valid @RequestBody AlojamientoRequest alojamientoRequest) {
        alojamientoServicio.actualizarAlojamiento(alojamientoId, alojamientoRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/eliminar/imagen/{alojamientoId}/{imagenId}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Long alojamientoId, @PathVariable String imagenId) {
        alojamientoMultimediaServicio.eliminarArchivo(alojamientoId, imagenId);
        return ResponseEntity.noContent().build();
    }
}
