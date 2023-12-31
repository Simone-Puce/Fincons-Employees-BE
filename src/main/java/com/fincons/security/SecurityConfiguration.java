package com.fincons.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/company-employee-management/v1/email").permitAll()//
                        .requestMatchers("/company-employee-management/v1/session-value").permitAll()
                        .requestMatchers("/company-employee-management/v1/home").permitAll()
                        .requestMatchers("/company-employee-management/v1/register").permitAll()
                        .requestMatchers("/company-employee-management/v1/employees").authenticated() //working
                        .requestMatchers("/company-employee-management/v1/login").permitAll()
                        .requestMatchers("/company-employee-management/v1/logout").permitAll()
                        .requestMatchers("/company-employee-management/v1/error").permitAll()
                        .requestMatchers("/company-employee-management/v1/registered-users").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/company-employee-management/v1/admin/**").hasRole("ADMIN")
                );
        http
                .formLogin(form -> form
                        .loginPage("/company-employee-management/v1/login")
                        .loginProcessingUrl("/company-employee-management/v1/login")
                        .failureUrl("/company-employee-management/v1/error") //pagine di errore
                        .defaultSuccessUrl("/company-employee-management/v1/home").permitAll());
        http
                .logout(logout -> logout
                        .logoutUrl("/company-employee-management/v1/logout")
                        .logoutSuccessUrl("/company-employee-management/v1/home")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID").permitAll());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
