package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDTOForRequest {

    private String name;

    private String surname;

    private LocalDate birthday;

    private String passport;

    private String phoneNumber;
}
