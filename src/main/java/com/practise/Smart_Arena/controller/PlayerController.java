package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.PlayerDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.PlayerService.PlayerService;
import com.practise.Smart_Arena.service.PolyaService;
import com.practise.Smart_Arena.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/player")
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

    @PostMapping(value = "/registerPlayer")
    public ResponseEntity<?> registerPlayer(@RequestBody PlayerDTOForRequest playerDTO) {
        try {
            return ResponseEntity.ok(playerSer.registerPlayer(playerDTO));
        } catch (AllExceptions.DataIntegrityViolationException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // did not add
    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/cancelPolya/{statusId}")
    public ResponseEntity<String> cancelPolya(@PathVariable UUID statusId, Authentication authentication) {
        try {
            UUID playerId = ((Player) authentication.getPrincipal()).getId();
            statusSer.cancelPolya(statusId, playerId);
            return new ResponseEntity<>("Successfully", HttpStatus.ACCEPTED);
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.InternalServerError exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    @PreAuthorize("hasAnyRole('PLAYER','OWNER')")
    @GetMapping(value = "/getPlayerById/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable UUID playerId) {
        try {
            return ResponseEntity.ok(playerSer.getPlayerById(playerId));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // did not add
    @PreAuthorize(value = "hasRole('PLAYER')")
    @GetMapping(value = "/getAllMessages")
    public ResponseEntity<?> getAllMessages(Authentication authentication) {
        try {
            UUID playerId = ((Player) authentication.getPrincipal()).getId();
            return ResponseEntity.ok(playerSer.getAllMessages(playerId));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // did not add
    @PreAuthorize(value = "hasRole('PLAYER')")
    @GetMapping(value = "/getActiveBooks")
    public ResponseEntity<?> getActiveBooks(Authentication authentication) {
        try {
            UUID playerId = ((Player) authentication.getPrincipal()).getId();
            return ResponseEntity.ok(playerSer.getActiveBooks(playerId));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }

    }

    // did not add
    @GetMapping(value = "/getTopPolyas")
    public ResponseEntity<?> getTopPolyas() {
        try {
            return ResponseEntity.ok(polyaSer.getTopPolyas());
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
