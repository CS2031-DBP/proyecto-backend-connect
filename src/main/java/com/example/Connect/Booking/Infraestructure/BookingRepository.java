package com.example.Connect.Booking.Infraestructure;

import com.example.Connect.Booking.Domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  List<Booking> findByArrendadorId(Long arrendadorId);

  @Query("SELECT b FROM Booking b WHERE b.pAlojamiento.id = :pAlojamientoId")
  List<Booking> findByPAlojamientoId(@Param("pAlojamientoId") Long pAlojamientoId);
}
