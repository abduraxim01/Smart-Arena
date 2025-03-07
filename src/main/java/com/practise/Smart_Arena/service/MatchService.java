package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.MatchDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.MatchDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.MatchMapper;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.player.Match;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.player.Team;
import com.practise.Smart_Arena.repository.MatchRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.PolyaRepository;
import com.practise.Smart_Arena.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRep;

    @Autowired
    private PlayerRepository playerRep;

    @Autowired
    private PolyaRepository polyaRep;

    final private MatchMapper matchMap = new MatchMapper();


    public MatchDTOForResponse createMatch(MatchDTOForRequest matchDTO) {
        Player player = playerRep.findById(matchDTO.getBookerId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Polya polya = polyaRep.findById(matchDTO.getPolyaId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Polya not found"));
        return matchMap.toDTO(matchRep.save(Match.builder()
                .day(matchDTO.getDate())
                .time(matchDTO.getTime())
                .player(player)
                .polya(polya)
                .build()));
    }
}