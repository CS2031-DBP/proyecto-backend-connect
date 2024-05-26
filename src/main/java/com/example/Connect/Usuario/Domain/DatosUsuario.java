package com.example.forutec2.Usuario.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "datos_usuario")
public class DatosUsuario {

    @Id
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String apellido;

    @Column(length = 20, nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Long edad;

    private ZonedDateTime FechaCreacionPerfil;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
      this.FechaCreacionPerfil = ZonedDateTime.now(ZoneId.of("America/Lima"));
    }
}

