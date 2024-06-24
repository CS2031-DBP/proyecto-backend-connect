package com.example.Connect.Comentarios.Application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Connect.Security.JwtUtil;

import com.example.Connect.Comentarios.Dto.ComentariosDto;
import com.example.Connect.Comentarios.Dto.ComentariosCreateDto;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

  private final ComentariosService comentariosService;

  public ComentariosController(ComentariosService comentariosService) {
    this.comentariosService = comentariosService;
  }


  @PostMapping("/create/publicacion/{publicacionId}")
  public ResponseEntity<ComentariosDto> createComentarios(
      @RequestHeader("Authorization") String token,
      @Valid @RequestBody ComentariosCreateDto comentariosCreateDto,
      @PathVariable Long publicacionId
  ){
    ComentariosDto comentariosDto = comentariosService.createComentarios(
        comentariosCreateDto,
        token,
        publicacionId
    );
    return ResponseEntity.ok(comentariosDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ComentariosDto> getComentarios(
      @PathVariable Long id
  ){
    ComentariosDto comentariosDto = comentariosService.getComentarios(id);
    return ResponseEntity.ok(comentariosDto);
  }

  @GetMapping("/all/publicacion/{id}")
  public ResponseEntity<List<ComentariosDto>> getAllComentarios(
      @PathVariable Long id
  ){
    List<ComentariosDto> comentariosDto = comentariosService.getAllComentarios(id);
    return ResponseEntity.ok(comentariosDto);
  }

  @GetMapping("/all/usuario/{id}")
  public ResponseEntity<List<ComentariosDto>> getAllComentariosByUsuario(
      @PathVariable Long id
  ){
    List<ComentariosDto> comentariosDto = comentariosService.getAllComentariosByUsuario(id);
    return ResponseEntity.ok(comentariosDto);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteComentarios(
      @PathVariable Long id,
      @RequestHeader("Authorization") String token
  ) {
    comentariosService.deleteComentarios(id, token);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<ComentariosDto> updateComentarios(
      @PathVariable Long id,
      @Valid @RequestBody ComentariosCreateDto comentariosCreateDto,
      @RequestHeader("Authorization") String token
  ){
    ComentariosDto comentariosDto = comentariosService.updateComentarios(id, comentariosCreateDto, token);
    return ResponseEntity.ok(comentariosDto);
  }
}
