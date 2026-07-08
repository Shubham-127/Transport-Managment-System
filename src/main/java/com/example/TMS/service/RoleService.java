package com.example.TMS.service;


import com.example.TMS.dto.request.RoleRequestdto;
import com.example.TMS.dto.response.RoleResponsedto;

import java.util.List;

public interface RoleService {
    RoleResponsedto createRole(RoleRequestdto request);

    List<RoleResponsedto> getAllRoles();

    RoleResponsedto getRoleById(Long id);

    RoleResponsedto updateRole(Long id, RoleRequestdto request);

    void deleteRole(Long id);

    void assignRoleToUser(Long userId, Long roleId);

    void removeRoleFromUser(Long userId, Long roleId);


}
