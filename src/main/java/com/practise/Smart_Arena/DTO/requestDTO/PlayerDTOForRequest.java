package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDTOForRequest {

    private String name;

    private String surname;

    private String phoneNumber;

    private LocalDate birthday;

    private String birthdayPlace;

    private String residence;
}
