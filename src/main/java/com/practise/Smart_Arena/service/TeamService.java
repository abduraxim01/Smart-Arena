package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.InviteDTOForRequest;
import com.practise.Smart_Arena.DTO.requestDTO.TeamDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.TeamDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.InviteMessageMapper;
import com.practise.Smart_Arena.mapper.TeamMapper;
import com.practise.Smart_Arena.model.player.InviteMessage;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.player.ResponseMessage;
import com.practise.Smart_Arena.model.player.Team;
import com.practise.Smart_Arena.repository.InviteMessageRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.ResponseMessageRepository;
import com.practise.Smart_Arena.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    final private TeamRepository teamRep;

    final private PlayerRepository playerRep;

    final private TeamMapper teamMap;

    final private InviteMessageMapper inviteMessageMap;

    final private InviteMessageRepository inviteMessageRep;

    final private ResponseMessageRepository playerResponseRep;

    @Autowired
    public TeamService(TeamMapper teamMap, PlayerRepository playerRep, TeamRepository teamRep, InviteMessageMapper inviteMessageMap, InviteMessageRepository inviteMessageRep, ResponseMessageRepository playerResponseRep) {
        this.teamMap = teamMap;
        this.playerRep = playerRep;
        this.teamRep = teamRep;
        this.inviteMessageMap = inviteMessageMap;
        this.inviteMessageRep = inviteMessageRep;
        this.playerResponseRep = playerResponseRep;
    }

    public TeamDTOForResponse createTeam(TeamDTOForRequest teamDTO) {
        Player player = playerRep.findById(teamDTO.getCreatorId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
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

    public void sendInviteMessage(InviteDTOForRequest inviteDTO) {
        Player teamOwner = playerRep.findById(inviteDTO.getSenderId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Player player = playerRep.findById(inviteDTO.getRecipientId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        if (teamOwner.isTeamOwner() && player.getTeam() == null) {
            Team ownerTeam = teamOwner.getTeam();
            if (player.isOpenJoin()) {
                autoAcceptInviteMessage(inviteDTO, ownerTeam, player);
            }
            // write logic that player must take notification about this invite
        }
    }

    public void autoAcceptInviteMessage(InviteDTOForRequest inviteDTO, Team team, Player receivingPlayer) {
        InviteMessage inviteMessage = inviteMessageMap.toModel(inviteDTO, team);
        inviteMessageRep.save(inviteMessage);
        receivingPlayer.setTeam(team);
        playerRep.save(receivingPlayer);
        playerResponseRep.save(ResponseMessage.builder()
                .inviteMessage(inviteMessage)
                .isAccepted(true)
                .build());
    }

    // if user mark as accept invite message, it will join automatically
    // must write addPlayerToTeam method
}
