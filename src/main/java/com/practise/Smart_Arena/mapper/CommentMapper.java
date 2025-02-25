package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.CommentDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.CommentDTOForResponse;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.player.Comment;
import com.practise.Smart_Arena.model.player.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public Comment toModel(CommentDTOForRequest commentDTO, Player player, Polya polya) {
        return Comment.builder()
                .body(commentDTO.getBody())
                .polya(polya)
                .player(player)
                .star(commentDTO.getStar())
                .build();
    }

    public CommentDTOForResponse toDTO(Comment comment) {
        return CommentDTOForResponse.builder()
                .id(comment.getId())
                .star(comment.getStar())
                .body(comment.getBody())
                .build();
    }

    public List<CommentDTOForResponse> toDTO(List<Comment> commentList) {
        if (commentList == null) return new ArrayList<>();
        return commentList.stream()
                .map(this::toDTO)
                .toList();
    }
}
