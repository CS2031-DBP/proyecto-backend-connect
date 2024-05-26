package com.example.forutec2.Booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BookingCreateDto {
    private Long inquilinoId;
    private Long palojamientoId;
}
