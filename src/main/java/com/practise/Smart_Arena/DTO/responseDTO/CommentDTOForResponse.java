package com.practise.Smart_Arena.DTO.responseDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTOForResponse {

    private UUID id;

    private String body;

    private float star;
}
