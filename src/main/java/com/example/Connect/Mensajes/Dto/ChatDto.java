package com.example.Connect.Mensajes.Dto;

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
public class ChatDto {
  private Long id;
  private Boolean isGroup;
  private String nameChat;
}
