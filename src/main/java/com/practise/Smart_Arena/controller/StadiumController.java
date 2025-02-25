package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.StadiumDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/stadium")
public class StadiumController {

    @Autowired
    private StadiumService stadiumSer;

    @PreAuthorize(value = "hasRole('OWNER')")
    @PostMapping(value = "/createStadium/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStadium(@PathVariable(name = "ownerId") UUID ownerId, @RequestBody StadiumDTOForRequest stadiumDTO) {
        try {
            return ResponseEntity.ok(stadiumSer.createStadium(stadiumDTO, ownerId));
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
