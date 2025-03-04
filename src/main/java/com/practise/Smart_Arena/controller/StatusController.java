package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.StatusDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/status")
public class StatusController {

    @Autowired
    private StatusService statusSer;

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/createStatus")
    public ResponseEntity<?> createStatus(@RequestBody StatusDTOForRequest statusDTO, Authentication authentication) {
        try {
            UUID playerId = ((Player) authentication.getPrincipal()).getId();
            return ResponseEntity.ok(statusSer.createStatus(statusDTO, playerId));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
