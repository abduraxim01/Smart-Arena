package com.practise.Smart_Arena.DTO.responseDTO;

import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTOForResponse {

    private UUID id;

    private LocalDate day;

    private LocalTime startTime;

    private LocalTime endTime;

    private UUID bookerId;

    private UUID polyaId;
}
