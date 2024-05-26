package com.example.Connect.Publicacion.Infraestructure;

import com.example.Connect.Publicacion.Domain.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    
    Optional<Publicacion> findById(Long id);
    @Query("SELECT p FROM Publicacion p ORDER BY p.fecha_publicacion DESC")
    Page<Publicacion> findAllOrderByFechaPublicacionDesc(Pageable pageable);
}
