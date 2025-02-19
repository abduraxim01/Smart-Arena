package com.practise.Smart_Arena.mapper;

import com.practise.Smart_Arena.DTO.requestDTO.QulayliklarDTOForRequest;
import com.practise.Smart_Arena.model.owner.Qulayliklar;
import com.practise.Smart_Arena.model.owner.Stadium;

public class QulayliklarMapper {

    public Qulayliklar toModel(QulayliklarDTOForRequest qulayDTO, Stadium stadium) {
        return Qulayliklar.builder()
                .bathroom(qulayDTO.isBathroom())
                .water(qulayDTO.isWater())
                .parking(qulayDTO.isParking())
                .lights(qulayDTO.isLights())
                .toilet(qulayDTO.isToilet())
                .nakedka(qulayDTO.isNakedka())
                .lockerRoom(qulayDTO.isLockerRoom())
                .umbrella(qulayDTO.isUmbrella())
                .stadium(stadium)
                .build();
    }
}
