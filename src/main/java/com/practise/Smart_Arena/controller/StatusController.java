package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.StatusDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.MatchDTOForResponse;
import com.practise.Smart_Arena.DTO.responseDTO.StatusDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/status")
public class StatusController {

    final private StatusService statusSer;

    @Autowired
    public StatusController(StatusService statusSer) {
        this.statusSer = statusSer;
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/createStatus")
    public ResponseEntity<MatchDTOForResponse> createStatus(@RequestBody StatusDTOForRequest statusDTO, Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(statusSer.createStatus(statusDTO, playerId));
    }

    @PreAuthorize(value = "hasRole('OWNER')")
    @PostMapping(value = "/getActiveStatusForToday")
    public ResponseEntity<List<StatusDTOForResponse>> getActiveStatusForToday(Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(statusSer.getActiveStatusForToday(playerId));
    }
}
