package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.CancelPolyaDTO;
import com.practise.Smart_Arena.DTO.requestDTO.OwnerDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.service.OwnerService;
import com.practise.Smart_Arena.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/owner")
public class OwnerController {

    final private OwnerService ownerSer;

    final private StatusService statusSer;

    @Autowired
    public OwnerController(OwnerService ownerSer, StatusService statusSer) {
        this.ownerSer = ownerSer;
        this.statusSer = statusSer;
    }

    @PostMapping(value = "/registerOwner", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerOwner(@RequestBody OwnerDTOForRequest ownerDTO) {
        try {
            return ResponseEntity.ok(ownerSer.registerOwner(ownerDTO));
        } catch (AllExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // did not add
    @PreAuthorize(value = "hasRole('OWNER')")
    @PostMapping(value = "/markAsDidNotAttend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> markAsDidNotAttend(@RequestBody CancelPolyaDTO cancelPolyaDTO) {
        try {
            statusSer.markAsDidNotAttend(cancelPolyaDTO);
            return new ResponseEntity<>("Successfully", HttpStatus.ACCEPTED);
        } catch (AllExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}