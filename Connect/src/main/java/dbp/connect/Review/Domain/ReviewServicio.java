package dbp.connect.Review.Domain;

import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamientoServicio;
import dbp.connect.PublicacionAlojamiento.Exceptions.PublicacionAlojamientoNotFoundException;
import dbp.connect.PublicacionAlojamiento.Infrastructure.PublicacionAlojamientoRespositorio;
import dbp.connect.Review.DTOS.ReviewRequest;
import dbp.connect.Review.Infrastructure.ReviewRepository;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServicio {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PublicacionAlojamientoServicio publicacionAlojamientoServicio;
    @Autowired
    private PublicacionAlojamientoRespositorio publicacionAlojamientoRespositorio;
    @Autowired
    private UserRepository userRepository;

    public Long createReview(ReviewRequest reviewRequest) {
        Optional<PublicacionAlojamiento> publicacionAlojamiento = publicacionAlojamientoRespositorio.findById(reviewRequest.getPublicacionId());
        if (publicacionAlojamiento.isEmpty()) {
            throw new PublicacionAlojamientoNotFoundException("Publicacion no encontrada");
        }
        Review review = new Review();
        review.setCalificacion(reviewRequest.getRating());
        User autorReview = userRepository.findById(reviewRequest.getAutorId()).
                orElseThrow(()->new EntityNotFoundException("Autor no encontrado"));
        review.setAutor(autorReview);


    }
}
