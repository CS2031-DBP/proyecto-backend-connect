package dbp.connect.Notificaciones.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionesService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    public List<Notificaciones> obtenerTodasLasNotificaciones() {
        return notificacionesRepository.findAll();
    }

    public Notificaciones obtenerNotificacionPorId(Long id) throws NotificacionesExceptions {
        Optional<Notificaciones> notificacion = notificacionesRepository.findById(id);
        if (notificacion.isPresent()) {
            return notificacion.get();
        } else {
            throw new NotificacionesExceptions("Notificación no encontrada");
        }
    }

    public Notificaciones crearNotificacion(Notificaciones notificacion) {
        return notificacionesRepository.save(notificacion);
    }

    public Notificaciones actualizarNotificacion(Long id, Notificaciones notificacion) throws NotificacionesExceptions {
        if (notificacionesRepository.existsById(id)) {
            notificacion.setId(id);
            return notificacionesRepository.save(notificacion);
        } else {
            throw new NotificacionesExceptions("Notificación no encontrada");
        }
    }

    public void eliminarNotificacion(Long id) throws NotificacionesExceptions {
        if (notificacionesRepository.existsById(id)) {
            notificacionesRepository.deleteById(id);
        } else {
            throw new NotificacionesExceptions("Notificación no encontrada");
        }
    }
}
