package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.OwnerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.OwnerDTOForResponse;
import com.practise.Smart_Arena.mapper.OwnerMapper;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.repository.OwnerRepository;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownRep;

    final private OwnerMapper ownMap = new OwnerMapper();

    final private Logger log = LogManager.getLogger(OwnerService.class);

    final private PhoneNumberFilter phoneNumberFilter = new PhoneNumberFilter();

    public OwnerDTOForResponse registerOwner(OwnerDTOForRequest ownerDTO) throws ConstraintViolationException {
        phoneNumberFilter.isValidPhoneNumber(ownerDTO.getPhoneNumber());
        if (ownRep.existsByPhoneNumber(ownerDTO.getPhoneNumber())) {
            log.error("Phone number already exists: {}", ownerDTO.getPhoneNumber());
            throw new AllExceptions.UsernameAlreadyTakenException("Phone number already exists: " + ownerDTO.getPhoneNumber());
        }
        if (ownerDTO.getName().length() < 3 || ownerDTO.getSurname().length() < 3) {
            log.error("User's name: {} or surname: {} is very short", ownerDTO.getName(), ownerDTO.getSurname());
            throw new AllExceptions.IllegalArgumentException("User's name or surname must be at least 3 characters");
        }
        if (ownerDTO.getPassport().length() < 9) {
            log.error("User's passport: {} is very short", ownerDTO.getPassport());
            throw new AllExceptions.IllegalArgumentException("User's passport must be at least 9 characters");
        }
        log.info("New owner registered name: {} phone number: {}", ownerDTO.getName(), ownerDTO.getPhoneNumber());
        return ownMap.toDTO(ownRep.save(ownMap.toModel(ownerDTO)));
    }
}
