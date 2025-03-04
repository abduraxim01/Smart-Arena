package com.practise.Smart_Arena.repository;

import com.practise.Smart_Arena.model.owner.Qulayliklar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QulayliklarRepository extends JpaRepository<Qulayliklar, UUID> {
}
