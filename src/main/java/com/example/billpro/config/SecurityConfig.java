package com.example.billpro.config;

import com.example.billpro.security.CustomUserDetailsService;
import com.example.billpro.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Désactive CSRF (pas besoin avec JWT)
                .csrf(csrf -> csrf.disable())

                // On ne garde pas de session (stateless = mobile-friendly)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Règles d'accès
                .authorizeHttpRequests(auth -> auth
                        // Tout le monde peut accéder au login
                        .requestMatchers("/api/auth/**").permitAll()

                        // Optionnel : autoriser H2 console si tu l'utilises
                        .requestMatchers("/h2-console/**").permitAll()

                        // Tout le reste nécessite d'être authentifié
                        .anyRequest().authenticated()


                )

                // Active notre filtre JWT à chaque requête
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Pour H2 console (si tu l'utilises)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // Hashage des mots de passe (BCrypt = très sécurisé)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Permet à Spring de faire l'authentification
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}