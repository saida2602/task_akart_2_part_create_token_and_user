package com.saida.akart_task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/v1/api/createToken").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/api/getUser/").authenticated()
                .anyRequest().permitAll());
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.disable());
        http.logout(logout -> logout.disable());
        http.formLogin(formLogin -> formLogin.disable());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

}