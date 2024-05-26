package com.example.forutec2.Usuario.Application;

import com.example.forutec2.Security.JwtUtil;
import com.example.forutec2.Usuario.Domain.Usuario;
import com.example.forutec2.Usuario.Dto.AuthRequest;
import com.example.forutec2.Usuario.Dto.AuthResponse;
import com.example.forutec2.Usuario.Infraestructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.forutec2.Usuario.Dto.UsuarioCreateDto;
import org.springframework.validation.annotation.Validated;
import com.example.forutec2.Usuario.Dto.UsuarioDto;

import com.example.forutec2.Exception.CustomException;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

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
