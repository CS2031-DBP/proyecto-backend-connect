package com.example.Connect.Usuario.Application;

import com.example.Connect.Mail.MailRegistroEvent;
import com.example.Connect.Security.JwtUtil;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Dto.AuthRequest;
import com.example.Connect.Usuario.Dto.AuthResponse;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.Connect.Usuario.Dto.UsuarioCreateDto;
import com.example.Connect.Usuario.Dto.UsuarioDto;

import com.example.Connect.Exception.CustomException;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getContrasenia())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuario usuario = usuarioRepository.findByCorreo(authRequest.getCorreo()).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
            String jwt = jwtUtil.generateToken(usuario.getId(), usuario.getRol().name());
            return new AuthResponse(jwt);
        } catch (AuthenticationException e) {
            throw new CustomException(401, "Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDto> createUsuario(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto) {
        UsuarioDto usuarioDto = usuarioService.createUsuario(usuarioCreateDto);
        eventPublisher.publishEvent(new MailRegistroEvent(usuarioDto.getCorreo()));
        return ResponseEntity.ok(usuarioDto);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Authorization header missing or invalid");
        }

        String jwt = authorizationHeader.substring(7);
        jwtUtil.invalidateToken(jwt);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
