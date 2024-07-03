package dbp.connect.Review.Aplication;

import dbp.connect.Review.DTOS.ResponseReviewDTO;
import dbp.connect.Review.DTOS.ReviewRequest;
import dbp.connect.Review.Domain.ReviewServicio;
import dbp.connect.Review.Infrastructure.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewServicio reviewServicio;
    @PreAuthorize("hasRole('ROLE_TRAVELER') ")
    @PostMapping()
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequest reviewDTO) {
        Long reviewId = reviewServicio.createReview(reviewDTO);
        URI location = URI.create("/review/" + reviewId);
        return ResponseEntity.created(location).build();
    }
    @GetMapping("{publicacionAlojamientoId}")
    public ResponseEntity<Page<ResponseReviewDTO>> obtenerReviews(@PathVariable Long publicacionAlojamientoId,
                                                                  @RequestParam Integer page,
                                                                  @RequestParam Integer size) {
        return ResponseEntity.ok(reviewServicio.obtenerReviewsPorPublicacionId(publicacionAlojamientoId,page,size));
    }
    @GetMapping("{publicacionAlojamientoId}/{reviewId}")
    public ResponseEntity<ResponseReviewDTO> obtenerReview(@PathVariable Long publicacionAlojamientoId, Long reviewId){
        ResponseReviewDTO dto = reviewServicio.getReview(publicacionAlojamientoId,reviewId);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER') ")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> elimarReviewById(@PathVariable Long reviewId){
        reviewServicio.eliminarReseña(reviewId);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ROLE_TRAVELER') ")
    @PatchMapping("{publicacionAId}/{reviewId}")
    public ResponseEntity<Void> actualizarContenido(@PathVariable Long publicacionAId,
                                                    @PathVariable  Long reviewId,
                                                    @RequestParam String contenido){
        reviewServicio.actualizarContenido(publicacionAId,reviewId,contenido);
        return ResponseEntity.accepted().build();
    }
}
