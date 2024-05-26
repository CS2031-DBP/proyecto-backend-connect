package com.example.Connect.Booking.Infraestructure;

import com.example.Connect.Booking.Domain.Booking;
import com.example.Connect.ConnectApplication;
import com.example.Connect.Publicacion.Domain.P_Alojamiento;
import com.example.Connect.Publicacion.Domain.Publicacion;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BookingRepository bookingRepository;

    private Usuario inquilino;
    private Usuario arrendador;
    private DatosUsuario usuario;
    private DatosUsuario usuario2;
    private P_Alojamiento alojamiento;
    private Publicacion publicacion;
    private Booking booking;

    public void setUpUsuario() {
        usuario = new DatosUsuario();
        usuario.setNombre("juan");
        usuario.setApellido("Apellido");
        usuario.setEdad(33L);
        usuario.setTelefono("+51983555832");
        usuario.setFechaCreacionPerfil(ZonedDateTime.now(ZoneId.systemDefault()));
    }

    public void setUpUsuario2() {
        usuario2 = new DatosUsuario();
        usuario2.setNombre("Usuario2");
        usuario2.setApellido("Apellido2");
        usuario2.setEdad(34L);
        usuario2.setTelefono("+51111111111");
        usuario2.setFechaCreacionPerfil(ZonedDateTime.now(ZoneId.systemDefault()));
    }

    public void setUpInquilino() {
        inquilino = new Usuario();
        inquilino.setDatosUsuario(usuario);
        inquilino.setCorreo("ejemplo@gmail.com");
        inquilino.setContrasenia("12345678");
        inquilino.setRol(UsuarioRol.ARRENDATARIO);
    }

    public void setUpArrendador() {
        arrendador = new Usuario();
        arrendador.setDatosUsuario(usuario2);
        arrendador.setCorreo("ejemplo2@gmail.com");
        arrendador.setContrasenia("123456789");
        arrendador.setRol(UsuarioRol.ARRENDADOR);
    }

    public void setUpAlojamiento() {
        alojamiento = new P_Alojamiento();
        alojamiento.setDireccion("ejemplo");
        alojamiento.setDisponible(true);
        alojamiento.setPrecio(22.2);
        alojamiento.setReferencia("cerca");
    }

    public void setUpPublicacion() {
        publicacion = new Publicacion();
        publicacion.setFecha_publicacion(ZonedDateTime.now(ZoneId.systemDefault()));
        publicacion.setBody("publicacion");
        publicacion.setTitulo("Titulo");
        publicacion.setUsuario(arrendador);
        publicacion.setAlojamiento(alojamiento);
    }

    public void setUpBooking() {
        booking = new Booking();
        booking.setArrendador(arrendador);
        booking.setInquilino(inquilino);
        booking.setPAlojamiento(alojamiento);
    }

    @BeforeEach
    public void setUp() {
        setUpUsuario();
        setUpUsuario2();
        setUpInquilino();
        setUpArrendador();
        setUpAlojamiento();
        setUpPublicacion();
        setUpBooking();

        // Persist entities using EntityManager
        entityManager.persist(usuario);
        entityManager.persist(usuario2);
        entityManager.persist(inquilino);
        entityManager.persist(arrendador);
        entityManager.persist(alojamiento);
        entityManager.persist(publicacion);
        entityManager.persist(booking);


        entityManager.flush();
    }

    @Test
    public void findByArrendadorId() {
        List<Booking> bookings = bookingRepository.findByArrendadorId(arrendador.getId());
        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
        assertEquals(arrendador.getId(), bookings.get(0).getArrendador().getId());
    }

    @Test
    public void findByPAlojamientoId() {
        List<Booking> bookings = bookingRepository.findByPAlojamientoId(alojamiento.getId());
        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
        assertEquals(alojamiento.getId(), bookings.get(0).getPAlojamiento().getId());
    }
}