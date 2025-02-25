package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTOForRequest {

    private String phoneNumber;

    private String code;
}
