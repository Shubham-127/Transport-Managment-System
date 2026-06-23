package com.example.UserCRUD.service;


import com.example.UserCRUD.dto.request.LoginRequestdto;
import com.example.UserCRUD.dto.request.Update.UpdateUserRequestdto;
import com.example.UserCRUD.dto.request.Create.CreateUserRequestdto;
import com.example.UserCRUD.dto.response.LoginResponsedto;
import com.example.UserCRUD.dto.response.responsedto;

import java.util.List;

public interface UserService {
    responsedto createUser (CreateUserRequestdto request);
    List<responsedto> getAllUsers();
    responsedto getUserById(Long id);
    responsedto updateUser (Long id, UpdateUserRequestdto request);
    LoginResponsedto login(LoginRequestdto request);

     void deleteUser(Long id);
}
