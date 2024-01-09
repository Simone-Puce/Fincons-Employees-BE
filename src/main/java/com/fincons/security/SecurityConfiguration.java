package com.fincons.security;

import com.fincons.jwt.JwtAuthenticationEntryPoint;
import com.fincons.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import com.fincons.auth.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

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

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;


    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((authz) -> authz
                        //Auth for App
                        .requestMatchers(HttpMethod.DELETE,"/company-employee-management/v1/department/**").hasRole("USER") // Will replace with admin when FE will be ready
                        .requestMatchers(HttpMethod.DELETE,"/company-employee-management/v1/employee/**").hasRole("USER") // Will replace with admin when FE will be ready
                        .requestMatchers(HttpMethod.DELETE,"/company-employee-management/v1/position/**").hasRole("USER") // Will replace with admin when FE will be ready
                        .requestMatchers(HttpMethod.DELETE,"/company-employee-management/v1/project/**").hasRole("USER")  // Will replace with admin when FE will be ready

                        .requestMatchers("/company-employee-management/v1/department/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/employee/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/position/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/project/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/certificate/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/certificate-employee/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/file/**").hasRole("USER")
                        .requestMatchers("/company-employee-management/v1/importfile").hasRole("USER")

                        //Auth for Login/Reg
                        .requestMatchers("/company-employee-management/v1/email").permitAll()
                        .requestMatchers("/company-employee-management/v1/email").permitAll()//
                        .requestMatchers("/company-employee-management/v1/session-value").permitAll()
                        .requestMatchers("/company-employee-management/v1/home").permitAll()
                        .requestMatchers("/company-employee-management/v1/register").permitAll()
                        .requestMatchers("/company-employee-management/v1/employees").hasRole("USER") //working
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

        http        //ADDED
                .addFilterBefore(        //ADDED
                        jwtAuthFilter,       //ADDED
                        UsernamePasswordAuthenticationFilter.class);         //ADDED
        return http.build();
    }
/*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
