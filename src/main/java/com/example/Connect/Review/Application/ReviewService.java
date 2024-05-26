package com.example.forutec2.Review.Application;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.forutec2.Security.JwtUtil;

import com.example.forutec2.Exception.CustomException;

import com.example.forutec2.Review.Domain.Review;
import com.example.forutec2.Publicacion.Domain.P_Alojamiento;
import com.example.forutec2.Usuario.Domain.Usuario;

import com.example.forutec2.Review.Infraestructure.ReviewRepository;
import com.example.forutec2.Publicacion.Infraestructure.P_AlojamientoRepository;
import com.example.forutec2.Usuario.Infraestructure.UsuarioRepository;

import com.example.forutec2.Review.Dto.ReviewDto;
import com.example.forutec2.Review.Dto.ReviewCreateDto;

@Service
public class ReviewService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private P_AlojamientoRepository p_alojamientoRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public ReviewDto createReview(
    ReviewCreateDto reviewCreateDto,
    Long id,
    Long userId
  ) {

    P_Alojamiento p_alojamiento = p_alojamientoRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Alojamiento no encontrado"));

    Usuario usuario = usuarioRepository.findById(userId)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    Review review = new Review();
    review.setUsuario(usuario);
    review.setBody(reviewCreateDto.getBody());
    review.setCalificacion(reviewCreateDto.getCalificacion());
    review.setPAlojamiento(p_alojamiento);

    try {
      Review reviewGuardado = reviewRepository.save(review); 

      return new ReviewDto(
        reviewGuardado.getId(),
        reviewGuardado.getUsuario().getId(),
        reviewGuardado.getBody(),
        reviewGuardado.getCalificacion(),
        reviewGuardado.getPAlojamiento().getId(),
        reviewGuardado.getFecha_publicacion()
      );

    } catch (DataIntegrityViolationException e) {
      throw new CustomException(400, "Review no creado");
    }
  }

  public ReviewDto getReview(Long id) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Review no encontrado"));

    return new ReviewDto(
      review.getId(),
      review.getUsuario().getId(),
      review.getBody(),
      review.getCalificacion(),
      review.getPAlojamiento().getId(),
      review.getFecha_publicacion()
    );
  }

  public List<ReviewDto> getReviews(Long id) {
    P_Alojamiento p_alojamiento = p_alojamientoRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Publicacion de alojamiento no encontrado"));
    
    List<Review> reviews = reviewRepository.findByPAlojamientoId(p_alojamiento.getId());

    return reviews.stream().map(review -> new ReviewDto(
      review.getId(),
      review.getUsuario().getId(),
      review.getBody(),
      review.getCalificacion(),
      review.getPAlojamiento().getId(),
      review.getFecha_publicacion()
    )).collect(Collectors.toList());
  }

  public List<ReviewDto> getReviewsByUser(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
    
    List<Review> reviews = reviewRepository.findByUsuarioId(usuario.getId());

    return reviews.stream().map(review -> new ReviewDto(
      review.getId(),
      review.getUsuario().getId(),
      review.getBody(),
      review.getCalificacion(),
      review.getPAlojamiento().getId(),
      review.getFecha_publicacion()
    )).collect(Collectors.toList());
  }

  public void deleteReview(Long id, String token) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));

    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Review no encontrado"));

    if (review.getUsuario().getId() != userId) {
      throw new CustomException(403, "No tienes permiso para eliminar este review");
    }

    reviewRepository.delete(review);
  }

  public ReviewDto updateReview(Long id, ReviewCreateDto reviewCreateDto, String token) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));

    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Review no encontrado"));

    if (review.getUsuario().getId() != userId) {
      throw new CustomException(403, "No tienes permiso para editar este review");
    }

    review.setBody(reviewCreateDto.getBody());
    review.setCalificacion(reviewCreateDto.getCalificacion());

    Review reviewGuardado = reviewRepository.save(review);

    return new ReviewDto(
      reviewGuardado.getId(),
      reviewGuardado.getUsuario().getId(),
      reviewGuardado.getBody(),
      reviewGuardado.getCalificacion(),
      reviewGuardado.getPAlojamiento().getId(),
      reviewGuardado.getFecha_publicacion()
    );
  }
}
  
