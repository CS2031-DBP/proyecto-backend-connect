package com.example.forutec2.Comentarios.Dto;

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
public class ComentariosDto {
    private Long id;
    private String body;
    private ZonedDateTime fechaPublicacion;
    private Long publicacionId;
    private Long usuarioId;
}
