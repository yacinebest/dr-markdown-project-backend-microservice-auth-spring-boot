package com.besttocode.auth.daos;

import com.besttocode.auth.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByJwtToken(String jwtToken);
    List<UserModel> findByDisplayNameOrUsernameOrEmail(String username);
}

