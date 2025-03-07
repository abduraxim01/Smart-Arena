package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.QulayliklarDTOForRequest;
import com.practise.Smart_Arena.service.QulayliklarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/qulayliklar")
public class QulayliklarController {

    @Autowired
    private QulayliklarService qulaySer;

    @PreAuthorize(value = "hasRole('OWNER')")
    @PostMapping(value = "/createQulayliklar/{stadiumId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createQulayliklar(@PathVariable UUID stadiumId, @RequestBody QulayliklarDTOForRequest qulayDTO) {
        qulaySer.createQulayliklar(qulayDTO, stadiumId);
        return ResponseEntity.ok().build();
    }
}
