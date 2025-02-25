package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.CommentDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentSer;

    @PreAuthorize(value = "hasRole('PLAYER')")
    @PostMapping(value = "/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentDTOForRequest commentDTO) {
        try {
            return ResponseEntity.ok(commentSer.createComment(commentDTO));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
