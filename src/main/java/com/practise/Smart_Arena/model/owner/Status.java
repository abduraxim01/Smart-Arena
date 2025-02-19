package com.practise.Smart_Arena.model.owner;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private LocalDate day;

    private LocalTime startTime;

    private LocalTime endTime;

    private int booker_id;

    @ManyToOne
    @JoinColumn(name = "polya_id")
    @JsonBackReference
    private Polya polya;
}
