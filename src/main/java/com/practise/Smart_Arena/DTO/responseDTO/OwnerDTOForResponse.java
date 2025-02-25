package com.practise.Smart_Arena.DTO.responseDTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDTOForResponse {

    private UUID id;

    private String name;

    private String surname;

    private LocalDate birthday;

    private String passport;

    private String phoneNumber;

    private List<StadiumDTOForResponse> stadiumList;
}
