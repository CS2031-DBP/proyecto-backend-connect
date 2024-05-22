package dbp.connect.PublicacionInicio.Aplication;


import dbp.connect.MultimediaInicio.Domain.MultimediaInicioServicio;
import dbp.connect.PublicacionInicio.DTOS.PostInicioDTO;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("/publicacionInicio")
public class PublicacionInicioController {
    @Autowired
    private final PublicacionInicioServicio publicacionInicioServicio;
    @Autowired

    public PublicacionInicioController() {
        this.publicacionInicioServicio = new PublicacionInicioServicio();
        this.multimediaInicioServicio = new MultimediaInicioServicio();
    }
    @PostMapping()
    public ResponseEntity<Void> crearPublicacionInicio(@RequestBody PostInicioDTO publicacionDTO,
                                                       @RequestParam("files") MultipartFile[] archivosMultimedia) {
        PublicacionInicio publicacionInicio = publicacionInicioServicio.createPostInicioDTO(publicacionDTO,archivosMultimedia);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{publicacionId}")
    public ResponseEntity<Page<PublicacionInicio>> obtenerPublicacionInicio(@PathVariable Long publicacionId,
                                                                            @RequestParam Integer page,
                                                                            @RequestParam Integer size) {
        return ResponseEntity.ok(publicacionInicioServicio.obtenerPublciacionesInicio(publicacionId,page,size));
    }
    @DeleteMapping("/{publicacionId}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long publicacionId) {
        publicacionInicioServicio.eliminarPublicacionInicio(publicacionId);
        return ResponseEntity.noContent().build();
    }



}
