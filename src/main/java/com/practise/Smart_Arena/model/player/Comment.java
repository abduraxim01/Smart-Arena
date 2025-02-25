package com.practise.Smart_Arena.model.player;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practise.Smart_Arena.model.owner.Polya;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String body;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "polya_id")
    private Polya polya;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "player_id")
    private Player player;

    private float star;
}
