package com.besttocode.auth.services.impl;

import com.besttocode.auth.daos.RoleDAO;
import com.besttocode.auth.daos.UserDAO;
import com.besttocode.auth.dtos.UserInfoDTO;
import com.besttocode.auth.dtos.UserLoginDTO;
import com.besttocode.auth.models.RoleModel;
import com.besttocode.auth.models.UserModel;
import com.besttocode.auth.services.TokenService;
import com.besttocode.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserInfoDTO userInfoDTO) {

        UserModel userModel = modelMapper.map(userInfoDTO, UserModel.class);

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));


        userModel.setRoles(
                roleDAO.findAll().stream()
                        .map(RoleModel::getRole)
                        .filter(role -> role.contains("USER"))
                        .collect(Collectors.toList())
        );

        tokenService.generateToken(userModel);

        userDAO.save(userModel);

        userInfoDTO.setPassword("");

        modelMapper.map(userModel, userInfoDTO);
    }

    @Override
    public UserInfoDTO retrieveUserInfo(String id) {
        Optional<UserModel> userModelOptional = userDAO.findById(id);
        if (userModelOptional.isPresent()) {
            return modelMapper.map(userModelOptional.get(), UserInfoDTO.class);
        }
        return null;
    }

    @Override
    public UserInfoDTO loginUser(UserLoginDTO userLoginDTO) {
        Optional<UserModel> optionalMarkdownUserModel = userDAO.findByUsername(userLoginDTO.getUsername());
        if (optionalMarkdownUserModel.isPresent()) {
            UserModel userModel = optionalMarkdownUserModel.get();

            if (passwordEncoder.matches(userLoginDTO.getPassword(), userModel.getPassword())) {

                return modelMapper.map(userModel, UserInfoDTO.class);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }

        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
