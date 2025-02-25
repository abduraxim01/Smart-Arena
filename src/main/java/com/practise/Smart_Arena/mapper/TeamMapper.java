package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.responseDTO.TeamDTOForResponse;
import com.practise.Smart_Arena.model.player.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public TeamDTOForResponse toDTO(Team team) {
        return TeamDTOForResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .playerList(team.getPlayerList())
                .build();
    }
}
