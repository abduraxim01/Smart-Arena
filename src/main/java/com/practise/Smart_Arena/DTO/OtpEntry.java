package com.practise.Smart_Arena.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpEntry {

    private String otp;

    private long expiryTime;
}
