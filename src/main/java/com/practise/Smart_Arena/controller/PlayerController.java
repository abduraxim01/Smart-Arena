package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.PlayerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.InviteMessageDTOForResponse;
import com.practise.Smart_Arena.DTO.responseDTO.PlayerDTOForResponse;
import com.practise.Smart_Arena.DTO.responseDTO.PolyaDTOForResponse;
import com.practise.Smart_Arena.DTO.responseDTO.StatusDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.PlayerService.PlayerService;
import com.practise.Smart_Arena.service.PolyaService;
import com.practise.Smart_Arena.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/player", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    final private PlayerService playerSer;

    final private StatusService statusSer;

    final private PolyaService polyaSer;

    @Autowired
    public PlayerController(PlayerService playerSer, StatusService statusSer, PolyaService polyaSer) {
        this.playerSer = playerSer;
        this.statusSer = statusSer;
        this.polyaSer = polyaSer;
    }

    // response ni field larini qaytadan tekwiriw
    @PostMapping(value = "/registerPlayer")
    public ResponseEntity<PlayerDTOForResponse> registerPlayer(@RequestBody PlayerDTOForRequest playerDTO) {
        return ResponseEntity.ok(playerSer.registerPlayer(playerDTO));
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/cancelPolya/{statusId}")
    public ResponseEntity<Void> cancelPolya(@PathVariable UUID statusId, Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        // write delete match logic inside cancelPolya method in service
        statusSer.cancelPolya(statusId, playerId);
        return ResponseEntity.accepted().build();

    }

    @PreAuthorize("hasAnyRole('PLAYER','OWNER')")
    @GetMapping(value = "/getPlayerById/{playerId}")
    public ResponseEntity<PlayerDTOForResponse> getPlayerById(@PathVariable UUID playerId) {
        return ResponseEntity.ok(playerSer.getPlayerById(playerId));
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @GetMapping(value = "/getAllMessages")
    public ResponseEntity<List<InviteMessageDTOForResponse>> getAllMessages(Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(playerSer.getAllMessages(playerId));
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @GetMapping(value = "/getActiveBooks")
    public ResponseEntity<List<StatusDTOForResponse>> getActiveBooks(Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(playerSer.getActiveBooks(playerId));
    }

    @GetMapping(value = "/getTopPolyas")
    public ResponseEntity<List<PolyaDTOForResponse>> getTopPolyas() {
        return ResponseEntity.ok(polyaSer.getTopPolyas());
    }
}
