package dbp.connect.Notificaciones.Infrastructure;

import dbp.connect.Notificaciones.Domain.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {
}


