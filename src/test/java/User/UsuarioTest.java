package User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioTest {
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
    public void testgetuserinfo(){
        Usuario usuario1 = usuarioRepository.save(usuario);
        mockMvc.perform(get("/usuario/{id}", usuario1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(usuario1.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datosUsuario", Matchers.is(usuario1.getDatosUsuario())));
    }
    @Test
    public void testdeleteusuario(){
        Usuario usuario1 = usuarioRepository.save(usuario);
        mockMvc.perform(delete("/usuario/delete"))
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


