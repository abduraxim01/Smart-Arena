package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.StadiumDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.StadiumDTOForResponse;
import com.practise.Smart_Arena.model.owner.Owner;
import com.practise.Smart_Arena.model.owner.Stadium;
import org.springframework.data.geo.Point;

public class StadiumMapper {

    public Stadium toModel(StadiumDTOForRequest dto, Owner owner) {
        return Stadium.builder()
                .name(dto.getName())
                .rules(dto.getRules())
                .locationName(dto.getLocationName())
                .locationPoint(new Point(dto.getLocationPoint().getX(), dto.getLocationPoint().getY()))
                .workingDays(dto.getWorkingDays())
                .socialMediaAccounts(dto.getSocialMediaAccounts())
                .owner(owner)
                .workingHoursStart(dto.getWorkingHoursStart())
                .workingHoursEnd(dto.getWorkingHoursEnd())
                .build();
    }

    public StadiumDTOForResponse toDTO(Stadium stadium) {
        return StadiumDTOForResponse.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .rules(stadium.getRules())
                .locationName(stadium.getLocationName())
                .locationPoint(new Point(stadium.getLocationPoint().getX(), stadium.getLocationPoint().getY()))
                .workingDays(stadium.getWorkingDays())
                .socialMediaAccounts(stadium.getSocialMediaAccounts())
                .workingHoursStart(stadium.getWorkingHoursStart())
                .workingHoursEnd(stadium.getWorkingHoursEnd())
                // Qulayliklar va PolyaList qo'shilishi kerak
                .build();
    }
}
