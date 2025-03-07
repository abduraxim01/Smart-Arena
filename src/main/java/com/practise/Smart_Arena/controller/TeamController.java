package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.TeamDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.TeamDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/team", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    final private TeamService teamSer;

    @Autowired
    public TeamController(TeamService teamSer) {
        this.teamSer = teamSer;
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/createTeam")
    public ResponseEntity<TeamDTOForResponse> createTeam(@RequestBody TeamDTOForRequest teamDTO, Authentication authentication) {
        UUID creatorId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(teamSer.createTeam(teamDTO, creatorId));
    }
}
