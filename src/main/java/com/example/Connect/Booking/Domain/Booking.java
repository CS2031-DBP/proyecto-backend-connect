package com.example.forutec2.Booking.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.forutec2.Usuario.Domain.Usuario;
import com.example.forutec2.Publicacion.Domain.P_Alojamiento;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking")
public class Booking {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "arrendador_id", nullable = false)
  @JsonBackReference
  private Usuario arrendador;

  @ManyToOne
  @JoinColumn(name = "inquilino_id", nullable = false)
  @JsonBackReference
  private Usuario inquilino;

  @ManyToOne
  @JoinColumn(name = "p_alojamiento_id", nullable = false)
  @JsonBackReference
  private P_Alojamiento pAlojamiento;
}
