package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.PlayerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.PlayerDTOForResponse;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.privileges.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerMapper {

    final private CommentMapper commentMap;

    @Autowired
    public PlayerMapper(CommentMapper commentMap) {
        this.commentMap = commentMap;
    }

    public Player toModel(PlayerDTOForRequest playerDTO) {
        return Player.builder()
                .name(playerDTO.getName())
                .surname(playerDTO.getSurname())
                .birthday(playerDTO.getBirthday())
                .birthdayPlace(playerDTO.getBirthdayPlace())
                .phoneNumber(playerDTO.getPhoneNumber())
                .residence(playerDTO.getResidence())
                .role(Role.PLAYER)
                .build();
    }

    public PlayerDTOForResponse toDTO(Player player) {
        return PlayerDTOForResponse.builder()
                .id(player.getId())
                .name(player.getName())
                .surname(player.getSurname())
                .phoneNumber(player.getPhoneNumber())
                .birthday(player.getBirthday())
                .birthdayPlace(player.getBirthdayPlace())
                .residence(player.getResidence())
                .commentList(commentMap.toDTO(player.getCommentList()))
                .team(player.getTeam())
                .isTeamOwner(player.isTeamOwner())
                .build();
    }

    public List<PlayerDTOForResponse> toDTO(List<Player> playerList) {
        if (playerList == null) return new ArrayList<>();
        return playerList.stream()
                .map(this::toDTO)
                .toList();
    }
}
