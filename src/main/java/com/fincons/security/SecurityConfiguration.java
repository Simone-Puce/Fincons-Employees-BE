package com.fincons.security;

import com.fincons.enums.RoleEndpoint;
import com.fincons.utility.ApplicationUri;
import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;

import com.fincons.utility.Endpoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class SecurityConfiguration {


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    private  JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private   JwtAuthenticationFilter jwtAuthFilter;


    @Value("${application.context}")
    private String appContext;

    @Value("${role.base.uri}")
    private String roleBaseUri;

    @Value("${department.base.uri}")
    private String departmentBaseUri;

    @Value("${employee.base.uri}")
    private String employeeBaseUri;

    @Value("${position.base.uri}")
    private String positionUri;

    @Value("${project.base.uri}")
    private String projectBaseUri;

    @Value("${modify.user}")
    private String modifyUser;

    @Value("${file.base.uri}")
    private String fileBaseUri;

    @Value("${email.sender.base.uri}")
    private String emailSenderUri;

    @Value("${update.user.password}")
    private String updateUserPassword;

    @Value("${registered.users}")
    private String registeredUsers;

    @Value("${login.base.uri}")
    private String loginBaseUri;

    @Value("${logout.base.uri}")
    private String logoutBaseUri;

    @Value("${error.base.uri}")
    private String errorBaseUri;

    @Value("${register.base.uri}")
    private String registerBaseUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);



        List<Endpoint> endpoints = Arrays.asList(

                new Endpoint(appContext + roleBaseUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + departmentBaseUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + positionUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + projectBaseUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + fileBaseUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + emailSenderUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + updateUserPassword + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + employeeBaseUri + "/**", Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER)),
                new Endpoint(appContext + registeredUsers, Arrays.asList(RoleEndpoint.ADMIN,RoleEndpoint.USER))


        );

        http.authorizeHttpRequests(authz -> {

            for (Endpoint e: endpoints) {
                if (e.getRoles().contains(RoleEndpoint.ADMIN) && e.getRoles().contains(RoleEndpoint.USER)) {
                    authz.requestMatchers(HttpMethod.GET, e.getPath()).hasAnyRole("ADMIN","USER");
                    authz.requestMatchers(e.getPath()).hasRole("ADMIN");
                }else if(e.getRoles().contains(RoleEndpoint.ADMIN) && e.getRoles().size() == 1){
                    authz.requestMatchers(e.getPath()).hasRole("ADMIN");
                } else if (e.getRoles().contains(RoleEndpoint.USER) && e.getRoles().size() == 1) {
                    authz.requestMatchers(e.getPath()).hasRole("USER");
                }
            }
            authz.requestMatchers(appContext + loginBaseUri).permitAll()
                    .requestMatchers(appContext + logoutBaseUri).permitAll()
                    .requestMatchers(appContext +registerBaseUri).permitAll()
                    .requestMatchers(appContext + errorBaseUri).permitAll()
                    .requestMatchers(appContext + modifyUser).authenticated()
                    .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
