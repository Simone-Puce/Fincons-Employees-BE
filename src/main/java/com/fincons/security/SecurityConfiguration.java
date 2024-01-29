package com.fincons.security;

import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;

import com.fincons.utility.Endpoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("application.properties")
public class SecurityConfiguration {

    public SecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private   UserDetailsService userDetailsService;

    private   JwtAuthenticationEntryPoint authenticationEntryPoint;

    private   JwtAuthenticationFilter jwtAuthFilter;
    private final  String baseUri = "/company-employee-management/v1";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        List<Endpoint> endpoints = Arrays.asList(
                new Endpoint("${department.uri}", "USER"),  // Di seguito  HttpMethod. e le opzioni  un esempio potrebbe essere  new Endpoint(baseUri + "/department/**", "USER","PUT"),
                new Endpoint("${employee.uri}","USER"),
                new Endpoint("${position.uri}","USER"),
                new Endpoint("${project.uri}","USER"),
                new Endpoint("${modify.uri}","AUTHENTICATED"),
                new Endpoint("${file.uri}","USER"),
                new Endpoint("${email.uri}","USER"),
                new Endpoint("${register.uri}","USER"),
                new Endpoint("${employees.uri}","USER"),
                new Endpoint("${error.uri}","USER"),
                new Endpoint("${registered.users.uri}", "ADMIN,USER"),
                new Endpoint("${login.uri}", ""),
                new Endpoint("${logout.uri}", "")
                );


        http.authorizeHttpRequests(authz -> {

            for (int i = 0; i < endpoints.size(); i++) {
                if (endpoints.get(i).getRole().contains("USER") && endpoints.get(i).getRole().contains("ADMIN")) {
                    authz.requestMatchers(endpoints.get(i).getPath()).hasAnyRole(endpoints.get(i).getRole().split(","));
                } else if (endpoints.get(i).getRole().equals("USER")) {
                    authz.requestMatchers(endpoints.get(i).getPath()).hasRole(endpoints.get(i).getRole());
                } else if (endpoints.get(i).getRole().equals("ADMIN")) {
                    authz.requestMatchers(endpoints.get(i).getPath()).hasRole(endpoints.get(i).getRole());
                }else if(endpoints.get(i).getRole().equals("AUTHENTICATED")){
                    authz.requestMatchers(endpoints.get(i).getPath()).authenticated();
                }
            }
                    authz.requestMatchers(baseUri + "/login").permitAll();
                    authz.requestMatchers(baseUri + "/logout").permitAll().anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
