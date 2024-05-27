package com.example.Connect.Usuario.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.Connect.Publicacion.Domain.Publicacion;
import com.example.Connect.Comentarios.Domain.Comentarios;
import com.example.Connect.Booking.Domain.Booking;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = "correo")})
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 30, nullable = false, unique = true)
  private String correo;

  @Column(nullable = false)
  private String contrasenia;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private UsuarioRol rol;

  @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, optional = true)
  @JsonManagedReference
  private DatosUsuario datosUsuario;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Publicacion> publicaciones;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Comentarios> comentarios;

  @OneToMany(mappedBy = "arrendador", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Booking> bookingsArrendador;

  @OneToMany(mappedBy = "inquilino", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Booking> bookingsInquilino;
}

