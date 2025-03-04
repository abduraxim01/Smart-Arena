package com.practise.Smart_Arena.model.owner;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practise.Smart_Arena.model.player.Comment;
import com.practise.Smart_Arena.model.player.Match;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Polya {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private byte orderNumber;

    private TypePolya type;

    private byte size;

    private double prise;

    private List<String> imagesUrl;

    private double stars;

    @OneToMany(mappedBy = "polya", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Status> statusList;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    @JsonBackReference
    private Stadium stadium;

    @OneToMany(mappedBy = "polya", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> commentList;

    @OneToMany(mappedBy = "polya", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Match> matchList;
}
