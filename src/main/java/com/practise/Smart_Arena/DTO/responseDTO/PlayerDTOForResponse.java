package com.practise.Smart_Arena.DTO.responseDTO;

import com.practise.Smart_Arena.model.player.Match;
import com.practise.Smart_Arena.model.player.PlayerMatchStats;
import com.practise.Smart_Arena.model.player.Team;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTOForResponse {

    private UUID id;

    private String name;

    private String surname;

    private String phoneNumber;

    private LocalDate birthday;

    private String birthdayPlace;

    private String residence;

    private boolean isTeamOwner;

//    private List<CommentDTOForResponse> commentList;

    private List<Match> matchList;

    private PlayerMatchStats playerMatchStats;

    private boolean isOpenJoin;

    private Team team;
}
