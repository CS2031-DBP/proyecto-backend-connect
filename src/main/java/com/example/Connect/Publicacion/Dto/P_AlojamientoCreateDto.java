package com.example.forutec2.Publicacion.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class P_AlojamientoCreateDto {

  @NotBlank()
  private String titulo;

  @NotBlank()
  private String body;

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private Double precio;

  @NotBlank()
  private String direccion;

  @NotBlank()
  private String referencia;
}
