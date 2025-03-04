package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.model.player.InviteMessage;
import com.practise.Smart_Arena.repository.InviteMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    final private InviteMessageRepository invMessageRep;

    @Autowired
    public NotificationService(InviteMessageRepository invMessageRep) {
        this.invMessageRep = invMessageRep;
    }

    public List<InviteMessage> getNewNotifications(UUID playerID) {
        return invMessageRep.findInviteMessageByRecipientId(playerID)
                .stream()
                .filter(message -> message.getResponse() != null)
                .toList();
    }
}
