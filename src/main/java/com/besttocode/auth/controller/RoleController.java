package com.besttocode.auth.controller;

import com.besttocode.auth.dtos.RoleDTO;
import com.besttocode.auth.services.RoleService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {

    private RoleService roleService;

    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDto) {

        Preconditions.checkNotNull(roleDto);

        roleService.createRole(roleDto);
        return roleDto;
    }

    @GetMapping("/info/{id}")
    public RoleDTO getRoleInfo(@PathVariable String id) {
        return roleService.roleInfo(id);
    }


//
//    @DeleteMapping("/{id}")
//    public void deleteRole(@PathVariable String id) {
//
//        log.info("deleting role by id: " + id);
//    }
//
//    @PutMapping("/{id}")
//    public RoleDTO updateRole(@PathVariable String id, @RequestBody RoleDTO roleDTO) {
//
//        log.info("updating user with id: " + id);
//        return null;
//    }
}

