package com.example.UserCRUD.dto.request;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor@Getter@Setter@Builder
public class LoginRequestdto {
    private String email;
    private String password;
}
