package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.StatusDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.StatusDTOForResponse;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.owner.Status;
import com.practise.Smart_Arena.model.player.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatusMapper {

    public Status toModel(StatusDTOForRequest statusDTO, Polya polya, Player player) {
        return Status.builder()
                .day(statusDTO.getDay())
                .polya(polya)
                .player(player)
                .startTime(statusDTO.getStartTime())
                .endTime(statusDTO.getEndTime())
                .build();
    }

    public StatusDTOForResponse toDTO(Status status) {
        return StatusDTOForResponse.builder()
                .id(status.getId())
                .bookerId(status.getPlayer().getId())
                .polyaId(status.getPolya().getId())
                .day(status.getDay())
                .endTime(status.getEndTime())
                .startTime(status.getStartTime())
                .build();
    }

    public List<StatusDTOForResponse> toDTO(List<Status> statusList) {
        if (statusList == null) return new ArrayList<>();
        return statusList.stream()
                .map(this::toDTO)
                .toList();
    }
}
