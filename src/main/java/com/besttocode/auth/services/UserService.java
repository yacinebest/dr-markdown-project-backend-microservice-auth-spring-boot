package com.besttocode.auth.services;

import com.besttocode.auth.dtos.UserInfoDTO;
import com.besttocode.auth.dtos.UserLoginDTO;

public interface UserService {
    void createUser(UserInfoDTO userInfoDTO);

    UserInfoDTO retrieveUserInfo(String id);

    UserInfoDTO loginUser(UserLoginDTO userLoginDTO);
}
