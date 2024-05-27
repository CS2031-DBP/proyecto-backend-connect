package com.example.Connect.Usuario.Domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JsonBackReference
    private Usuario usuario;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String apellido;

    @Column(length = 20, nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Long edad;

    private ZonedDateTime FechaCreacionPerfil;



    @PrePersist
    public void prePersist() {
      this.FechaCreacionPerfil = ZonedDateTime.now(ZoneId.of("America/Lima"));
    }
}

