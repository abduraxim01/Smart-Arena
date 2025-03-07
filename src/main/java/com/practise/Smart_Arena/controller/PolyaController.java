package com.practise.Smart_Arena.controller;

import com.practise.Smart_Arena.DTO.requestDTO.PolyaDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.PolyaDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.service.PolyaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/polya")
public class PolyaController {

    final private PolyaService polyaSer;

    @Autowired
    public PolyaController(PolyaService polyaSer) {
        this.polyaSer = polyaSer;
    }

    @PreAuthorize(value = "hasRole('OWNER')")
    @PostMapping(value = "/createPolya/{stadiumId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PolyaDTOForResponse> createPolya(@PathVariable UUID stadiumId, @RequestPart("polya") PolyaDTOForRequest polyaDTO,
                                                           @RequestPart("imageList") List<MultipartFile> images) {
        return ResponseEntity.ok(polyaSer.createPolya(polyaDTO, images, stadiumId));
    }
}
