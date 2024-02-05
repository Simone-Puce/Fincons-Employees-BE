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

import java.util.ArrayList;
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

                new Endpoint(applicationUri.getAppContext() + applicationUri.getRoleBaseUri() + "/**","ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getDepartmentBaseUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getEmployeeBaseUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getPositionUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getProjectBaseUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getModifyUser(), "AUTHENTICATED"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getFileBaseUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getEmailSenderUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getUpdateUserPassword() + "/**", "AUTHENTICATED"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getEmployeesBaseUri() + "/**", "ADMIN"),
                new Endpoint(applicationUri.getAppContext() + applicationUri.getRegisteredUsers(), "ADMIN")
        );

        http.authorizeHttpRequests(authz -> {

            for (Endpoint e: endpoints) {
                if (e.getRoles().equals("ADMIN")) {
                    authz.requestMatchers(HttpMethod.GET, e.getPath()).hasAnyRole("ADMIN","USER");
                    authz.requestMatchers(e.getPath()).hasRole("ADMIN");
                }else if(e.getRoles().equals("AUTHENTICATED")){
                    authz.requestMatchers(e.getPath()).authenticated();
                }
            }
            authz.requestMatchers(applicationUri.getAppContext() + applicationUri.getLoginBaseUri()).permitAll()
                    .requestMatchers(applicationUri.getAppContext() + applicationUri.getLogoutBaseUri()).permitAll()
                    .requestMatchers(applicationUri.getAppContext() + applicationUri.getRegisterBaseUri()).permitAll()
                    .requestMatchers(applicationUri.getAppContext() + applicationUri.getErrorBaseUri()).permitAll()
                    .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
