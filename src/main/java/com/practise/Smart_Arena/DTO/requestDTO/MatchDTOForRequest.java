package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDTOForRequest {

    private LocalDate date;

    private LocalTime time;

    private UUID bookerId;

    private UUID polyaId;
}
