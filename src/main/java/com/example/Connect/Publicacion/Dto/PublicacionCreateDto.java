package com.example.Connect.Publicacion.Dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicacionCreateDto {
  @NotBlank()
  private String titulo;

  @NotBlank()
  private String body;


}
