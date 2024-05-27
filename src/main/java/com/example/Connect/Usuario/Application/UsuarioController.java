package com.example.Connect.Usuario.Application;

import com.example.Connect.Security.JwtUtil;
import com.example.Connect.Usuario.Dto.UsuarioCreateDto;
import com.example.Connect.Usuario.Dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioDto(@PathVariable Long id) {
        UsuarioDto usuarioDto = usuarioService.obtenerUsuarioDto(id);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<UsuarioDto> getUserInfo(@RequestHeader("Authorization") String token) {
        UsuarioDto usuarioDto = usuarioService.obtenerUsuarioPorToken(token);
        return ResponseEntity.ok(usuarioDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteUsuario(@RequestHeader("Authorization") String token) {
        usuarioService.eliminarUsuarioPorToken(token);
        Map<String, String> response = new HashMap<>();
        response.put("message", "cuenta eliminada");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<UsuarioDto> updateUsuario(@RequestHeader("Authorization") String token, @RequestBody UsuarioCreateDto usuarioCreateDto) {
        UsuarioDto usuarioDto = usuarioService.actualizarUsuarioPorToken(token, usuarioCreateDto);
        return ResponseEntity.ok(usuarioDto);
    }

}
