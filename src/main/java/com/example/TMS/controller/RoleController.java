package com.example.TMS.controller;

import com.example.TMS.dto.request.RoleRequestdto;
import com.example.TMS.dto.response.RoleResponsedto;
import com.example.TMS.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class    RoleController {

    private final RoleService roleService;

    // POST /api/roles — create a new role
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RoleResponsedto> createRole(@RequestBody RoleRequestdto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));
    }

    // GET /api/roles — get all roles
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RoleResponsedto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // GET /api/roles/{id} — get one role
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RoleResponsedto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // PUT /api/roles/{id} — update a role
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<RoleResponsedto> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequestdto request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    // DELETE /api/roles/{id} — delete a role
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/roles/assign?userId=1&roleId=2 — assign role to user
    @PostMapping("/assign")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<String> assignRole(
            @RequestParam Long userId,
            @RequestParam Long roleId) {
        roleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role assigned to user successfully!");
    }

    // DELETE /api/roles/remove?userId=1&roleId=2 — remove role from user
    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeRole(
            @RequestParam Long userId,
            @RequestParam Long roleId) {
        roleService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok("Role removed from user successfully!");
    }
}
