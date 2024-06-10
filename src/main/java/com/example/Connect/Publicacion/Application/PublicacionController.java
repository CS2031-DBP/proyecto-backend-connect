package com.example.Connect.Publicacion.Application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Connect.Security.JwtUtil;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.example.Connect.Publicacion.Dto.PublicacionCreateDto;
import com.example.Connect.Publicacion.Dto.PublicacionDto;
import com.example.Connect.Publicacion.Dto.P_AlojamientoCreateDto;
import com.example.Connect.Publicacion.Dto.P_AlojamientoDto;
import com.example.Connect.Publicacion.Application.PublicacionService;

// import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

  private final PublicacionService publicacionService;

  public PublicacionController(PublicacionService publicacionService) {
    this.publicacionService = publicacionService;
  }

  @PostMapping("/create")
  public ResponseEntity<PublicacionDto> createPublicacion(
    @RequestHeader("Authorization") String token,
    @Valid @RequestBody PublicacionCreateDto publicacionCreateDto
  ){
    PublicacionDto publicacionDto = publicacionService.createPublicacion(
        publicacionCreateDto,
        token
    );
    return ResponseEntity.ok(publicacionDto);
  }

  @PostMapping("/protected/create")
  public ResponseEntity<P_AlojamientoDto> createAlojamiento(
      @RequestHeader("Authorization") String token,
      @Valid @RequestBody P_AlojamientoCreateDto alojamientoCreateDto
  ){
    P_AlojamientoDto alojamientoDto = publicacionService.createAlojamiento(
        alojamientoCreateDto,
        token
    );
    return ResponseEntity.ok(alojamientoDto);
  }
 
  @GetMapping("/{id}")
  public Object getPublicacion(@PathVariable Long id) {
      return publicacionService.getPublicacion(id);
  }

  @GetMapping("/range")
  public List<Object> getPublicacionesPorRango(@RequestParam int start, @RequestParam int count) {
      return publicacionService.getPublicacionesPorRango(start, count);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Map<String, String>> deletePublicacion(
      @PathVariable Long id,
      @RequestHeader("Authorization") String token
  ) {
      publicacionService.deletePublicacion(id, token);
      
      Map<String, String> response = new HashMap<>();
      response.put("message", "publicacion eliminada");
      return ResponseEntity.ok(response);
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<PublicacionDto> updatePublicacion(
      @PathVariable Long id,
      @RequestHeader("Authorization") String token,
      @Valid @RequestBody PublicacionCreateDto publicacionCreateDto
  ) {
    PublicacionDto publicacionDto = publicacionService.updatePublicacion(
        id,
        publicacionCreateDto,
        token
    );
    return ResponseEntity.ok(publicacionDto);
  }

  @PatchMapping("/protected/update/{id}")
  public ResponseEntity<P_AlojamientoDto> updateAlojamiento(
      @PathVariable Long id,
      @RequestHeader("Authorization") String token,
      @Valid @RequestBody P_AlojamientoCreateDto alojamientoCreateDto
  ) {
    P_AlojamientoDto alojamientoDto = publicacionService.updateAlojamiento(
        id,
        alojamientoCreateDto,
        token
    );
    return ResponseEntity.ok(alojamientoDto);
  }

  // @PreAuthorize("hasAuthority('ARRENDADOR')")
  @GetMapping("/protected")
  public String protectedEndpoint() {
      return "This is a protected endpoint. You have been authenticated successfully!";
  }
}

