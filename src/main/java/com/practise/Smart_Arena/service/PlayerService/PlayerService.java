package com.practise.Smart_Arena.service.PlayerService;

import com.practise.Smart_Arena.DTO.requestDTO.PlayerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.InviteMessageDTOForResponse;
import com.practise.Smart_Arena.DTO.responseDTO.PlayerDTOForResponse;
import com.practise.Smart_Arena.DTO.responseDTO.StatusDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.InviteMessageMapper;
import com.practise.Smart_Arena.mapper.PlayerMapper;
import com.practise.Smart_Arena.mapper.StatusMapper;
import com.practise.Smart_Arena.model.owner.Status;
import com.practise.Smart_Arena.model.player.Player;
import com.practise.Smart_Arena.repository.InviteMessageRepository;
import com.practise.Smart_Arena.repository.OwnerRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.service.PhoneNumberFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    final private PlayerRepository playerRep;

    final private PlayerMapper playerMap;

    final private PhoneNumberFilter phoneNumberFilter;

    final private InviteMessageRepository invMessageRep;

    final private InviteMessageMapper invMessageMap;

    final private OwnerRepository ownerRep;

    final private StatusMapper statusMap;

    final private Logger log = LogManager.getLogger(PlayerService.class);

    @Autowired
    public PlayerService(PlayerRepository playerRep, PlayerMapper playerMap, PhoneNumberFilter phoneNumberFilter, InviteMessageRepository invMessageRep, InviteMessageMapper invMessageMap, OwnerRepository ownerRep, StatusMapper statusMap) {
        this.playerRep = playerRep;
        this.playerMap = playerMap;
        this.phoneNumberFilter = phoneNumberFilter;
        this.invMessageRep = invMessageRep;
        this.invMessageMap = invMessageMap;
        this.ownerRep = ownerRep;
        this.statusMap = statusMap;
    }

    public PlayerDTOForResponse registerPlayer(PlayerDTOForRequest playerDTO) {
        phoneNumberFilter.isValidPhoneNumber(playerDTO.getPhoneNumber());
        if (playerDTO.getName().length() < 3 || playerDTO.getSurname().length() < 3) {
            log.error("User's name: {} or surname: {} is very short", playerDTO.getName(), playerDTO.getSurname());
            throw new AllExceptions.IllegalArgumentException("User's name or surname must be at least 3 characters");
        }
        if (playerRep.existsByPhoneNumber(playerDTO.getPhoneNumber())) {
            log.error("PhoneNumber: {} already exists as Player", playerDTO.getPhoneNumber());
            throw new AllExceptions.UsernameAlreadyTakenException("PhoneNumber: " + playerDTO.getPhoneNumber() + " already exists as Player");
        }
        if (ownerRep.existsByPhoneNumber(playerDTO.getPhoneNumber())) {
            log.error("PhoneNumber: {} already exists as Owner", playerDTO.getPhoneNumber());
            throw new AllExceptions.UsernameAlreadyTakenException("PhoneNumber: " + playerDTO.getPhoneNumber() + " already exists as Player");
        }
        try {
            return playerMap.toDTO(playerRep.save(playerMap.toModel(playerDTO)));
        } catch (AllExceptions.DataIntegrityViolationException exception) {
            throw new AllExceptions.DataIntegrityViolationException("Database constraint violation: " + exception.getMessage());
        }
    }

    public PlayerDTOForResponse getPlayerById(UUID playerId) {
        return playerMap.toDTO(playerRep.findById(playerId).orElseThrow(() ->
                new AllExceptions.EntityNotFoundException("Player with id: " + playerId + " not found")));
    }

    public List<InviteMessageDTOForResponse> getAllMessages(UUID playerId) {
        playerRep.findById(playerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player with id: " + playerId + " not found"));
        return invMessageMap.toDTO(invMessageRep.findInviteMessageByRecipientId(playerId));
    }

    public List<StatusDTOForResponse> getActiveBooks(UUID playerId) {
        Player player = playerRep.findById(playerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"));
        return statusMap.toDTO(player.getStatusList().stream()
                .filter(status -> status.getEndTime().isBefore(LocalTime.now()))
                .toList());
    }
}
