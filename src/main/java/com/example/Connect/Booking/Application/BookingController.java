package com.example.Connect.Booking.Application;

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

import com.example.Connect.Booking.Dto.BookingDto;
import com.example.Connect.Booking.Dto.BookingCreateDto;

@RestController
@RequestMapping("/booking")
public class BookingController {

  private final BookingService bookingService;

  private final JwtUtil jwtUtil;

  public BookingController(BookingService bookingService, JwtUtil jwtUtil) {
    this.bookingService = bookingService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/protected/create")
  public ResponseEntity<BookingDto> createBooking(
    @RequestHeader("Authorization") String token,
    @Valid @RequestBody BookingCreateDto bookingCreateDto) {
    BookingDto BookingDto = bookingService.createBooking(bookingCreateDto,token);
    return ResponseEntity.ok(BookingDto);
  }

  @GetMapping("/protected/all/usuario")
  public ResponseEntity<List<BookingDto>> getAllBookingsUser(
    @RequestHeader("Authorization") String token
  ) {
    List<BookingDto> BookingDtos = bookingService.getAllBookings(token);
    return ResponseEntity.ok(BookingDtos);
  }

  @GetMapping("/protected/all/publicacion/{publicationId}")
  public ResponseEntity<List<BookingDto>> getAllBookingsPublication(
    @RequestHeader("Authorization") String token,
    @PathVariable Long publicationId
  ) {
    List<BookingDto> BookingDtos = bookingService.getAllBookingsPublication(
      token,
      publicationId
    );
    return ResponseEntity.ok(BookingDtos);
  }

  @DeleteMapping("/protected/delete/{bookingId}")
  public ResponseEntity<Map<String, Boolean>> deleteBooking(
    @RequestHeader("Authorization") String token,
    @PathVariable Long bookingId
  ) {
    bookingService.deleteBooking(token, bookingId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
