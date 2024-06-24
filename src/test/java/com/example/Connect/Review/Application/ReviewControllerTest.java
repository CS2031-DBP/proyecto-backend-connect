package com.example.Connect.Review.Application;

import com.example.Connect.Review.Domain.Review;
import com.example.Connect.Review.Dto.ReviewCreateDto;
import com.example.Connect.Review.Dto.ReviewDto;
import com.example.Connect.Security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private JwtUtil jwtUtil;

    private ReviewDto reviewDto;
    private ReviewCreateDto reviewCreateDto;

    @BeforeEach
    void setUp() {
        reviewDto = new ReviewDto();
        reviewDto.setId(1L);
        reviewDto.setBody("buena casa");
        reviewDto.setCalificacion(5L);

        reviewCreateDto = new ReviewCreateDto();
        reviewCreateDto.setBody("Test body");
        reviewCreateDto.setCalificacion(4L);

        Mockito.when(jwtUtil.extractUsername(anyString())).thenReturn("1");
    }

    @Test
    void testCreateReview() throws Exception {
        Mockito.when(reviewService.createReview(any(ReviewCreateDto.class), anyLong(), anyString()))
                .thenReturn(reviewDto);

        mockMvc.perform(post("/review/create/p_alojamiento/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\": \"Test body\", \"calificacion\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reviewDto.getId().intValue())))
                .andExpect(jsonPath("$.body", is(reviewDto.getBody())))
                .andExpect(jsonPath("$.calificacion", is(reviewDto.getCalificacion().intValue())));
    }
    @Test
    void testGetReview() throws Exception {
        Mockito.when(reviewService.getReview(anyLong())).thenReturn(reviewDto);

        mockMvc.perform(get("/review/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reviewDto.getId().intValue())))
                .andExpect(jsonPath("$.body", is(reviewDto.getBody())))
                .andExpect(jsonPath("$.calificacion", is(reviewDto.getCalificacion().intValue())));
    }

    @Test
    void testGetReviewsByPublicacion() throws Exception {
        List<ReviewDto> reviewList = Collections.singletonList(reviewDto);
        Mockito.when(reviewService.getReviews(anyLong())).thenReturn(reviewList);

        mockMvc.perform(get("/review/all/p_alojamiento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(reviewDto.getId().intValue())))
                .andExpect(jsonPath("$[0].body", is(reviewDto.getBody())))
                .andExpect(jsonPath("$[0].calificacion", is(reviewDto.getCalificacion().intValue())));
    }

    @Test
    void testGetReviewsByUser() throws Exception {
        List<ReviewDto> reviewList = Collections.singletonList(reviewDto);
        Mockito.when(reviewService.getReviewsByUser(anyLong())).thenReturn(reviewList);

        mockMvc.perform(get("/review/all/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(reviewDto.getId().intValue())))
                .andExpect(jsonPath("$[0].body", is(reviewDto.getBody())))
                .andExpect(jsonPath("$[0].calificacion", is(reviewDto.getCalificacion().intValue())));
    }

    @Test
    void testDeleteReview() throws Exception {
        mockMvc.perform(delete("/review/delete/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted", is(true)));
    }

    @Test
    void testUpdateReview() throws Exception {
        Mockito.when(reviewService.updateReview(anyLong(), any(ReviewCreateDto.class), anyString()))
                .thenReturn(reviewDto);

        mockMvc.perform(patch("/review/update/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\": \"Updated body\", \"calificacion\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reviewDto.getId().intValue())))
                .andExpect(jsonPath("$.body", is("Updated body")))
                .andExpect(jsonPath("$.calificacion", is(reviewDto.getCalificacion().intValue())));
    }
}
