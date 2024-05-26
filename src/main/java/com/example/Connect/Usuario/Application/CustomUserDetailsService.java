package com.example.Connect.Usuario.Application;

import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Connect.Exception.CustomException;

import java.util.ArrayList;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new CustomException(404, "Not found"));
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getContrasenia(), new ArrayList<>());
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException(404, "Not found"));
        return new org.springframework.security.core.userdetails.User(usuario.getId().toString(), usuario.getContrasenia(), Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())));
    }
}
