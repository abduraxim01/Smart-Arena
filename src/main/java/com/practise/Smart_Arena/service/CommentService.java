package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.CommentDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.CommentDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.CommentMapper;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.repository.CommentRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.PolyaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRep;

    @Autowired
    private PlayerRepository playerRep;

    @Autowired
    private PolyaRepository polyaRep;

    final private CommentMapper commentMap = new CommentMapper();

    public CommentDTOForResponse createComment(CommentDTOForRequest commentDTO) {
        Polya polya = polyaRep.findById(UUID.fromString(commentDTO.getPolyaId())).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Polya not found"));
        Player player = playerRep.findById(UUID.fromString(commentDTO.getPlayerId())).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        return commentMap.toDTO(commentRep.save(commentMap.toModel(commentDTO, player, polya)));
    }
}
