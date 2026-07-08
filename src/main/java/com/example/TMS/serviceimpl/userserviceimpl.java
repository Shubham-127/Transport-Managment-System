package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.LoginRequestdto;
import com.example.TMS.dto.request.Update.UpdateUserRequestdto;
import com.example.TMS.dto.request.Create.CreateUserRequestdto;
import com.example.TMS.dto.response.LoginResponsedto;
import com.example.TMS.dto.response.RoleResponsedto;
import com.example.TMS.dto.response.responsedto;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import com.example.TMS.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.TMS.repository.userRepository;
import com.example.TMS.service.UserService;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class userserviceimpl implements UserService {
    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    public responsedto createUser(CreateUserRequestdto request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already in use:" + request.getEmail());

        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(user);

        return mapToresponsedto(savedUser);
    }

    @Override
    public List<responsedto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToresponsedto)
                .collect(Collectors.toList());
    }

    @Override
    public responsedto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" +id));
        return mapToresponsedto(user);
    }

    @Override
    public responsedto updateUser(Long id, UpdateUserRequestdto request) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id:" +id));
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);

        return mapToresponsedto(updatedUser);
    }

    @Override
    public LoginResponsedto login(LoginRequestdto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginResponsedto.builder()
                .Token(token)
                .email(user.getEmail())
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id:" +id));
        userRepository.delete(user);

    }
    private responsedto mapToresponsedto(User user){

        Set<RoleResponsedto> roleDTOs = user.getRoles()
                .stream()
                .map(role -> RoleResponsedto.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .description(role.getDescription())
                        .build())
                .collect(Collectors.toSet());

        return responsedto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
