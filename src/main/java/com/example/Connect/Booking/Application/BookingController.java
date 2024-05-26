package com.example.forutec2.Booking.Application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.forutec2.Security.JwtUtil;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.example.forutec2.Booking.Domain.Booking;

import com.example.forutec2.Booking.Application.BookingService;

import com.example.forutec2.Booking.Dto.BookingDto;
import com.example.forutec2.Booking.Dto.BookingCreateDto;

import com.example.forutec2.Booking.Infraestructure.BookingRepository;

import com.example.forutec2.Exception.CustomException;

@RestController
@RequestMapping("/booking")
public class BookingController {

  @Autowired
  private BookingService bookingService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/protected/create")
  public ResponseEntity<BookingDto> createBooking(
    @RequestHeader("Authorization") String token,
    @Valid @RequestBody BookingCreateDto bookingCreateDto
  ) {

    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    BookingDto BookingDto = bookingService.createBooking(
      bookingCreateDto,
      userId
    );
    return ResponseEntity.ok(BookingDto);
  }

  @GetMapping("/protected/all/usuario")
  public ResponseEntity<List<BookingDto>> getAllBookingsUser(
    @RequestHeader("Authorization") String token
  ) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    List<BookingDto> BookingDtos = bookingService.getAllBookings(userId);
    return ResponseEntity.ok(BookingDtos);
  }

  @GetMapping("/protected/all/publicacion/{publicationId}")
  public ResponseEntity<List<BookingDto>> getAllBookingsPublication(
    @RequestHeader("Authorization") String token,
    @PathVariable Long publicationId
  ) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    List<BookingDto> BookingDtos = bookingService.getAllBookingsPublication(
      userId,
      publicationId
    );
    return ResponseEntity.ok(BookingDtos);
  }

  @DeleteMapping("/protected/delete/{bookingId}")
  public ResponseEntity<Map<String, Boolean>> deleteBooking(
    @RequestHeader("Authorization") String token,
    @PathVariable Long bookingId
  ) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    bookingService.deleteBooking(userId, bookingId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
