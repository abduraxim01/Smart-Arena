package com.practise.Smart_Arena.model.player;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practise.Smart_Arena.model.owner.Polya;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "booker_id")
    @JsonBackReference
    private Team team;

    @ManyToOne
    @JoinColumn(name = "polya_id")
    @JsonBackReference
    private Polya polya;
}
