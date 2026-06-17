package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.RoleRequestdto;
import com.example.UserCRUD.dto.response.RoleResponsedto;
import com.example.UserCRUD.dto.response.RoleResponsedto;
import com.example.UserCRUD.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // POST /api/roles — create a new role
    @PostMapping
    public ResponseEntity<RoleResponsedto> createRole(@RequestBody RoleRequestdto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));
    }

    // GET /api/roles — get all roles
    @GetMapping
    public ResponseEntity<List<RoleResponsedto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // GET /api/roles/{id} — get one role
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponsedto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // PUT /api/roles/{id} — update a role
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponsedto> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequestdto request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    // DELETE /api/roles/{id} — delete a role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/roles/assign?userId=1&roleId=2 — assign role to user
    @PostMapping("/assign")
    public ResponseEntity<String> assignRole(
            @RequestParam Long userId,
            @RequestParam Long roleId) {
        roleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role assigned to user successfully!");
    }

    // DELETE /api/roles/remove?userId=1&roleId=2 — remove role from user
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeRole(
            @RequestParam Long userId,
            @RequestParam Long roleId) {
        roleService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok("Role removed from user successfully!");
    }
}
