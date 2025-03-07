package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.InviteDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.InviteMessageMapper;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.player.Team;
import com.practise.Smart_Arena.model.player.message.InviteMessage;
import com.practise.Smart_Arena.model.player.message.ResponseMessage;
import com.practise.Smart_Arena.repository.InviteMessageRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.ResponseMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NotificationService {

    final private InviteMessageRepository invMessageRep;

    final private InviteMessageMapper inviteMessageMap;

    final private InviteMessageRepository inviteMessageRep;

    final private ResponseMessageRepository playerResponseRep;

    final private PlayerRepository playerRep;

    @Autowired
    public NotificationService(InviteMessageRepository invMessageRep, InviteMessageMapper inviteMessageMap, InviteMessageRepository inviteMessageRep, ResponseMessageRepository playerResponseRep, PlayerRepository playerRep) {
        this.invMessageRep = invMessageRep;
        this.inviteMessageMap = inviteMessageMap;
        this.inviteMessageRep = inviteMessageRep;
        this.playerResponseRep = playerResponseRep;
        this.playerRep = playerRep;
    }

    public List<InviteMessage> getNewNotifications(UUID playerID) {
        return invMessageRep.findInviteMessageByRecipientId(playerID)
                .stream()
                .filter(message -> message.getResponse() != null)
                .toList();
    }

    public void sendInviteNotification(InviteDTOForRequest inviteDTO, UUID playerId) {
        Player teamOwner = playerRep.findById(playerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Player player = playerRep.findById(inviteDTO.getRecipientId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        if (teamOwner.isTeamOwner() && player.getTeam() == null) {
            Team ownerTeam = teamOwner.getTeam();
            InviteMessage inviteMessage = inviteMessageMap.toModel(inviteDTO, ownerTeam, playerId);
            inviteMessageRep.save(inviteMessage);
            if (player.isOpenJoin()) {
                autoAcceptInviteNotification(inviteMessage, ownerTeam, player);
            }
        }
    }

    public void autoAcceptInviteNotification(InviteMessage inviteMessage, Team team, Player receivingPlayer) {
        receivingPlayer.setTeam(team);
        playerRep.save(receivingPlayer);
        playerResponseRep.save(ResponseMessage.builder()
                .inviteMessage(inviteMessage)
                .isAccepted(true)
                .build());
    }
}
