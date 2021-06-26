package com.besttocode.auth.config.security;

import com.besttocode.auth.daos.UserDAO;
import com.besttocode.auth.exceptions.InvalidTokenException;
import com.besttocode.auth.exceptions.TokenAuthException;
import com.besttocode.auth.models.UserModel;
import com.besttocode.auth.services.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
public class CustomAuthProvider extends  AbstractUserDetailsAuthenticationProvider {

    private UserDAO userDAO;
    private TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final String token = (String) authentication.getCredentials();
        if (isEmpty(token)) {
            return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }

        Optional<UserModel> optionalUserModel = userDAO.findByJwtToken(token);

        if (optionalUserModel.isPresent()) {
            UserModel userModel = optionalUserModel.get();

            try {
                tokenService.validateToken(token);
            } catch (InvalidTokenException e) {
                userModel.setJwtToken(null);
                userDAO.save(userModel);

                return null;
            }
            return new User(username, "",
                    AuthorityUtils.createAuthorityList(
                            userModel.getRoles().stream()
                                    .map(roleName -> "ROLE_" + roleName)
                                    .toArray(String[]::new)
                    ));
        }

        throw new TokenAuthException("User not found for token: " + token);
    }
}
