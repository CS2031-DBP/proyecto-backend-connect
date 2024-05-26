package com.example.Connect.Review.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.example.Connect.Publicacion.Domain.P_Alojamiento;
import com.example.Connect.Usuario.Domain.Usuario;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
public class Review {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  @JsonBackReference
  private Usuario usuario;

  @Column(length = 200, nullable = false)
  private String body;

  @Column(nullable = false)
  private Long calificacion;

  @ManyToOne
  @JoinColumn(name = "p_alojamiento_id", nullable = false)
  @JsonBackReference
  private P_Alojamiento pAlojamiento;

  private ZonedDateTime fecha_publicacion;

  @PrePersist
  public void prePersist() {
      this.fecha_publicacion = ZonedDateTime.now(ZoneId.of("America/Lima"));
  }
}
