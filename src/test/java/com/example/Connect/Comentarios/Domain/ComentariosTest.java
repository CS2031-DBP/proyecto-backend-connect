package com.example.Connect.Comentarios.Domain;

import com.example.Connect.Publicacion.Domain.Publicacion;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ComentariosTest {
    private Comentarios comentario;
    private Usuario usuario;
    private Publicacion publicacion;
    private DatosUsuario datosUsuario;

    @BeforeEach
    void setUp() {
        // Configurar el usuario
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
        usuario.setRol(UsuarioRol.ARRENDATARIO);

        // Configurar la publicación
        publicacion = new Publicacion();
        publicacion.setFecha_publicacion(ZonedDateTime.now(ZoneId.systemDefault()));
        publicacion.setBody("Esta es una publicación de prueba");
        publicacion.setTitulo("Prueba");
        publicacion.setUsuario(usuario);

        // Configurar el comentario
        comentario = new Comentarios();
        comentario.setBody("Este es un comentario de prueba");
        comentario.setPublicacion(publicacion);
        comentario.setUsuario(usuario);
        comentario.prePersist(); // Configura la fecha de publicación
    }

    @Test
    void testComentarioCreacion() {
        assertNotNull(comentario);
        assertEquals("Este es un comentario de prueba", comentario.getBody());
        assertNotNull(comentario.getFecha_publicacion());
        assertEquals(publicacion, comentario.getPublicacion());
        assertEquals(usuario, comentario.getUsuario());
    }

    @Test
    void testComentarioBodyNotNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comentario.setBody(null);
        });
        String expectedMessage = "El cuerpo del comentario no puede ser nulo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRelacionComentarioPublicacion() {
        assertNotNull(comentario.getPublicacion());
        assertEquals(publicacion, comentario.getPublicacion());
    }

    @Test
    void testRelacionComentarioUsuario() {
        assertNotNull(comentario.getUsuario());
        assertEquals(usuario, comentario.getUsuario());
    }

    @Test
    void testFechaPublicacionSetOnPrePersist() {
        Comentarios newComentario = new Comentarios();
        newComentario.setBody("Nuevo comentario");
        newComentario.setPublicacion(publicacion);
        newComentario.setUsuario(usuario);
        newComentario.prePersist();

        assertNotNull(newComentario.getFecha_publicacion());
        assertEquals(ZonedDateTime.now(ZoneId.of("America/Lima")).getZone(), newComentario.getFecha_publicacion().getZone());
    }
}
