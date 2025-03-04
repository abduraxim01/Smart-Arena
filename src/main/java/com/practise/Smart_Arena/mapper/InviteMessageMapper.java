package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.InviteDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.InviteMessageDTOForResponse;
import com.practise.Smart_Arena.model.player.InviteMessage;
import com.practise.Smart_Arena.model.player.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InviteMessageMapper {

    public InviteMessage toModel(InviteDTOForRequest invite, Team team) {
        return InviteMessage.builder()
                .senderId(invite.getSenderId())
                .teamId(team.getId())
                .recipientId(invite.getRecipientId())
                .teamName(team.getName())
                .message(invite.getMessage())
                .build();
    }

    public InviteMessageDTOForResponse toDTO(InviteMessage invMessage) {
        return InviteMessageDTOForResponse.builder()
                .id(invMessage.getId())
                .message(invMessage.getMessage())
                .teamId(invMessage.getTeamId())
                .teamName(invMessage.getTeamName())
                .build();
    }

    public List<InviteMessageDTOForResponse> toDTO(List<InviteMessage> inviteMessages) {
        if (inviteMessages == null) return new ArrayList<>();
        return inviteMessages.stream()
                .map(this::toDTO)
                .toList();
    }
}
