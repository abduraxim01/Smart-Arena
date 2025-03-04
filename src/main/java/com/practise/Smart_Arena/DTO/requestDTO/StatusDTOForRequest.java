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
public class StatusDTOForRequest {

    private LocalDate day;

    private LocalTime startTime;

    private LocalTime endTime;

    private UUID polyaId;
}
