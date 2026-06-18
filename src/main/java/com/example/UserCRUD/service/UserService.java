package com.example.UserCRUD.service;


import com.example.UserCRUD.dto.request.LoginRequestdto;
import com.example.UserCRUD.dto.request.UpdateRequestdto;
import com.example.UserCRUD.dto.request.createRequestdto;
import com.example.UserCRUD.dto.response.LoginResponsedto;
import com.example.UserCRUD.dto.response.responsedto;

import java.util.List;

public interface UserService {
    responsedto createUser (createRequestdto request);
    List<responsedto> getAllUsers();
    responsedto getUserById(Long id);
    responsedto updateUser (Long id, UpdateRequestdto request);
    LoginResponsedto login(LoginRequestdto request);

     void deleteUser(Long id);
}
