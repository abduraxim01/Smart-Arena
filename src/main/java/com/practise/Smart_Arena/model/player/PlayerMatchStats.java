package com.practise.Smart_Arena.model.player;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMatchStats {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private int attended;

    private int canceled;

    private int didNotAttended;

    @OneToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference
    private Player player;
}
