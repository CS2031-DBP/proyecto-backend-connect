package dbp.connect.Review.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> obtenerTodasLasReseñas() {
        return reviewRepository.findAll();
    }

    public Review obtenerReseñaPorId(Long id) throws ReviewNotFoundException {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new ReviewNotFoundException("Reseña no encontrada");
        }
    }

    public Review crearReseña(Review review) {
        return reviewRepository.save(review);
    }

    public Review actualizarReseña(Long id, Review review) throws ReviewNotFoundException {
        if (reviewRepository.existsById(id)) {
            review.setId(id);
            return reviewRepository.save(review);
        } else {
            throw new ReviewNotFoundException("Reseña no encontrada");
        }
    }

    public void eliminarReseña(Long id) throws ReviewNotFoundException {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        } else {
            throw new ReviewNotFoundException("Reseña no encontrada");
        }
    }
}
