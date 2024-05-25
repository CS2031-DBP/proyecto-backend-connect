package dbp.connect;

import dbp.connect.Alojamiento.Excepciones.DescripcionIgualException;
import dbp.connect.Comentarios.Excepciones.ComentarioNoEncontradoException;
import dbp.connect.Comentarios.Excepciones.PublicacionNoEncontradoException;
import dbp.connect.Excepciones.NoEncontradoException;
import dbp.connect.PublicacionInicio.Exceptions.UsuarioNoCoincideId;
import dbp.connect.Review.Exceptions.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ComentarioNoEncontradoException.class)
    public ResponseEntity<String> exception(ComentarioNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PublicacionNoEncontradoException.class)
    public ResponseEntity<String> exception(PublicacionNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DescripcionIgualException.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(DescripcionIgualException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<String> handleReviewNotFounException(ReviewNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoEncontradoException.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(NoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNoCoincideId.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(UsuarioNoCoincideId ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
