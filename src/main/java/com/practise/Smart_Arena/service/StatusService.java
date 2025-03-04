package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.CancelPolyaDTO;
import com.practise.Smart_Arena.DTO.requestDTO.MatchDTOForRequest;
import com.practise.Smart_Arena.DTO.requestDTO.StatusDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.MatchDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.StatusMapper;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.owner.Status;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.model.player.PlayerMatchStats;
import com.practise.Smart_Arena.repository.PlayerMatchStatsRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.PolyaRepository;
import com.practise.Smart_Arena.repository.StatusRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class StatusService {

    final private PlayerRepository playerRep;

    final private PolyaRepository polyaRep;

    final private MatchService matchSer;

    final private StatusRepository statusRep;

    final private StatusMapper statusMap;

    final private PlayerMatchStatsRepository playerMatchStatsRep;

    @Value("${POLYA_CANCEL_TIME}")
    private String POLYA_CANCEL_TIME;

    final private Logger log = LogManager.getLogger(StatusService.class);

    @Autowired
    public StatusService(PlayerRepository playerRep, PolyaRepository polyaRep, MatchService matchSer, StatusRepository statusRep, StatusMapper statusMap, PlayerMatchStatsRepository playerMatchStatsRep) {
        this.playerRep = playerRep;
        this.polyaRep = polyaRep;
        this.matchSer = matchSer;
        this.statusRep = statusRep;
        this.statusMap = statusMap;
        this.playerMatchStatsRep = playerMatchStatsRep;
    }

    public MatchDTOForResponse createStatus(StatusDTOForRequest statusDTO, UUID playerId) {
        Player player = playerRep.findById(playerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Polya polya = polyaRep.findById(statusDTO.getPolyaId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Polya not found"));
        if (isBooked(polya.getStatusList(), statusDTO.getDay(), statusDTO.getStartTime(), statusDTO.getEndTime())) {
            statusRep.save(statusMap.toModel(statusDTO, polya, player));
            return matchSer.createMatch(new MatchDTOForRequest(statusDTO.getDay(), statusDTO.getStartTime(), playerId, statusDTO.getPolyaId()));
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

    public void cancelPolya(UUID statusId, UUID playerId) {
        Status status = statusRep.findById(statusId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Book not found"));
        Player player = playerRep.findById(playerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        if (status.getStartTime().getHour() < LocalTime.now().getHour() - Integer.parseInt(POLYA_CANCEL_TIME)) {
            log.info("Player: {} canceled polya: {} . Match time: {}", playerId, status.getPolya().getId(), status.getStartTime());
            PlayerMatchStats stats = player.getPlayerMatchStats();
            stats.setCanceled(stats.getCanceled() + 1);
            playerMatchStatsRep.save(stats);
            statusRep.delete(status);
        }
        log.warn("Player: {} could not cancel book, Status: {}", playerId, status.getId());
        throw new AllExceptions.InternalServerError("You cannot cancel book less than " + POLYA_CANCEL_TIME + " hours before that game start");
    }

    public void markAsDidNotAttend(CancelPolyaDTO cancelDTO) {
        Player player = playerRep.findById(cancelDTO.bookerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        Status status = statusRep.findById(cancelDTO.statusId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Book not found"));
        if (status.getDay().equals(LocalDate.now()) && status.getStartTime().isBefore(LocalTime.now().minusMinutes(30))) {
            log.info("Owner marked Player: {} as did not attend polya: {} . Match time: {}", cancelDTO.bookerId, status.getPolya().getId(), status.getStartTime());
            PlayerMatchStats stats = player.getPlayerMatchStats();
            stats.setDidNotAttended(stats.getDidNotAttended() + 1);
            playerMatchStatsRep.save(stats);
            statusRep.delete(status);
        }
        log.warn("Owner tried to mark as do not attend Player");
        throw new AllExceptions.InternalServerError("You cannot mark as do not attend Player before half hours from game start");
    }
}
