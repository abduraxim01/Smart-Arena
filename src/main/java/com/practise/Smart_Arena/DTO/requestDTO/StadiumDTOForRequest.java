package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;
import org.springframework.data.geo.Point;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumDTOForRequest {

    private String name;

    private LocalTime workingHoursStart;

    private LocalTime workingHoursEnd;

    private Set<DayOfWeek> workingDays;

    private String locationName;

    private Point locationPoint;

    private String rules;

    private Set<String> socialMediaAccounts;
}
