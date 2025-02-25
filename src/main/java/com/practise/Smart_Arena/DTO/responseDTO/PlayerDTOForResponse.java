package com.practise.Smart_Arena.DTO.responseDTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practise.Smart_Arena.model.player.Comment;
import com.practise.Smart_Arena.model.player.Team;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
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

    private List<CommentDTOForResponse> commentList;

    private Team team;
}
