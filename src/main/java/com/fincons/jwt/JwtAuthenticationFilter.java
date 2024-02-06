package com.fincons.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    /*
    this filter is responsible for extracting and validating a JWT token from a request,
    and if the token is valid, it authenticates the corresponding user and sets the
    authentication object in the security context.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get JWT token from HTTP Request
        String token = getTokenFromRequest(request);

        // Validate Token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // get email from token
            String email = jwtTokenProvider.getEmailFromJWT(token);

            // take user from load by username (email)
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // create an authentication object
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // credential null couse userDetails already contains credential thanks userDetailsService.loadUserByUsername(email);
                    userDetails.getAuthorities());

            // set details of authentication
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // set authentication in context of security and thanks to this spring know user is  authenticated for all period or request
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization"); //Header contains key-value  key = Authorization and value = Bearer token

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ){ // If string is not null and if string starts with "Bearer " <- space is fundamental
            return bearerToken.substring(7, bearerToken.length()); // substring indicates from with index we must start to extract string
        }

        return null;


    }
}
