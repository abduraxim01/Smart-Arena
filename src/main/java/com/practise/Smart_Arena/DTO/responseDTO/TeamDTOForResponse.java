package com.practise.Smart_Arena.DTO.responseDTO;

import com.practise.Smart_Arena.model.player.Player;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTOForResponse {

    private UUID id;

    private String name;

    private List<Player> playerList;
}
