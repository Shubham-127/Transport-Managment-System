package com.example.UserCRUD.dto.request.Create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class CreateUserRequestdto {

    private String name;
    private String email;

    private String password;
}
