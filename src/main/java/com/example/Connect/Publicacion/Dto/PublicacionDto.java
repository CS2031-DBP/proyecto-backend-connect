package com.example.forutec2.Publicacion.Dto; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

import com.example.forutec2.Comentarios.Dto.ComentariosDto;

import java.util.List;

@Getter
@Setter
@Data
public class PublicacionDto {
  private Long id;
  private Long userId;
  private String titulo;
  private String body;
  private ZonedDateTime fecha_publicacion;
  private List<ComentariosDto> comentarios;

  public PublicacionDto() {}
  public PublicacionDto(
      Long id,
      Long userId,
      String titulo,
      String body,
      ZonedDateTime fecha_publicacion
  ) 
  {
    this.id = id;
    this.userId = userId;
    this.titulo = titulo;
    this.body = body;
    this.fecha_publicacion = fecha_publicacion;
    this.comentarios = null;
  }

  public PublicacionDto(
      Long id,
      Long userId,
      String titulo,
      String body,
      ZonedDateTime fecha_publicacion,
      List<ComentariosDto> comentarios
  ) 
  {
    this.id = id;
    this.userId = userId;
    this.titulo = titulo;
    this.body = body;
    this.fecha_publicacion = fecha_publicacion;
    this.comentarios = comentarios;
  }
}
