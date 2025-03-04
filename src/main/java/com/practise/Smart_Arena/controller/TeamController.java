package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.InviteDTOForRequest;
import com.practise.Smart_Arena.DTO.requestDTO.TeamDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/team")
public class TeamController {

    @Autowired
    private TeamService teamSer;

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/createTeam")
    public ResponseEntity<?> createTeam(@RequestBody TeamDTOForRequest teamDTO) {
        try {
            return ResponseEntity.ok(teamSer.createTeam(teamDTO));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // did not add
    @PostMapping(value = "/sendInviteMessage")
    public ResponseEntity<?> sendInviteMessage(@RequestBody InviteDTOForRequest inviteDTO) {
        return ResponseEntity.ok(null);
    }
}
