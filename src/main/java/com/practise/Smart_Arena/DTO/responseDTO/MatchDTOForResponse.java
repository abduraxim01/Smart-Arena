package com.practise.Smart_Arena.DTO.responseDTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDTOForResponse {

    private UUID id;

    private LocalDate day;

    private LocalTime time;

    private UUID polyaId;

    private UUID bookerId;
}
