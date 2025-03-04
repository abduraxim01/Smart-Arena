package com.practise.Smart_Arena.model.player;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteMessage {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID senderId;

    private UUID teamId;

    private UUID recipientId;

    private String teamName;

    private String message;

    @OneToOne(mappedBy = "inviteMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private ResponseMessage response;
}
