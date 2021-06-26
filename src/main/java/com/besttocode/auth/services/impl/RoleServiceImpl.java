package com.besttocode.auth.services.impl;

import com.besttocode.auth.daos.RoleDAO;
import com.besttocode.auth.dtos.RoleDTO;
import com.besttocode.auth.models.RoleModel;
import com.besttocode.auth.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;
    private ModelMapper modelMapper;

    @Override
    public RoleDTO roleInfo(String id) {

        Optional<RoleModel> roleModelOptional = roleDAO.findById(id);

        if (roleModelOptional.isPresent()) {
            RoleModel roleModel = roleModelOptional.get();

            return modelMapper.map(roleModel, RoleDTO.class);
        }

        return null;
    }

    @Override
    public void createRole(RoleDTO roleDTO) {

        RoleModel roleModel = modelMapper.map(roleDTO, RoleModel.class);

        roleDAO.save(roleModel);

        modelMapper.map(roleModel, roleDTO);

    }
}
