package dbp.connect.Alojamiento.Excepciones;

import dbp.connect.Excepciones.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParticularExceptionController {
    @ExceptionHandler(DescripcionIgualException.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(DescripcionIgualException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
