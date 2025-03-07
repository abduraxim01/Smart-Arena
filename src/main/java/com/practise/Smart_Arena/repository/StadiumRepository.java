package com.practise.Smart_Arena.repository;

import com.practise.Smart_Arena.model.owner.Owner;
import com.practise.Smart_Arena.model.owner.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.UUID;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, UUID> {

    List<Stadium> findStadiumByOwner(Owner owner);
}
