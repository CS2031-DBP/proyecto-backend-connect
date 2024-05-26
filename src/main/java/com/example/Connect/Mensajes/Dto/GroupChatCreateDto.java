package com.example.Connect.Mensajes.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupChatCreateDto {
    private String nameChat;
    private List<Long> userIds;
}
