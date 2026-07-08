package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.RoleRequestdto;
import com.example.TMS.dto.response.RoleResponsedto;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.Role;
import com.example.TMS.model.User;
import com.example.TMS.repository.RoleRepository;
import com.example.TMS.repository.userRepository;
import com.example.TMS.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final userRepository userRepostiory;

    @Override
    public RoleResponsedto createRole(RoleRequestdto request){
        if(roleRepository.existsByName(request.getName())){
            throw new RuntimeException("Role already exists:" + request.getName());

        }
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Role saved = roleRepository.save(role);
        return mapToresponsedto(saved);
    }

    @Override
    public List<RoleResponsedto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToresponsedto)
                .collect(Collectors.toList());

    }

    @Override
    public RoleResponsedto getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found with id:"+id));
        return mapToresponsedto(role);
    }

    @Override
    public RoleResponsedto updateRole(Long id, RoleRequestdto request) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found with id:" +id));
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Role updated = roleRepository.save(role);
        return mapToresponsedto(updated);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);

    }

    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        User user = userRepostiory.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id:" +userId));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found with id:" +roleId));
        user.getRoles().add(role);
        userRepostiory.save(user);
    }

    @Override
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepostiory.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id:" +userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));
        user.getRoles().remove(role);
        userRepostiory.save(user);
    }

    private RoleResponsedto mapToresponsedto(Role role) {
        return RoleResponsedto.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

}
