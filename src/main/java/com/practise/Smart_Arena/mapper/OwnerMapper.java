package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.OwnerDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.OwnerDTOForResponse;
import com.practise.Smart_Arena.model.owner.Owner;
import com.practise.Smart_Arena.model.privileges.Role;

public class OwnerMapper {

    final private StadiumMapper stadiumMap = new StadiumMapper();

    public Owner toModel(OwnerDTOForRequest ownerDTO) {
        return Owner.builder()
                .name(ownerDTO.getName())
                .surname(ownerDTO.getSurname())
                .passport(ownerDTO.getPassport())
                .phoneNumber(ownerDTO.getPhoneNumber())
                .birthday(ownerDTO.getBirthday())
                .role(Role.OWNER)
                .build();
    }

    public OwnerDTOForResponse toDTO(Owner owner) {
        return OwnerDTOForResponse.builder()
                .id(owner.getId())
                .name(owner.getName())
                .surname(owner.getSurname())
                .birthday(owner.getBirthday())
                .passport(owner.getPassport())
                .phoneNumber(owner.getPhoneNumber())
                .stadiumList(stadiumMap.toDTO(owner.getStadiumList()))
                .build();
    }
}
