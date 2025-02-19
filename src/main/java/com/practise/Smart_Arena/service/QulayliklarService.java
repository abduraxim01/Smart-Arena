package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.DTO.requestDTO.QulayliklarDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.mapper.QulayliklarMapper;
import com.practise.Smart_Arena.model.owner.Stadium;
import com.practise.Smart_Arena.repository.QulayliklarRepository;
import com.practise.Smart_Arena.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QulayliklarService {

    @Autowired
    private QulayliklarRepository qulayRep;

    @Autowired
    private StadiumRepository stadiumRep;

    final private QulayliklarMapper qulayMap =new QulayliklarMapper();

    public void createQulayliklar(QulayliklarDTOForRequest qulayDTO, UUID stadiumId) {
        Stadium stadium = stadiumRep.findById(stadiumId).orElseThrow(()->new AllExceptions.EntityNotFoundException("Stadium not found"));
        qulayRep.save(qulayMap.toModel(qulayDTO,stadium));
    }
}
