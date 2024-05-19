package dbp.connect.Notificaciones.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class NotificacionesExceptions extends Exception {
    public NotificacionesExceptions(String mensaje) {
        super(mensaje);
    }
}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotificacionesExceptions.class)
    public ResponseEntity<String> handleNotificacionesException(NotificacionesExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
