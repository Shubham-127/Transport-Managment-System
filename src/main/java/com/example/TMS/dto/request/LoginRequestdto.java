package com.example.TMS.dto.request;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor@Getter@Setter@Builder
public class LoginRequestdto {
    private String email;
    private String password;
}
