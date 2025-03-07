package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.TeamDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.TeamDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.TeamMapper;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.player.Team;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TeamService {

    final private TeamRepository teamRep;

    final private PlayerRepository playerRep;

    final private TeamMapper teamMap;

    @Autowired
    public TeamService(TeamMapper teamMap, PlayerRepository playerRep, TeamRepository teamRep) {
        this.teamMap = teamMap;
        this.playerRep = playerRep;
        this.teamRep = teamRep;
    }

    public TeamDTOForResponse createTeam(TeamDTOForRequest teamDTO, UUID creatorId) {
        Player player = playerRep.findById(creatorId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Team team = Team.builder()
                .name(teamDTO.getName())
                .playerList(List.of(player))
                .build();
        Team teamFromDB = teamRep.save(team);
        player.setTeam(teamFromDB);
        player.setTeamOwner(true);
        playerRep.save(player);
        return teamMap.toDTO(teamFromDB);
    }
}
