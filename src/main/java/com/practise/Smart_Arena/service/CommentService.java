package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.CommentDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.CommentDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.CommentMapper;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.player.Comment;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.repository.CommentRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.PolyaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRep;

    @Autowired
    private PlayerRepository playerRep;

    @Autowired
    private PolyaRepository polyaRep;

    final private CommentMapper commentMap = new CommentMapper();

    public CommentDTOForResponse createComment(CommentDTOForRequest commentDTO, UUID playerId) {
        Polya polya = polyaRep.findById(UUID.fromString(commentDTO.getPolyaId())).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Polya not found"));
        Player player = playerRep.findById(playerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Comment comment = commentRep.save(commentMap.toModel(commentDTO, player, polya));
        calculateAverageStar(polya);
        return commentMap.toDTO(comment);
    }

    public  synchronized void calculateAverageStar(Polya polya) {
        List<Comment> commentList = commentRep.findCommentsByPolya(polya);
        if (commentList.isEmpty()) {
            polya.setStars(0);
            return;
        }
        double allStars = commentList.stream().mapToDouble(Comment::getStar).sum();
        polya.setStars(allStars / commentList.size());
        polyaRep.save(polya);
    }
}
