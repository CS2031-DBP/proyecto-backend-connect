package dbp.connect.Notificaciones.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {
/*
    @Autowired
    private NotificacionesService notificacionesService;

    @GetMapping
    public ResponseEntity<List<Notificaciones>> obtenerTodasLasNotificaciones() {
        List<Notificaciones> notificaciones = notificacionesService.obtenerTodasLasNotificaciones();
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificaciones> obtenerNotificacionPorId(@PathVariable Long id) {
        try {
            Notificaciones notificacion = notificacionesService.obtenerNotificacionPorId(id);
            return new ResponseEntity<>(notificacion, HttpStatus.OK);
        } catch (NotificacionesExceptions e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Notificaciones> crearNotificacion(@RequestBody Notificaciones notificacion) {
        Notificaciones nuevaNotificacion = notificacionesService.crearNotificacion(notificacion);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificaciones> actualizarNotificacion(@PathVariable Long id, @RequestBody Notificaciones notificacion) {
        try {
            Notificaciones notificacionActualizada = notificacionesService.actualizarNotificacion(id, notificacion);
            return new ResponseEntity<>(notificacionActualizada, HttpStatus.OK);
        } catch (NotificacionesExceptions e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        try {
            notificacionesService.eliminarNotificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotificacionesExceptions e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/}
