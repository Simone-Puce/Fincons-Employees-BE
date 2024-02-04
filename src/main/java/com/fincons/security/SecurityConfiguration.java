package com.fincons.security;

import com.fincons.utility.ApplicationUri;
import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;

import com.fincons.utility.Endpoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private ApplicationUri applicationUri;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        List<Endpoint> endpoints = Arrays.asList(
                new Endpoint(applicationUri.getAppContext() + applicationUri.getUserUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getRoleUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getDepartmentUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getEmployeeUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getPositionUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getProjectUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getModifyUserUri(), "AUTHENTICATED"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getFileUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getEmailSenderUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getUpdateUserPasswordUri() + "/**", "AUTHENTICATED"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getEmployeesUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getRegisteredUsersUri(), "ADMIN")
        );

        http.authorizeHttpRequests(authz -> {

            for (Endpoint e: endpoints) {
                if (e.getRole().equals("ADMIN")) {
                    authz.requestMatchers(HttpMethod.GET, e.getPath()).hasAnyRole("ADMIN","USER");
                    authz.requestMatchers(e.getPath()).hasRole("ADMIN");
                }else if(e.getRole().equals("AUTHENTICATED")){
                    authz.requestMatchers(e.getPath()).authenticated();
                }
            }
            authz.requestMatchers(applicationUri.getAppContext() + applicationUri.getLoginUri()).permitAll()
                    .requestMatchers(applicationUri.getAppContext() + applicationUri.getLogoutUri()).permitAll()
                    .requestMatchers(applicationUri.getAppContext() + applicationUri.getRegisterUri()).permitAll()
                    .requestMatchers(applicationUri.getAppContext() + applicationUri.getErrorUri()).permitAll()
                    .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
