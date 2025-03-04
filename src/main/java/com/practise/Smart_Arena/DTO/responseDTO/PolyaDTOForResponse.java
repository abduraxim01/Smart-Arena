package com.practise.Smart_Arena.DTO.responseDTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practise.Smart_Arena.model.owner.Stadium;
import com.practise.Smart_Arena.model.owner.Status;
import com.practise.Smart_Arena.model.owner.TypePolya;
import com.practise.Smart_Arena.model.player.Comment;
import com.practise.Smart_Arena.model.player.Match;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolyaDTOForResponse {

    private UUID id;

    private byte orderNumber;

    private String type;

    private byte size;

    private double prise;

    private double stars;

    private List<String> imagesUrl;

    private List<Status> statusList;

    private List<CommentDTOForResponse> commentList;

    private List<Match> matchList;
}
