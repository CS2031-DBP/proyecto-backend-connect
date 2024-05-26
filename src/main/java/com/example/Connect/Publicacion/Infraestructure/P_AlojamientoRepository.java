package com.example.forutec2.Publicacion.Infraestructure;

import com.example.forutec2.Publicacion.Domain.P_Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface P_AlojamientoRepository extends JpaRepository<P_Alojamiento, Long> {
    Optional<P_Alojamiento> findById(Long id);
}
