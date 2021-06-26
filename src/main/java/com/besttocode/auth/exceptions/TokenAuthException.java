package com.besttocode.auth.exceptions;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthException extends AuthenticationException {
    public TokenAuthException(String s) {
        super(s);
    }
}

