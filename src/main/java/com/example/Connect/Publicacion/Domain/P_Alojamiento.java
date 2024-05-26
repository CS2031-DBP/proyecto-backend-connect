package com.example.forutec2.Publicacion.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.example.forutec2.Review.Domain.Review;
import com.example.forutec2.Booking.Domain.Booking;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_alojamiento")
public class P_Alojamiento {

  @Id
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "publicacion_id", nullable = false)
  @JsonBackReference
  private Publicacion publicacion;

  @Column(nullable = false)
  private Double precio;

  @Column(length = 150, nullable = false)
  private String direccion;

  @Column(length = 150, nullable = false)
  private String referencia;

  @Column(nullable = false)
  private Boolean disponible;

  @OneToMany(mappedBy = "pAlojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Review> reviews;

  @OneToMany(mappedBy = "pAlojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Booking> bookings;

  @PrePersist
  public void prePersist() {
      this.disponible = true;
  }
}
