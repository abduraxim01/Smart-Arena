package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTOForRequest {

    private String name;

    private List<UUID> playersId;
}
