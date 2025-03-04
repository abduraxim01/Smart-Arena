package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/notification")
public class NotificationController {

    final private NotificationService notService;

    @Autowired
    public NotificationController(NotificationService notService) {
        this.notService = notService;
    }

    // did not add
    @GetMapping
    @PreAuthorize(value = "hasRole('PLAYER')")
    public ResponseEntity<?> getNewNotifications(Authentication authentication) {
        try {
            UUID playerId = ((Player) authentication.getPrincipal()).getId();
            return ResponseEntity.ok(notService.getNewNotifications(playerId));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
