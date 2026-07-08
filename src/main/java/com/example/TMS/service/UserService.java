package com.example.TMS.service;


import com.example.TMS.dto.request.LoginRequestdto;
import com.example.TMS.dto.request.Update.UpdateUserRequestdto;
import com.example.TMS.dto.request.Create.CreateUserRequestdto;
import com.example.TMS.dto.response.LoginResponsedto;
import com.example.TMS.dto.response.responsedto;

import java.util.List;

public interface UserService {
    responsedto createUser (CreateUserRequestdto request);
    List<responsedto> getAllUsers();
    responsedto getUserById(Long id);
    responsedto updateUser (Long id, UpdateUserRequestdto request);
    LoginResponsedto login(LoginRequestdto request);

     void deleteUser(Long id);
}
