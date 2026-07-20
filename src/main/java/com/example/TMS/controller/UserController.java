package com.example.TMS.controller;

import com.example.TMS.dto.request.LoginRequestdto;
import com.example.TMS.dto.request.Update.UpdateUserRequestdto;
import com.example.TMS.dto.request.Create.CreateUserRequestdto;
import com.example.TMS.dto.response.LoginResponsedto;
import com.example.TMS.dto.response.responsedto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.TMS.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping

    public ResponseEntity<responsedto> createUser(@RequestBody CreateUserRequestdto request) {
        responsedto response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<responsedto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")public ResponseEntity<responsedto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestdto request
            ){
        responsedto updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")

    public ResponseEntity<LoginResponsedto> login(@RequestBody LoginRequestdto request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
