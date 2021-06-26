package com.besttocode.auth.controller;

import com.besttocode.auth.services.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/token")
public class TokenController {

    private TokenService tokenService;

    @GetMapping("/validate")
    @PreAuthorize("hasAnyRole('ANONYMOUS','USER','ADMIN')")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception {


        String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String token = null;
        if (!isEmpty(authHeader)) {
            token = authHeader.split("\\s")[1];

        }

        tokenService.validateToken(token);
    }
}

