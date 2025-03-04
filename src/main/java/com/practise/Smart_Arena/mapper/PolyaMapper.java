package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.PolyaDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.PolyaDTOForResponse;
import com.practise.Smart_Arena.model.owner.Polya;
import com.practise.Smart_Arena.model.owner.Stadium;
import com.practise.Smart_Arena.model.owner.TypePolya;

import java.util.ArrayList;
import java.util.List;

public class PolyaMapper {

    final private CommentMapper commentMap = new CommentMapper();

    public Polya toModel(PolyaDTOForRequest polyaDTO, List<String> imagesUrlList, Stadium stadium) {
        return Polya.builder()
                .prise(polyaDTO.getPrise())
                .size(polyaDTO.getSize())
                .type(TypePolya.valueOf(polyaDTO.getType()))
                .orderNumber(polyaDTO.getOrderNumber())
                .imagesUrl(imagesUrlList)
                .stadium(stadium)
                .build();
    }

    public PolyaDTOForResponse toDTO(Polya polya) {
        return PolyaDTOForResponse.builder()
                .id(polya.getId())
                .size(polya.getSize())
                .commentList(commentMap.toDTO(polya.getCommentList()))
                .statusList(polya.getStatusList())
                .matchList(polya.getMatchList())
                .imagesUrl(polya.getImagesUrl())
                .orderNumber(polya.getOrderNumber())
                .stars(polya.getStars())
                .prise(polya.getPrise())
                .type(polya.getType().name())
                .build();
    }

    public List<PolyaDTOForResponse> toDTO(List<Polya> polyaList) {
        if (polyaList == null) return new ArrayList<>();
        return polyaList.stream()
                .map(this::toDTO)
                .toList();
    }
}
