package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteDTOForRequest {

    private UUID senderId;

    private UUID recipientId;

    private String message;
}
