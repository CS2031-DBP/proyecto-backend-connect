package com.example.forutec2.Publicacion.Dto; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

import com.example.forutec2.Comentarios.Dto.ComentariosDto;
import com.example.forutec2.Review.Dto.ReviewDto;

import java.util.List;

@Getter
@Setter
@Data
public class P_AlojamientoDto {
  private Long id;
  private Long userId;
  private String titulo;
  private String body;
  private ZonedDateTime fecha_publicacion;
  private Double precio;
  private String ubicacion;
  private String referencia;
  private Boolean disponible;
  private List<ComentariosDto> comentarios;
  private List<ReviewDto> reviews;


  public P_AlojamientoDto() {}
  public P_AlojamientoDto(
      Long id,
      Long userId,
      String titulo,
      String body,
      ZonedDateTime fecha_publicacion,
      Double precio,
      String ubicacion,
      String referencia,
      Boolean disponible
  ) 
  {
    this.id = id;
    this.userId = userId;
    this.titulo = titulo;
    this.body = body;
    this.fecha_publicacion = fecha_publicacion;
    this.precio = precio;
    this.ubicacion = ubicacion;
    this.referencia = referencia;
    this.disponible = disponible;
    this.comentarios = null;
  }

  public P_AlojamientoDto(
      Long id,
      Long userId,
      String titulo,
      String body,
      ZonedDateTime fecha_publicacion,
      Double precio,
      String ubicacion,
      String referencia,
      Boolean disponible,
      List<ComentariosDto> comentarios,
      List<ReviewDto> reviews
  ) 
  {
    this.id = id;
    this.userId = userId;
    this.titulo = titulo;
    this.body = body;
    this.fecha_publicacion = fecha_publicacion;
    this.precio = precio;
    this.ubicacion = ubicacion;
    this.referencia = referencia;
    this.disponible = disponible;
    this.comentarios = comentarios;
    this.reviews = reviews;
  }
}

