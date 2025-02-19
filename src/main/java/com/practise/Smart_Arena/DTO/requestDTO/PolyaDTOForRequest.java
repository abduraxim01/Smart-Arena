package com.practise.Smart_Arena.DTO.requestDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolyaDTOForRequest {

    private byte orderNumber;

    private String type;

    private byte size;

    private double prise;
}
