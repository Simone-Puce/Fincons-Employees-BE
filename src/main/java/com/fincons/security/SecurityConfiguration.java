package com.fincons.security;

import com.fincons.utility.ApplicationUri;
import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;

import com.fincons.utility.Endpoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private  JwtAuthenticationEntryPoint authenticationEntryPoint;


    private   JwtAuthenticationFilter jwtAuthFilter;

    private ApplicationUri aU;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        List<Endpoint> endpoints = Arrays.asList(
                new Endpoint( aU.getAPP_CONTEXT() + aU.getDEPARTMENT_URI() + "/**", "USER"),  // Di seguito  HttpMethod. e le opzioni  un esempio potrebbe essere  new Endpoint(baseUri + "/department/**", "USER","PUT")
                new Endpoint( aU.getAPP_CONTEXT() + aU.getEMPLOYEE_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getPOSITION_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getPROJECT_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getMODIFY_USER_URI() , "AUTHENTICATED"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getFILE_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getEMAIL_SENDER_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getUPDATE_USER_PASSWORD_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getEMPLOYEES_URI() + "/**", "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getERROR_URI(), "USER"),
                new Endpoint( aU.getAPP_CONTEXT() + aU.getREGISTERED_USERS_URI() , "ADMIN,USER")
                );

        http.authorizeHttpRequests(authz -> {

            for (Endpoint e: endpoints) {
                if (e.getRole().contains("USER") && e.getRole().contains("ADMIN")) {
                    authz.requestMatchers(e.getPath()).hasAnyRole(e.getRole().split(","));
                } else if (e.getRole().equals("USER")  ||  e.getRole().equals("ADMIN") ) {
                    authz.requestMatchers(e.getPath()).hasRole(e.getRole());
                }else if(e.getRole().equals("AUTHENTICATED")){
                    authz.requestMatchers(e.getPath()).authenticated();
                }
            }
                    authz.requestMatchers(aU.getAPP_CONTEXT() + aU.getLOGIN_URI()).permitAll()
                            .requestMatchers(aU.getAPP_CONTEXT() + aU.getLOGOUT_URI()).permitAll()
                            .requestMatchers(aU.getAPP_CONTEXT() + aU.getREGISTER_URI()).permitAll()
                            .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
