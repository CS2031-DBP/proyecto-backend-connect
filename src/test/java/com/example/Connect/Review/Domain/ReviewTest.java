package com.example.Connect.Review.Domain;

import com.example.Connect.Publicacion.Domain.P_Alojamiento;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    private Review review;
    private Usuario usuario;
    private P_Alojamiento pAlojamiento;
    private DatosUsuario datosUsuario;

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
        usuario.setRol(UsuarioRol.ARRENDATARIO);

        pAlojamiento = new P_Alojamiento();
        pAlojamiento.setId(1L);
        pAlojamiento.setPrecio(100.0);
        pAlojamiento.setDireccion("Calle 123");
        pAlojamiento.setReferencia("Cerca del parque");
        pAlojamiento.setDisponible(true);

        review = new Review();
        review.setBody("Esta es una reseña de prueba");
        review.setCalificacion(5L);
        review.setPAlojamiento(pAlojamiento);
        review.setUsuario(usuario);
        review.prePersist();
    }

    @Test
    void testReviewCreacion() {
        assertNotNull(review);
        assertEquals("Esta es una reseña de prueba", review.getBody());
        assertEquals(5L, review.getCalificacion());
        assertNotNull(review.getFecha_publicacion());
        assertEquals(pAlojamiento, review.getPAlojamiento());
        assertEquals(usuario, review.getUsuario());
    }

    @Test
    void testReviewBodyNotNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            review.setBody(null);
        });
        String expectedMessage = "El cuerpo de la reseña no puede ser nulo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRelacionReviewPAlojamiento() {
        assertNotNull(review.getPAlojamiento());
        assertEquals(pAlojamiento, review.getPAlojamiento());
    }

    @Test
    void testRelacionReviewUsuario() {
        assertNotNull(review.getUsuario());
        assertEquals(usuario, review.getUsuario());
    }

    @Test
    void testFechaPublicacionSetOnPrePersist() {
        Review newReview = new Review();
        newReview.setBody("Nueva reseña");
        newReview.setCalificacion(4L);
        newReview.setPAlojamiento(pAlojamiento);
        newReview.setUsuario(usuario);
        newReview.prePersist();

        assertNotNull(newReview.getFecha_publicacion());
        assertEquals(ZonedDateTime.now(ZoneId.of("America/Lima")).getZone(), newReview.getFecha_publicacion().getZone());
    }
}
