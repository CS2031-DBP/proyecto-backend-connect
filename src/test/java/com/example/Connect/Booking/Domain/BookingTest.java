package com.example.Connect.Booking.Domain;

import com.example.Connect.Publicacion.Domain.P_Alojamiento;
import com.example.Connect.Publicacion.Domain.Publicacion;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.relation.Role;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {
    private Usuario inquilino;
    private Usuario arrendador;
    private DatosUsuario usuario;
    private DatosUsuario usuario2;
    private P_Alojamiento alojamiento;
    private Publicacion publicacion;
    private Booking booking;

    public void setUpUsuario(){
        usuario = new DatosUsuario();
        usuario.setNombre("juan");
        usuario.setApellido("Apellido");
        usuario.setEdad(33L);
        usuario.setTelefono("+51983555832");
        usuario.setFechaCreacionPerfil(ZonedDateTime.now(ZoneId.systemDefault()));
    }
    public void setUpUsuario2(){

        usuario2 = new DatosUsuario();
        usuario2.setNombre("Usuario2");
        usuario2.setApellido("Apellido2");
        usuario2.setEdad(34L);
        usuario2.setTelefono("+51111111111");
        usuario2.setFechaCreacionPerfil(ZonedDateTime.now(ZoneId.systemDefault()));
    }
    public void setUpInquilino(){
        inquilino = new Usuario();
        inquilino.setDatosUsuario(usuario);
        inquilino.setCorreo("ejemplo@gmail.com");
        inquilino.setContrasenia("12345678");
        inquilino.setRol(UsuarioRol.ARRENDATARIO);
    }
    public void setUpArrendador(){
        arrendador = new Usuario();
        arrendador.setDatosUsuario(usuario2);
        arrendador.setCorreo("ejemplo2@gmail.com");
        arrendador.setContrasenia("123456789");
        arrendador.setRol(UsuarioRol.ARRENDADOR);
    }
    public void setUpAlojamiento(){
        alojamiento = new P_Alojamiento();
        alojamiento.setDireccion("ejemplo");
        alojamiento.setDisponible(true);
        alojamiento.setPrecio(22.2);
        alojamiento.setReferencia("cerca");
    }
    public void setUpPublicacion(){
        publicacion = new Publicacion();
        publicacion.setFecha_publicacion(ZonedDateTime.now(ZoneId.systemDefault()));
        publicacion.setBody("publicacion");
        publicacion.setTitulo("Titulo");
        publicacion.setUsuario(arrendador);
        publicacion.setAlojamiento(alojamiento);
    }
    public void setUpBooking(){
        booking = new Booking();
        booking.setArrendador(arrendador);
        booking.setInquilino(inquilino);
        booking.setPAlojamiento(alojamiento);
    }

    @BeforeEach
    void setUp(){
        setUpUsuario();
        setUpUsuario2();
        setUpInquilino();
        setUpArrendador();
        setUpAlojamiento();
        setUpPublicacion();
        setUpBooking();
    }
    @Test
    void TestUsuarioCreacion(){
        assertNotNull(usuario);
        assertEquals("juan",usuario.getNombre());
        assertEquals("Apellido",usuario.getApellido());
        assertEquals(33L,usuario.getEdad());
        assertEquals("+51983555832",usuario.getTelefono());
    }
    @Test
    void TestUsuarioCreacion2(){
        assertNotNull(usuario2);
        assertEquals("Usuario2",usuario2.getNombre());
        assertEquals("Apellido2",usuario2.getApellido());
        assertEquals(34L,usuario2.getEdad());
        assertEquals("+51111111111",usuario2.getTelefono());
    }
    @Test
    void TestArrendadorCreacion(){
        assertNotNull(arrendador);
        assertEquals(usuario2,arrendador.getDatosUsuario());
        assertEquals("ejemplo2@gmail.com",arrendador.getCorreo());
        assertEquals("123456789",arrendador.getContrasenia());
        assertEquals(UsuarioRol.ARRENDADOR,arrendador.getRol());

    }
    @Test
    void TestArrendatariroCreacion(){
        assertNotNull(inquilino);
        assertEquals(usuario,inquilino.getDatosUsuario());
        assertEquals("ejemplo@gmail.com",inquilino.getCorreo());
        assertEquals("12345678",inquilino.getContrasenia());
        assertEquals(UsuarioRol.ARRENDATARIO,inquilino.getRol());

    }

    @Test
    void TestPublicacionCreacion(){
        setUpAlojamiento();
        assertNotNull(publicacion);
        assertEquals("publicacion", publicacion.getBody());
        assertEquals("Titulo", publicacion.getTitulo());
        assertEquals(arrendador,publicacion.getUsuario());
        assertEquals(alojamiento,publicacion.getAlojamiento());

    }
    @Test
    void TestAlojamientoCreacion(){

        assertNotNull(alojamiento);
        assertEquals("ejemplo",alojamiento.getDireccion());
        assertEquals(true,alojamiento.getDisponible());
        assertEquals(22.2,alojamiento.getPrecio());
        assertEquals("cerca",alojamiento.getReferencia());
    }
    @Test
    void TestBookingCreacion(){
        assertNotNull(booking);
        assertEquals(alojamiento,booking.getPAlojamiento());
        assertEquals(inquilino,booking.getInquilino());
        assertEquals(arrendador,booking.getArrendador());
    }
}