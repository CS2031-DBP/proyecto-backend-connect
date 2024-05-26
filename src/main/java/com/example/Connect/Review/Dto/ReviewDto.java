package com.example.forutec2.Review.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ReviewDto {
  private Long id;
  private Long usuario_id;
  private String body;
  private Long calificacion;
  private Long p_alojamiento_id;
  private ZonedDateTime fecha;
}
