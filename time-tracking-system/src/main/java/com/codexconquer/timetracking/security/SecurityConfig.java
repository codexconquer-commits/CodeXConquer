package com.codexconquer.timetracking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔴 VERY IMPORTANT
                .csrf(csrf -> csrf.disable())

                // ❌ disable default login mechanisms
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth

                        // ✅ allow static frontend
                        .requestMatchers(
                                "/",
                                "/login.html",
                                "/dashboard.html",
                                "/profile.html",
                                "/add-time.html",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // ✅ allow auth APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // 🔒 everything else needs JWT
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
