package com.practise.Smart_Arena.model.owner;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Qulayliklar {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private boolean bathroom;

    private boolean toilet;

    private boolean parking;

    private boolean lockerRoom;

    private boolean water;

    private boolean umbrella;

    private boolean lights;

    private boolean nakedka;

    @OneToOne
    @JoinColumn(name = "stadium_id")
    @JsonBackReference
    private Stadium stadium;
}
