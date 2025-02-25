package com.practise.Smart_Arena.model.player;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practise.Smart_Arena.model.owner.Polya;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private LocalDate day;

    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "booker_id")
    @JsonBackReference
    private Player player;

    @ManyToOne
    @JoinColumn(name = "polya_id")
    @JsonBackReference
    private Polya polya;

    // player va teamni qo'wiwni oýlab koriw
}
