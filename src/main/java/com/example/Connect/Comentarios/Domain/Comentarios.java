package com.example.Connect.Comentarios.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.Connect.Publicacion.Domain.Publicacion;
import com.example.Connect.Usuario.Domain.Usuario;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comentarios")
public class Comentarios {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 200, nullable = false)
  private String body;

  @ManyToOne
  @JoinColumn(name = "publicacion_id", nullable = false)
  @JsonBackReference
  private Publicacion publicacion;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  @JsonBackReference
  private Usuario usuario;

  private ZonedDateTime fecha_publicacion;

  @PrePersist
  public void prePersist() {
      this.fecha_publicacion = ZonedDateTime.now(ZoneId.of("America/Lima"));
  }
}
