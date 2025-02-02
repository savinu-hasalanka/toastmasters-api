package com.example.app.config;

import com.example.app.filter.JwtFilter;
import com.example.app.service.club.ClubDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final ClubDetailsService clubDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Inside securityFilterChain method : SecurityConfig");
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v2/users/register", "/api/v2/users/login", "/api/v2/clubs/register", "/api/v2/clubs/login").permitAll()
                        .requestMatchers("/api/v2/exco/add", "/api/v2/exco/remove").hasAuthority("ROLE_CLUB")
                        .requestMatchers("api/v2/followers/follow/*", "api/v2/followers/unfollow/*").hasAuthority("ROLE_USER")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Qualifier("userAuthenticationProvider")
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    @Qualifier("clubAuthenticationProvider")
    public AuthenticationProvider clubAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(clubDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    @Qualifier("userAuthenticationManager")
    @Primary
    public AuthenticationManager userAuthenticationManager(
            AuthenticationConfiguration authenticationConfiguration,
            @Qualifier("userAuthenticationProvider") AuthenticationProvider userAuthenticationProvider) {
        return new ProviderManager(Collections.singletonList(userAuthenticationProvider));
    }

    @Bean
    @Qualifier("clubAuthenticationManager")
    public AuthenticationManager clubAuthenticationManager(
            AuthenticationConfiguration authenticationConfiguration,
            @Qualifier("clubAuthenticationProvider") AuthenticationProvider clubAuthenticationProvider) {
        return new ProviderManager(Collections.singletonList(clubAuthenticationProvider));
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}
