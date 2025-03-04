package com.practise.Smart_Arena.repository;

import com.practise.Smart_Arena.model.player.InviteMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InviteMessageRepository extends JpaRepository<InviteMessage, UUID> {
    List<InviteMessage> findInviteMessageByRecipientId(UUID recipientId);
}
