package com.example.UserCRUD.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor@AllArgsConstructor@Getter@Setter
public class LoginRequestdto {
    private String email;
    private String password;
}
