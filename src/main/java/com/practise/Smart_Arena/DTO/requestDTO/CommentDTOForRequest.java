package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTOForRequest {

    private String body;

    private String polyaId;

    private float star;
}
