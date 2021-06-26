package com.besttocode.auth.services;

import com.besttocode.auth.exceptions.InvalidTokenException;
import com.besttocode.auth.models.UserModel;

public interface TokenService {
    void validateToken(String token) throws InvalidTokenException;

    void generateToken(UserModel markdownUserModel);
}
