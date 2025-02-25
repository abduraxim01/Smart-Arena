package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.responseDTO.TeamDTOForResponse;
import com.practise.Smart_Arena.model.player.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    final private PlayerMapper playerMap;

    final private MatchMapper matchMap;

    @Autowired
    public TeamMapper(PlayerMapper playerMap, MatchMapper matchMap) {
        this.playerMap = playerMap;
        this.matchMap = matchMap;
    }

    public TeamDTOForResponse toDTO(Team team) {
        return TeamDTOForResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .playerList(team.getPlayerList())
                .matchList(team.getMatchList())
                .build();
    }

//    public TeamDTOForResponse toDTOWithoutPlayers(Team team) {
//        return TeamDTOForResponse.builder()
//                .id(team.getId())
//                .name(team.getName())
//                .playerList(playerMap.toDTO(team.getPlayerList()))
//                .matchList(matchMap.toDTO(team.getMatchList()))
//                .build();
//    }

    public TeamDTOForResponse toDTOForPlayerMapper(Team team) {
        return TeamDTOForResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .matchList(team.getMatchList())
                .build();
    }
}
