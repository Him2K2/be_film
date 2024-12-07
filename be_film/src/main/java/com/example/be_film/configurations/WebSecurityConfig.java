package com.example.be_film.configurations;

import com.example.be_film.filters.JwtTokenFilter;
import com.example.be_film.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class) // Thêm bộ lọc JWT

                .authorizeHttpRequests(requests -> requests
                        // Các endpoint công khai
                        .requestMatchers(
                                ("/api/v1/users/register"),
                                "/api/v1/users/login",
                                "/api/v1/films"
                        ).permitAll()

//                         Các endpoint yêu cầu quyền ADMIN với PUT và DELETE
                        .requestMatchers(PUT, "/api/v1/films/**").hasRole(Role.ADMIN)
                        .requestMatchers(DELETE, "/api/v1/films/**").hasRole(Role.ADMIN)
                        .requestMatchers(POST, "/api/v1/films").hasRole(Role.ADMIN)
                        .requestMatchers(POST, "/api/v1/Payment/create").hasRole(Role.USER)
                        .requestMatchers(DELETE, "/api/v1/films/**").hasRole(Role.ADMIN)
                        .requestMatchers(GET,"/api/v1/films/**").hasAnyRole(Role.ADMIN,Role.USER)
                        .requestMatchers(GET,"/api/v1/users/**").hasAnyRole(Role.ADMIN,Role.USER)



//                         Yêu cầu xác thực cho tất cả các endpoint khác
                        .anyRequest().authenticated()
                )
        ;

        return http.build();
    }
}
