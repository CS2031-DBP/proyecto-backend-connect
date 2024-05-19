package dbp.connect.Review.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class ReviewException extends Exception {
    public ReviewException(String mensaje) {
        super(mensaje);
    }
}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<String> handleReviewException(ReviewException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
