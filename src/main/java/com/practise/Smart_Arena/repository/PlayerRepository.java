package com.practise.Smart_Arena.repository;

import com.practise.Smart_Arena.model.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    Player findByPhoneNumber(String phoneNumber);
}
