package com.proyecto.neo.app.movimientos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Desactiva CSRF para simplificar pruebas
        .authorizeHttpRequests(requests -> requests
                .anyRequest().permitAll())  // Permite todas las solicitudes
        .formLogin(login -> login.disable())  // Desactiva el formulario de inicio de sesión
        .httpBasic(basic -> basic.disable());  // Desactiva la autenticación básica

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
