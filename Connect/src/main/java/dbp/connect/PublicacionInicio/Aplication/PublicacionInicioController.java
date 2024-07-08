package dbp.connect.PublicacionInicio.Aplication;


import dbp.connect.PublicacionInicio.DTOS.PostInicioDTO;
import dbp.connect.PublicacionInicio.DTOS.PublicacionInicioResponseDTO;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("/api/publicacionInicio")
public class PublicacionInicioController {
    @Autowired
    private PublicacionInicioServicio publicacionInicioServicio;

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
                                                                                          @RequestParam Integer size){
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublicacionByUsuario(usuarioId,page,size));
    }
    @PatchMapping("/{usuarioId}/{publicacionId}/contenido")
    public ResponseEntity<PublicacionInicioResponseDTO> cambiarContenido(@PathVariable Long usuarioId,
                                                                     @PathVariable Long publicacionId,
                                                                     @RequestParam String contenido){

    return ResponseEntity.ok(publicacionInicioServicio.actualizarContenido(usuarioId, publicacionId, contenido));}

    @PatchMapping("/{usuarioId}/{publicacionId}/multimedia")
    public ResponseEntity<PublicacionInicioResponseDTO> cambiarMultimedia(@PathVariable Long usuarioId,
                                                                      @PathVariable Long publicacionId,
                                                                      @RequestParam List<MultipartFile> multimedia){
    return ResponseEntity.ok(publicacionInicioServicio.actualizarMultimedia(usuarioId, publicacionId, multimedia));}
/*
    @GetMapping("/buscar")
    public ResponseEntity<Page<PublicacionInicioResponseDTO>> buscarPublicaciones(@RequestParam String palabraClave,
                                                                                  @RequestParam Integer page,
                                                                                  @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(publicacionInicioServicio.buscarPorPalabraClave(palabraClave, pageable));
    }

    @GetMapping("/amigos/{usuarioId}")
    public ResponseEntity<Page<PublicacionInicioResponseDTO>> obtenerPublicacionesAmigos(@PathVariable Long usuarioId,
                                                                                         @RequestParam Integer page,
                                                                                         @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublicacionesAmigos(usuarioId, pageable));
    }



    @GetMapping("/recientes")
    public ResponseEntity<Page<PublicacionInicioResponseDTO>> obtenerPublicacionesRecientes(@RequestParam Integer page,
                                                                                            @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublicacionesRecientes(pageable));
    }
    @GetMapping("/fecha")
    public ResponseEntity<Page<PublicacionInicioResponseDTO>> obtenerPublicacionesPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Pageable pageable) {
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublicacionesPorFecha(fechaInicio, fechaFin, pageable));
    }
   */
}



