package com.example.UserCRUD.dto.response;

import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class LoginResponsedto {
    private String Token;
    private String email;
}
