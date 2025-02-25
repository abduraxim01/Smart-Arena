package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.PlayerDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/player")
public class PlayerController {

    private final PlayerService playerSer;

    @Autowired
    public PlayerController(PlayerService playerSer) {
        this.playerSer = playerSer;
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

}
