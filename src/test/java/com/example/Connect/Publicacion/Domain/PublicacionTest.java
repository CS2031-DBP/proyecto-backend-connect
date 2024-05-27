package com.example.Connect.Publicacion.Domain;

import com.example.Connect.Comentarios.Domain.Comentarios;
import com.example.Connect.Review.Domain.Review;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PublicacionTest {

    private Review review;
    private Usuario usuario;
    private P_Alojamiento pAlojamiento;
    private DatosUsuario datosUsuario;
    private Comentarios comentario;
    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        datosUsuario = new DatosUsuario();
        datosUsuario.setNombre("Juan");
        datosUsuario.setApellido("Perez");
        datosUsuario.setEdad(30L);
        datosUsuario.setTelefono("+51987654321");
        datosUsuario.setFechaCreacionPerfil(ZonedDateTime.now(ZoneId.systemDefault()));

        usuario = new Usuario();
        usuario.setDatosUsuario(datosUsuario);
        usuario.setCorreo("juan.perez@gmail.com");
        usuario.setContrasenia("password123");
        usuario.setRol(UsuarioRol.ARRENDADOR);

        pAlojamiento = new P_Alojamiento();
        pAlojamiento.setId(1L);
        pAlojamiento.setPrecio(100.0);
        pAlojamiento.setDireccion("Calle 123");
        pAlojamiento.setReferencia("Cerca del parque");
        pAlojamiento.setDisponible(true);

        comentario = new Comentarios();
        comentario.setBody("Este es un comentario de prueba");
        comentario.setUsuario(usuario);
        comentario.prePersist();
        publicacion = new Publicacion();
        publicacion.setFecha_publicacion(ZonedDateTime.now(ZoneId.systemDefault()));
        publicacion.setBody("publicacion");
        publicacion.setTitulo("Titulo");
        publicacion.setUsuario(usuario);
        publicacion.setAlojamiento(pAlojamiento);
        publicacion.getComentarios().add(comentario);
    }

    @Test
    void testCreacionPublicacion() {
        assertNotNull(publicacion);
        assertEquals(pAlojamiento, publicacion.getAlojamiento());
        assertEquals("Titulo", publicacion.getTitulo());
        assertEquals(usuario, publicacion.getUsuario());
        assertEquals("publicacion", publicacion.getBody());
    }

    @Test
    void testRelacionComentarioPublicacion() {

        assertEquals(comentario, publicacion.getComentarios().get(0));
    }

    @Test
    void testRelacionPublicacionUsuario() {
        assertNotNull(publicacion.getUsuario());
        assertEquals(usuario, publicacion.getUsuario());
    }

    @Test
    void testFechaPublicacionSetOnPrePersist() {
        Publicacion publicacion = new Publicacion();
        publicacion.setBody("Nuevo comentario");
        publicacion.setUsuario(usuario);
        publicacion.prePersist();
        assertNotNull(publicacion.getFecha_publicacion());
        assertEquals(ZonedDateTime.now(ZoneId.of("America/Lima")).getZone(), publicacion.getFecha_publicacion().getZone());}

}