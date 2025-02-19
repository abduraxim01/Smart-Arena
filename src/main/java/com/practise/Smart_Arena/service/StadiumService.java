package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.StadiumDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.StadiumDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.StadiumMapper;
import com.practise.Smart_Arena.model.owner.Owner;
import com.practise.Smart_Arena.repository.OwnerRepository;
import com.practise.Smart_Arena.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StadiumService {

    @Autowired
    private StadiumRepository stadiumRep;

    @Autowired
    private OwnerRepository ownerRep;

    final private StadiumMapper stadiumMap = new StadiumMapper();

    public StadiumDTOForResponse createStadium(StadiumDTOForRequest stadiumDTO, UUID ownerId) {
        Owner owner = ownerRep.findById(ownerId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Owner not found" ));
        return stadiumMap.toDTO(stadiumRep.save(stadiumMap.toModel(stadiumDTO, owner)));
    }
}
