package com.fincons.security;

import com.fincons.utility.Classpath;
import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;

import com.fincons.utility.Endpoint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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

import java.util.Arrays;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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

    private   UserDetailsService userDetailsService;

    private   JwtAuthenticationEntryPoint authenticationEntryPoint;


    private   JwtAuthenticationFilter jwtAuthFilter;

    private Classpath cp;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Classpath cp = new Classpath();


        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        List<Endpoint> endpoints = Arrays.asList(
//                new Endpoint( "".concat(String.valueOf(EnumUri.APP_CONTEXT)) + "".concat(String.valueOf(EnumUri.DEPARTMENT_URI)) + "/**", "USER"),  // Di seguito  HttpMethod. e le opzioni  un esempio potrebbe essere  new Endpoint(baseUri + "/department/**", "USER","PUT")
//                new Endpoint( String.valueOf(EnumUri.APP_CONTEXT) + (EnumUri.EMPLOYEE_URI) + "/**", "USER"),
//               // new Endpoint( String.valueOf().APP_CONTEXT +  (EnumUri.POSITION_URI) + "/**", "USER"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.PROJECT_URI) + "/**", "USER"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.MODIFY_USER) + "/**", "AUTHENTICATED"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.FILE_URI) + "/**", "USER"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.EMAIL_SENDER_URI) + "/**", "USER"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.EMPLOYEES_URI) + "/**", "USER"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.ERROR_URI) + "/**", "USER"),
//                new Endpoint( EnumUri.APP_CONTEXT +  String.valueOf(EnumUri.REGISTERED_USERS_URI) + "/**", "ADMIN,USER")
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
                    authz.requestMatchers(cp.getAPP_CONTEXT() + cp.getLOGIN_URI()).permitAll();
                    //authz.requestMatchers(classpath.getAPP_CONTEXT() + classpath.getLOGOUT_URI()).permitAll();
                    authz.requestMatchers(cp.getAPP_CONTEXT() + cp.getREGISTER_URI()).permitAll().anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
