package com.practise.Smart_Arena.DTO.responseDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTOForResponse {

    private UUID id;

    private String token;

    private String role;
}
