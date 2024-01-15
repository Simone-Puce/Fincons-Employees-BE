package com.fincons.auth;

import org.springframework.security.core.Authentication;

public interface AuthenticationProvider {
    Authentication authenticate(Authentication authentication)
            throws Exception;

    boolean supports(Class<?> authentication);
}
