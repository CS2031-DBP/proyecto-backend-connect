package com.example.Connect.Comentarios.Infraestructure;

import com.example.Connect.Comentarios.Domain.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
    List<Comentarios> findByPublicacionId(Long publicacionId);
    List<Comentarios> findByUsuarioId(Long usuarioId);
}

