package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.responseDTO.MatchDTOForResponse;
import com.practise.Smart_Arena.model.player.Match;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchMapper {

    public MatchDTOForResponse toDTO(Match match) {
        return MatchDTOForResponse.builder()
                .id(match.getId())
                .day(match.getDay())
                .teamId(match.getTeam().getId())
                .polyaId(match.getTeam().getId())
                .time(match.getTime())
                .build();
    }

    public List<MatchDTOForResponse> toDTO(List<Match> matchList) {
        if (matchList == null) return new ArrayList<>();
        return matchList.stream()
                .map(this::toDTO)
                .toList();
    }
}
