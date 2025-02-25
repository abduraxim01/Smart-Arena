package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.MatchDTOForRequest;
import com.practise.Smart_Arena.DTO.requestDTO.StatusDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.MatchDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.StatusMapper;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.owner.Status;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.PolyaRepository;
import com.practise.Smart_Arena.repository.StatusRepository;
import com.practise.Smart_Arena.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class StatusService {

    final private TeamRepository teamRep;

    final private PlayerRepository playerRep;

    final private PolyaRepository polyaRep;

    final private MatchService matchSer;

    final private StatusRepository statusRep;

    final private StatusMapper statusMap;

    @Autowired
    public StatusService(TeamRepository teamRep, PlayerRepository playerRep, PolyaRepository polyaRep, MatchService matchSer, StatusRepository statusRep, StatusMapper statusMap) {
        this.teamRep = teamRep;
        this.playerRep = playerRep;
        this.polyaRep = polyaRep;
        this.matchSer = matchSer;
        this.statusRep = statusRep;
        this.statusMap = statusMap;
    }

    public MatchDTOForResponse createStatus(StatusDTOForRequest statusDTO) {
        playerRep.findById(statusDTO.getBookerId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Polya polya = polyaRep.findById(statusDTO.getPolyaId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Polya not found"));
        if (isBooked(polya.getStatusList(), statusDTO.getDay(), statusDTO.getStartTime(), statusDTO.getEndTime())) {
            statusRep.save(statusMap.toModel(statusDTO, polya));
            return matchSer.createMatch(new MatchDTOForRequest(statusDTO.getDay(), statusDTO.getStartTime(), statusDTO.getBookerId(), statusDTO.getPolyaId()));
        }
        throw new AllExceptions.UsernameAlreadyTakenException("Polya already booked");
    }

    public boolean isBooked(List<Status> statusListFromBase, LocalDate day, LocalTime startTime, LocalTime endTime) {
        for (Status status : statusListFromBase) {
            if (!status.getDay().equals(day)) return true;
            if (startTime.isAfter(endTime) || status.getStartTime().isBefore(endTime) || status.getStartTime().isBefore(startTime) && status.getEndTime().isAfter(startTime))
                return false;
        }
        return true;
    }
}
