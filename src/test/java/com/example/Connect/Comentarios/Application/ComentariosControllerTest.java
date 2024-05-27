package com.example.Connect.Comentarios.Application;

import com.example.Connect.Comentarios.Dto.ComentariosCreateDto;
import com.example.Connect.Comentarios.Dto.ComentariosDto;
import com.example.Connect.Security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComentariosController.class)
public class ComentariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentariosService comentariosService;

    @MockBean
    private JwtUtil jwtUtil;

    private ComentariosDto comentariosDto;
    private ComentariosCreateDto comentariosCreateDto;

    @BeforeEach
    void setUp() {
        comentariosDto = new ComentariosDto();
        comentariosDto.setId(1L);
        comentariosDto.setBody("buena casa");

        comentariosCreateDto = new ComentariosCreateDto();
        comentariosCreateDto.setBody("casa basura");

        Mockito.when(jwtUtil.extractUsername(anyString())).thenReturn("1");
    }

    @Test
    void testCreateComentarios() throws Exception {
        Mockito.when(comentariosService.createComentarios(any(ComentariosCreateDto.class), anyLong(), anyLong()))
                .thenReturn(comentariosDto);

        mockMvc.perform(post("/comentarios/create/publicacion/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\": \"Test body\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comentariosDto.getId().intValue())))
                .andExpect(jsonPath("$.body", is(comentariosDto.getBody())));
    }

    @Test
    void testGetComentarios() throws Exception {
        Mockito.when(comentariosService.getComentarios(anyLong())).thenReturn(comentariosDto);

        mockMvc.perform(get("/comentarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comentariosDto.getId().intValue())))
                .andExpect(jsonPath("$.body", is(comentariosDto.getBody())));
    }

    @Test
    void testGetAllComentarios() throws Exception {
        List<ComentariosDto> comentariosList = Collections.singletonList(comentariosDto);
        Mockito.when(comentariosService.getAllComentarios(anyLong())).thenReturn(comentariosList);

        mockMvc.perform(get("/comentarios/all/publicacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(comentariosDto.getId().intValue())))
                .andExpect(jsonPath("$[0].body", is(comentariosDto.getBody())));
    }

    @Test
    void testGetAllComentariosByUsuario() throws Exception {
        List<ComentariosDto> comentariosList = Collections.singletonList(comentariosDto);
        Mockito.when(comentariosService.getAllComentariosByUsuario(anyLong())).thenReturn(comentariosList);

        mockMvc.perform(get("/comentarios/all/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(comentariosDto.getId().intValue())))
                .andExpect(jsonPath("$[0].body", is(comentariosDto.getBody())));
    }

    @Test
    void testDeleteComentarios() throws Exception {
        mockMvc.perform(delete("/comentarios/delete/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted", is(true)));
    }

    @Test
    void testUpdateComentarios() throws Exception {
        Mockito.when(comentariosService.updateComentarios(anyLong(), any(ComentariosCreateDto.class), anyString()))
                .thenReturn(comentariosDto);

        mockMvc.perform(patch("/comentarios/update/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\": \"Updated body\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comentariosDto.getId().intValue())))
                .andExpect(jsonPath("$.body", is("Updated body")));
    }
}
