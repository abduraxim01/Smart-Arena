package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.PolyaDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.PolyaDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.PolyaMapper;
import com.practise.Smart_Arena.model.owner.Stadium;
import com.practise.Smart_Arena.repository.PolyaRepository;
import com.practise.Smart_Arena.repository.StadiumRepository;
import com.practise.Smart_Arena.service.imageService.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class PolyaService {

    @Autowired
    private PolyaRepository polyaRep;

    @Autowired
    private StadiumRepository stadiumRep;

    @Autowired
    private ImageService imageSer;

    final private PolyaMapper polyaMap = new PolyaMapper();

    public PolyaDTOForResponse createPolya(PolyaDTOForRequest polyaDTO, List<MultipartFile> images, UUID stadiumId) {
        Stadium stadium = stadiumRep.findById(stadiumId).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Stadium not found"));
        List<String> imageUrlList = imageSer.saveImages(images);
        return polyaMap.toDTO(polyaRep.save(polyaMap.toModel(polyaDTO, imageUrlList, stadium)));
    }
}
