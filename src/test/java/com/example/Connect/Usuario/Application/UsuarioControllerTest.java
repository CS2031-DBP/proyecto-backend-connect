package com.example.Connect.Usuario.Application;

import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private UsuarioRepository usuarioRepository;
    private Usuario usuario;
    private DatosUsuario datosUsuario;

    @BeforeEach
    public void setup() {
        datosUsuario = new DatosUsuario();
        usuario = new Usuario();
        datosUsuario.setUsuario(usuario);
        datosUsuario.setId(1L);
        datosUsuario.setApellido("iribar");
        datosUsuario.setNombre("Federico");
        datosUsuario.setEdad(18L);
        datosUsuario.setFechaCreacionPerfil(ZonedDateTime.now());
        datosUsuario.setTelefono("099694369");

        usuario.setId(1L);
        usuario.setCorreo("fedeiribar@gmail.com");
        usuario.setContrasenia("megustalautec");
        usuario.setRol(UsuarioRol.ARRENDADOR);
        usuario.setDatosUsuario(datosUsuario);
    }
    @Test
    public void testgetuserinfo() throws Exception {
        // Guarda un usuario en la base de datos
        Usuario usuario1 = usuarioRepository.save(usuario);

        // Realiza una solicitud GET para obtener la información del usuario
        mockMvc.perform(get("/usuario/{id}", usuario1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Verifica que el ID devuelto sea igual al ID del usuario guardado
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(usuario1.getId().intValue())))
                // Verifica algunos campos específicos de datosUsuario
                .andExpect(MockMvcResultMatchers.jsonPath("$.datosUsuario.nombre", Matchers.is(usuario1.getDatosUsuario().getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datosUsuario.apellido", Matchers.is(usuario1.getDatosUsuario().getApellido())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datosUsuario.telefono", Matchers.is(usuario1.getDatosUsuario().getTelefono())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datosUsuario.edad", Matchers.is(usuario1.getDatosUsuario().getEdad().intValue())));
    }
    @Test
    public void testdeleteusuario() throws Exception {
        Usuario usuario1 = usuarioRepository.save(usuario);
        mockMvc.perform(delete("/usuario/{id}", usuario1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertFalse(usuarioRepository.findById(usuario1.getId()).isPresent());
    }
    @Test
    public void testUpdateUsuario() throws Exception {
        Usuario usuario1 = usuarioRepository.save(usuario);
        usuario1.setCorreo("elpepe@gmail.com");
        usuario1.setContrasenia("Utecesunauniversidad");

        mockMvc.perform(put("/usuario/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario1)))
                .andExpect(status().isOk());

        Usuario usuario2 = usuarioRepository.findById(usuario1.getId()).orElseThrow();
        Assertions.assertEquals(usuario2.getCorreo(), "elpepe@gmail.com");
        Assertions.assertEquals(usuario2.getContrasenia(), "Utecesunauniversidad");
    }

}
