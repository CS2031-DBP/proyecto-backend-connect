package com.example.Connect.Review.Application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Connect.Security.JwtUtil;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.example.Connect.Review.Dto.ReviewDto;
import com.example.Connect.Review.Dto.ReviewCreateDto;

@RestController
@RequestMapping("/review")
public class ReviewController {
  
  @Autowired
  private ReviewService reviewService;

  @Autowired
  private JwtUtil jwtUtil;
 
  @PostMapping("/create/p_alojamiento/{id}")
  public ResponseEntity<ReviewDto> createReview(
    @RequestHeader("Authorization") String token,
    @Valid @RequestBody ReviewCreateDto reviewCreateDto,
    @PathVariable Long id
  ) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    ReviewDto reviewDto = reviewService.createReview(
      reviewCreateDto,
      id,
      userId
    );
    return ResponseEntity.ok(reviewDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReviewDto> getReview(
    @PathVariable Long id
  ) {
    ReviewDto reviewDto = reviewService.getReview(id);
    return ResponseEntity.ok(reviewDto);
  }

  @GetMapping("/all/p_alojamiento/{id}")
  public ResponseEntity<List<ReviewDto>> getReviewsByPublicacion(
    @PathVariable Long id
  ) {
    List<ReviewDto> reviewDtos = reviewService.getReviews(id);
    return ResponseEntity.ok(reviewDtos);
  }

  @GetMapping("/all/usuario/{id}")
  public ResponseEntity<List<ReviewDto>> getReviewsByUser(
    @PathVariable Long id
  ) {
    List<ReviewDto> reviewDtos = reviewService.getReviewsByUser(id);
    return ResponseEntity.ok(reviewDtos);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteReview(
    @PathVariable Long id,
    @RequestHeader("Authorization") String token
  ) {
    reviewService.deleteReview(id, token);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<ReviewDto> updateReview(
    @PathVariable Long id,
    @RequestHeader("Authorization") String token,
    @Valid @RequestBody ReviewCreateDto reviewCreateDto
  ) {
    ReviewDto reviewDto = reviewService.updateReview(id, reviewCreateDto, token);
    return ResponseEntity.ok(reviewDto);
  }
}
