package dbp.connect.Notificaciones.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;

    @GetMapping
    public List<Notificaciones> getAllNotificaciones() {
        return notificacionesService.getAllNotificaciones();
    }

    @PostMapping
    public Notificaciones createNotificaciones(@RequestBody Notificaciones notificaciones) {
        return notificacionesService.saveNotificaciones(notificaciones);
    }
}
