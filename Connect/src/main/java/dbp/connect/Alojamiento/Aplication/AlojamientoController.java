package dbp.connect.Alojamiento.Aplication;


import dbp.connect.Alojamiento.DTOS.AlojamientoRequest;
import dbp.connect.Alojamiento.Domain.AlojamientoServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alojamiento")
public class AlojamientoController {
    @Autowired
    AlojamientoServicio alojamientoServicio;
    @PostMapping("/crear")
    public ResponseEntity<Void> crearAlojamiento(@Valid @RequestBody AlojamientoRequest alojamientoRequest) {
        alojamientoServicio.guardarAlojamiento(alojamientoRequest);
        return ResponseEntity.ok().build();

    }
}
