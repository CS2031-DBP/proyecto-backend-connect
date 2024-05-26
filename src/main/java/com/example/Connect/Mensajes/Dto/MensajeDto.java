package com.example.forutec2.Mensajes.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class MensajeDto {
    private Long chatId;
    private Long userId;
    private String body;
}
