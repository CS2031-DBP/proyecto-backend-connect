package com.example.Connect.Publicacion.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Comentarios.Domain.Comentarios;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "publicacion")
public class Publicacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  @JsonBackReference
  private Usuario usuario;

  @Column(length = 100, nullable = false)
  private String titulo;

  @Column(length = 200, nullable = false)
  private String body;

  private ZonedDateTime fecha_publicacion;

  @OneToOne(mappedBy = "publicacion", cascade = CascadeType.ALL)
  @JsonManagedReference
  private P_Alojamiento alojamiento;

  @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Comentarios> comentarios;



  @PrePersist
  public void prePersist() {
      this.fecha_publicacion = ZonedDateTime.now(ZoneId.of("America/Lima"));
  }
}
