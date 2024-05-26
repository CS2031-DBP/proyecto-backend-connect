package com.example.forutec2.Usuario.Infraestructure;

import com.example.forutec2.Usuario.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findById(Long id);
}

