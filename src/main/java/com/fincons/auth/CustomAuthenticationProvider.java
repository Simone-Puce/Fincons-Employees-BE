package com.fincons.auth;

import com.fincons.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Override
    public Authentication authenticate (Authentication authentication) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("Credentials are not correct");
        }
    }
    @Override
    public boolean supports (Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
