package dbp.connect.Review.Domain;

import dbp.connect.Comentarios.DTOS.ComentarioRespuestaDTO;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Domain.PublicacionAlojamientoServicio;
import dbp.connect.PublicacionAlojamiento.Exceptions.PublicacionAlojamientoNotFoundException;
import dbp.connect.PublicacionAlojamiento.Infrastructure.PublicacionAlojamientoRespositorio;
import dbp.connect.Review.DTOS.ResponseReviewDTO;
import dbp.connect.Review.DTOS.ReviewRequest;
import dbp.connect.Review.Infrastructure.ReviewRepository;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        PublicacionAlojamiento publicacion = publicacionAlojamiento.get();
        User autorReview = userRepository.findById(reviewRequest.getAutorId()).
                orElseThrow(()->new EntityNotFoundException("Autor no encontrado"));

        Review review = new Review();
        review.setCalificacion(reviewRequest.getRating());
        review.setAutor(autorReview);
        review.setComentario(reviewRequest.getContent());
        review.setPublicacionAlojamiento(publicacion);
        review.setFecha(LocalDateTime.now(ZoneId.systemDefault()));
        reviewRepository.save(review);
        return review.getId();
    }

    public Page<ResponseReviewDTO> obtenerReviewsPorPublicacionId(Long publicacionAId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecha"));
        reviewRepository.findById(publicacionAId).orElseThrow(() -> new EntityNotFoundException("Review not found"));
        Page<Review> reviews = reviewRepository.findByPublicacionAlojamientoId(publicacionAId, pageable);
        List<ResponseReviewDTO> reviewsContent = new ArrayList<>(reviews.map(this::mapToResponseDTO).getContent());

        while (reviewsContent.size() < size) {
            ResponseReviewDTO defaultReview = reviewsContent.get(reviewsContent.size() - 1);
            reviewsContent.add(defaultReview);
        }

        return new PageImpl<>(reviewsContent, pageable, reviews.getTotalElements());
    }

    private ResponseReviewDTO mapToResponseDTO(Review review) {
        ResponseReviewDTO dto = new ResponseReviewDTO();
        dto.setAutorFullname(review.getAutor().getFullname());
        dto.setContenido(review.getComentario());
        dto.setCalificacion(review.getCalificacion());
        if (review.getAutor().getFoto() != null) {
            dto.setAutorFoto(review.getAutor().getFoto());
        } else {
            dto.setAutorFoto(null);
        }
        dto.setDateTime(review.getFecha().atZone(ZoneId.systemDefault()));
        return dto;
    }

}
