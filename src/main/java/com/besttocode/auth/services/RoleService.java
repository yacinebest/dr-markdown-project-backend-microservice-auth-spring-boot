package com.besttocode.auth.services;

import com.besttocode.auth.dtos.RoleDTO;

public interface RoleService {
    void createRole(RoleDTO roleDto);
    RoleDTO roleInfo(String id);
}
