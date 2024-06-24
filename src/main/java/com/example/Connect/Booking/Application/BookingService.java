package com.example.Connect.Booking.Application;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Connect.Security.JwtUtil;

import com.example.Connect.Exception.CustomException;

import com.example.Connect.Booking.Domain.Booking;
import com.example.Connect.Publicacion.Domain.P_Alojamiento;
import com.example.Connect.Usuario.Domain.Usuario;

import com.example.Connect.Booking.Dto.BookingDto;
import com.example.Connect.Booking.Dto.BookingCreateDto;

import com.example.Connect.Booking.Infraestructure.BookingRepository;
import com.example.Connect.Publicacion.Infraestructure.P_AlojamientoRepository;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;

@Service
public class BookingService {

  private final JwtUtil jwtUtil;

  private final BookingRepository bookingRepository;

  private final P_AlojamientoRepository p_alojamientoRepository;

  private final UsuarioRepository usuarioRepository;

  public BookingService(JwtUtil jwtUtil, BookingRepository bookingRepository, P_AlojamientoRepository p_alojamientoRepository, UsuarioRepository usuarioRepository) {
    this.jwtUtil = jwtUtil;
    this.bookingRepository = bookingRepository;
    this.p_alojamientoRepository = p_alojamientoRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public BookingDto createBooking(BookingCreateDto bookingCreateDto,String token) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    P_Alojamiento p_alojamiento = p_alojamientoRepository.findById(bookingCreateDto.getPalojamientoId()).
      orElseThrow(() -> new CustomException(404, "El alojamiento no existe"));

    Usuario propietario = usuarioRepository.findById(userId)
      .orElseThrow(() -> new CustomException(404, "Arrendador no encontrado"));

    Usuario inquilino = usuarioRepository.findById(bookingCreateDto.getInquilinoId())
      .orElseThrow(() -> new CustomException(404, "Inquilino no encontrado"));

    if (bookingCreateDto.getInquilinoId() == userId) {
      throw new CustomException(406, "No puedes reservar tu propio alojamiento");
    }

    Booking booking = new Booking();
    booking.setPAlojamiento(p_alojamiento);
    booking.setInquilino(inquilino);
    booking.setArrendador(propietario);
    
    try {
      Booking bookingGuardado = bookingRepository.save(booking);

      return new BookingDto(
        bookingGuardado.getId(),
        bookingGuardado.getArrendador().getId(),
        bookingGuardado.getInquilino().getId(),
        bookingGuardado.getPAlojamiento().getId()
      );
    }
    catch (DataIntegrityViolationException e) {
      throw new CustomException(400, "Booking no creado");
    }
  }

  public List<BookingDto> getAllBookings(String token) {
      Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
      Usuario usuario = usuarioRepository.findById(userId)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    List<Booking> bookings = bookingRepository.findByArrendadorId(userId);

    return bookings.stream().map(booking -> new BookingDto(
      booking.getId(),
      booking.getArrendador().getId(),
      booking.getInquilino().getId(),
      booking.getPAlojamiento().getId()
    )).collect(Collectors.toList());
  }

  public List<BookingDto> getAllBookingsPublication(String token, Long publicationId) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    Usuario usuario = usuarioRepository.findById(userId)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    P_Alojamiento p_alojamiento = p_alojamientoRepository.findById(publicationId)
        .orElseThrow(() -> new CustomException(404, "Publicacion no encontrada"));

    List<Booking> bookings = bookingRepository.findByPAlojamientoId(publicationId);

    return bookings.stream().map(booking -> new BookingDto(
      booking.getId(),
      booking.getArrendador().getId(),
      booking.getInquilino().getId(),
      booking.getPAlojamiento().getId()
    )).collect(Collectors.toList());
  }

  public void deleteBooking(String token, Long bookingId) {
      Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    Usuario usuario = usuarioRepository.findById(userId)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new CustomException(404, "Booking no encontrado"));

    if (booking.getArrendador().getId() != userId) {
      throw new CustomException(403, "No puedes borrar un booking que no es tuyo");
    }

    bookingRepository.delete(booking);
  }
}
