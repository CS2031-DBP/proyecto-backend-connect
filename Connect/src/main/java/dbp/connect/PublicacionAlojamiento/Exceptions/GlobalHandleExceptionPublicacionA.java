package dbp.connect.PublicacionAlojamiento.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleExceptionPublicacionA {
    @ExceptionHandler(PublicacionAlojamientoNotFoundException.class)
    public ResponseEntity<String> handleExceptionNotFound(PublicacionAlojamientoNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
