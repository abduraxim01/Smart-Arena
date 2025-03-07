package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.CancelPolyaDTO;
import com.practise.Smart_Arena.DTO.requestDTO.OwnerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.OwnerDTOForResponse;
import com.practise.Smart_Arena.service.OwnerService;
import com.practise.Smart_Arena.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/owner", consumes = MediaType.APPLICATION_JSON_VALUE)
public class OwnerController {

    final private OwnerService ownerSer;

    final private StatusService statusSer;

    @Autowired
    public OwnerController(OwnerService ownerSer, StatusService statusSer) {
        this.ownerSer = ownerSer;
        this.statusSer = statusSer;
    }

    @PostMapping(value = "/registerOwner")
    public ResponseEntity<OwnerDTOForResponse> registerOwner(@RequestBody OwnerDTOForRequest ownerDTO) {
        return ResponseEntity.ok(ownerSer.registerOwner(ownerDTO));
    }

    @PreAuthorize(value = "hasRole('OWNER')")
    @PostMapping(value = "/markAttend/{attendStatus}")
    public ResponseEntity<Void> markAttend(@PathVariable boolean attendStatus, @RequestBody CancelPolyaDTO cancelPolyaDTO) {
        statusSer.markAttend(attendStatus, cancelPolyaDTO);
        return ResponseEntity.accepted().build();
    }
}