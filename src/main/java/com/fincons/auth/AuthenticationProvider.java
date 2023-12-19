package com.fincons.auth;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import javax.naming.AuthenticationException;

public interface AuthenticationProvider {
    Authentication authenticate(Authentication authentication)
            throws Exception;

    boolean supports(Class<?> authentication);
}
