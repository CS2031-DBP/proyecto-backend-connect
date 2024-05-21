package dbp.connect.PublicacionAlojamiento.Aplication;

import dbp.connect.Alojamiento.DTOS.AlojamientoRequest;
import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.PublicacionAlojamiento.DTOS.PostPublicacionAlojamientoDTO;
import dbp.connect.PublicacionAlojamiento.DTOS.ResponsePublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamientoServicio;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
