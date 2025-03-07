package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.CommentDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.CommentDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.service.CommentService;
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
@RequestMapping(value = "/api/comment")
public class CommentController {

    final private CommentService commentSer;

    @Autowired
    public CommentController(CommentService commentSer) {
        this.commentSer = commentSer;
    }

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/createComment")
    public ResponseEntity<CommentDTOForResponse> createComment(@RequestBody CommentDTOForRequest commentDTO, Authentication authentication) {
        UUID playerId = ((Player) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(commentSer.createComment(commentDTO, playerId));
    }
}
