
package com.example.Connect.Review.Infraestructure;

import com.example.Connect.Review.Domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.pAlojamiento.id = :pAlojamientoId")
    List<Review> findByPAlojamientoId(@Param("pAlojamientoId") Long pAlojamientoId);
    List<Review> findByUsuarioId(Long usuarioId);
}

