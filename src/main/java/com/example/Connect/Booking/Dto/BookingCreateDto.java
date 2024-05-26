package com.example.Connect.Booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BookingCreateDto {
    private Long inquilinoId;
    private Long palojamientoId;
}
