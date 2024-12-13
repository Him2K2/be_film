package com.example.be_film.configurations;

import com.example.be_film.filters.JwtTokenFilter;
import com.example.be_film.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter

                .authorizeHttpRequests(requests -> requests
                        // Public endpoints
                        .requestMatchers(
                                "/api/v1/users/register",
                                "/api/v1/users/login",
                                "/api/v1/films/**",
                                "/api/v1/films"
                        ).permitAll()

                        .requestMatchers(PUT, "/api/v1/films/**").hasRole(Role.ADMIN)
                        .requestMatchers(DELETE, "/api/v1/films/**").hasRole(Role.ADMIN)
                        .requestMatchers(POST, "/api/v1/films").hasRole(Role.ADMIN)
                        .requestMatchers(POST, "/api/v1/Payment/create").hasRole(Role.USER)
                        .requestMatchers(GET, "/api/v1/films/**").hasAnyRole(Role.ADMIN, Role.USER)
                        .requestMatchers(GET, "/api/v1/users/**").hasAnyRole(Role.ADMIN, Role.USER)
                        .requestMatchers(GET, "/api/v1/users/").hasRole(Role.ADMIN)
                        .requestMatchers(GET, "/api/v1/users/**").hasRole(Role.ADMIN)
                        .requestMatchers(GET, "/api/v1/users/**").hasRole(Role.ADMIN)
                        .requestMatchers(POST, "/api/v1/users/").hasRole(Role.ADMIN)
                        .requestMatchers(PUT, "/api/v1/users/**").hasRole(Role.ADMIN)



                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF properly

        // CORS configuration
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:3000"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });

        return http.build();
    }
}
