package com.fincons.security;

import com.fincons.auth.CustomAuthenticationProvider;
import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;

import com.fincons.auth.CustomAuthenticationProvider;
import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    private UserDetailsService userDetailsService;

    private CustomAuthenticationProvider customAuthenticationProvider;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((authz) -> {
                    authz.requestMatchers(HttpMethod.DELETE, "/company-employee-management/v1/department/**").hasRole("USER");
                    authz.requestMatchers(HttpMethod.DELETE, "/company-employee-management/v1/employee/**").hasRole("USER");
                    authz.requestMatchers(HttpMethod.DELETE, "/company-employee-management/v1/position/**").hasRole("USER");
                    authz.requestMatchers(HttpMethod.DELETE, "/company-employee-management/v1/project/**").hasRole("USER");


                    authz.requestMatchers(HttpMethod.PUT, "/company-employee-management/v1/update-user").permitAll();


                    authz.requestMatchers("/company-employee-management/v1/department/**").hasRole("USER");
                    authz.requestMatchers("/company-employee-management/v1/employee/**").hasRole("USER");
                    authz.requestMatchers("/company-employee-management/v1/position/**").hasRole("USER");
                    authz.requestMatchers("/company-employee-management/v1/project/**").hasRole("USER");
                    authz.requestMatchers("/company-employee-management/v1/file/**").hasRole("USER");


                    authz.requestMatchers("/company-employee-management/v1/email").permitAll();
                    authz.requestMatchers("/company-employee-management/v1/session-value").permitAll();
                    authz.requestMatchers("/company-employee-management/v1/home").permitAll();
                    authz.requestMatchers("/company-employee-management/v1/register").permitAll();
                    authz.requestMatchers("/company-employee-management/v1/employees").authenticated();
                    authz.requestMatchers("/company-employee-management/v1/error").permitAll();
                    authz.requestMatchers("/company-employee-management/v1/registered-users").hasAnyRole("ADMIN","USER");

                    authz.requestMatchers("/company-employee-management/v1/login").permitAll();
                    authz.requestMatchers("/company-employee-management/v1/logout").permitAll().anyRequest().authenticated();


                }).httpBasic(Customizer.withDefaults());

        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  To knoww Why and what it is
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
