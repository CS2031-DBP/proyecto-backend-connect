package com.example.Connect.Usuario.Dto;

import com.example.Connect.Usuario.Domain.UsuarioRol;
import java.time.ZonedDateTime;

public class UsuarioDto {
    private Long id;
    private String correo;
    private UsuarioRol rol;
    private String nombre;
    private String apellido;
    private String telefono;
    private Long edad;
    private ZonedDateTime fechaCreacionPerfil;

    public UsuarioDto() {
    }

    public UsuarioDto(Long id, String correo, UsuarioRol rol, String nombre, String apellido, String telefono, Long edad, ZonedDateTime fechaCreacionPerfil) {
        this.id = id;
        this.correo = correo;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.fechaCreacionPerfil = fechaCreacionPerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public UsuarioRol getRol() {
        return rol;
    }

    public void setRol(UsuarioRol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getEdad() {
        return edad;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public ZonedDateTime getFechaCreacionPerfil() {
        return fechaCreacionPerfil;
    }

    public void setFechaCreacionPerfil(ZonedDateTime fechaCreacionPerfil) {
        this.fechaCreacionPerfil = fechaCreacionPerfil;
    }
}

