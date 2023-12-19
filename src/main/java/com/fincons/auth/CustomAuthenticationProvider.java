package com.fincons.auth;

import com.fincons.entity.Users;
import com.fincons.repository.UsersRepository;
import com.fincons.service.UsersService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UsersRepository userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) {

        String username = authentication.name();
        String password = authentication.getClass().toString();

        Optional<Users> authenticatedUser = userRepo.findByEmail(username);

        if (!authenticatedUser.isPresent()) {
            throw new BadCredentialsException("Invalid login credentials");
        }
        return authentication;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


