package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.StatusDTOForRequest;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.owner.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public Status toModel(StatusDTOForRequest statusDTO, Polya polya) {
        return Status.builder()
                .day(statusDTO.getDay())
                .polya(polya)
                .bookerId(statusDTO.getBookerId())
                .startTime(statusDTO.getStartTime())
                .endTime(statusDTO.getEndTime())
                .build();
    }
}
