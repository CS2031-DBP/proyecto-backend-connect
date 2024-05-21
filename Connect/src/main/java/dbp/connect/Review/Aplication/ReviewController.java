package dbp.connect.Review.Aplication;

import dbp.connect.Review.DTOS.ResponseReviewDTO;
import dbp.connect.Review.DTOS.ReviewRequest;
import dbp.connect.Review.Domain.Review;
import dbp.connect.Review.Domain.ReviewServicio;
import dbp.connect.Review.Infrastructure.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewServicio reviewServicio;

    @PostMapping()
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequest reviewDTO) {
        Long reviewId = reviewServicio.createReview(reviewDTO);
        URI location = URI.create("/review/" + reviewId);
        return ResponseEntity.created(location).build();
    }
    @GetMapping("{publicacionId}")
    public ResponseEntity<Page<ResponseReviewDTO>> obtenerReviews(@PathVariable Long publicacionId,
                                                                  @RequestParam Integer page,
                                                                  @RequestParam Integer size) {
        return ResponseEntity.ok(reviewServicio.obtenerReviewsPorPublicacionId(publicacionId,page,size));
    }
    /*
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
    }*/
}
