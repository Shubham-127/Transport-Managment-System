package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.UpdateRequestdto;
import com.example.UserCRUD.dto.request.createRequestdto;
import com.example.UserCRUD.dto.response.responsedto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.UserCRUD.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<responsedto> createUser(@RequestBody createRequestdto request) {
        responsedto response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<responsedto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PutMapping("/{id}")
    public ResponseEntity<responsedto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateRequestdto request
            ){
        responsedto updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
