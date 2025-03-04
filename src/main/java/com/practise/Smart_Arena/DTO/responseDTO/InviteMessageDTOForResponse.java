package com.practise.Smart_Arena.DTO.responseDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteMessageDTOForResponse {

    private UUID id;

    private UUID teamId;

    private String teamName;

    private String message;
}
