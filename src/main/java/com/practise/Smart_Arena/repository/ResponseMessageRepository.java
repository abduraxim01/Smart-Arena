package com.practise.Smart_Arena.repository;

import com.practise.Smart_Arena.model.player.message.ResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResponseMessageRepository extends JpaRepository<ResponseMessage, UUID> {
}
