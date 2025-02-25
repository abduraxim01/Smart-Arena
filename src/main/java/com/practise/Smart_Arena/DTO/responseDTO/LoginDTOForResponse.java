package com.practise.Smart_Arena.DTO.responseDTO;

import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTOForResponse {

    private UUID userId;

    private String token;

    private String role;

    private Map<String, List<String>> permissions;
}
