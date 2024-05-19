package dbp.connect.Review.Aplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Review>> obtenerTodasLasReseñas() {
        List<Review> reviews = bookingService.obtenerTodasLasReseñas();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> obtenerReseñaPorId(@PathVariable Long id) {
        try {
            Review review = bookingService.obtenerReseñaPorId(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Review> crearReseña(@RequestBody Review review) {
        try {
            Review nuevaReseña = bookingService.crearReseña(review);
            return new ResponseEntity<>(nuevaReseña, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> actualizarReseña(@PathVariable Long id, @RequestBody Review review) {
        try {
            Review reseñaActualizada = bookingService.actualizarReseña(id, review);
            return new ResponseEntity<>(reseñaActualizada, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReseña(@PathVariable Long id) {
        try {
            bookingService.eliminarReseña(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
