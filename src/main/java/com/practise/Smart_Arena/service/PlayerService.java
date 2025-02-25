package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.PlayerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.PlayerDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.PlayerMapper;
import com.practise.Smart_Arena.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    final private PlayerRepository playerRep;

    final private PlayerMapper playerMap;

    final private PhoneNumberFilter phoneNumberFilter;

    final private Logger log = LogManager.getLogger(PlayerService.class);

    @Autowired
    public PlayerService(PlayerRepository playerRep, PlayerMapper playerMap, PhoneNumberFilter phoneNumberFilter) {
        this.playerRep = playerRep;
        this.playerMap = playerMap;
        this.phoneNumberFilter = phoneNumberFilter;
    }

    public PlayerDTOForResponse registerPlayer(PlayerDTOForRequest playerDTO) {
        phoneNumberFilter.isValidPhoneNumber(playerDTO.getPhoneNumber());
        if (playerDTO.getName().length() < 3 || playerDTO.getSurname().length() < 3) {
            log.error("User's name: {} or surname: {} is very short", playerDTO.getName(), playerDTO.getSurname());
            throw new AllExceptions.IllegalArgumentException("User's name or surname must be at least 3 characters");
        }
        if (playerRep.existsByPhoneNumber(playerDTO.getPhoneNumber())) {
            log.error("Phone number already exists: {}", playerDTO.getPhoneNumber());
            throw new AllExceptions.UsernameAlreadyTakenException("Phone number already exists: " + playerDTO.getPhoneNumber());
        }
        try {
            return playerMap.toDTO(playerRep.save(playerMap.toModel(playerDTO)));
        } catch (AllExceptions.DataIntegrityViolationException exception) {
            throw new AllExceptions.DataIntegrityViolationException("Database constraint violation: " + exception.getMessage());
        }
    }
}
