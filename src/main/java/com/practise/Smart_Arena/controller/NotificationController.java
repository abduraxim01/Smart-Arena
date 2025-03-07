package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.InviteDTOForRequest;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.player.message.InviteMessage;
import com.practise.Smart_Arena.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/notification")
public class NotificationController {

    final private NotificationService notService;

    @Autowired
    public NotificationController(NotificationService notService) {
        this.notService = notService;
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @GetMapping(value = "/getNotifications")
    public ResponseEntity<List<InviteMessage>> getNewNotifications(Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(notService.getNewNotifications(playerId));
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/sendInviteNotification")
    public ResponseEntity<Void> sendInviteNotification(@RequestBody InviteDTOForRequest inviteDTO, Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        notService.sendInviteNotification(inviteDTO, playerId);
        return ResponseEntity.ok().build();
    }
}
