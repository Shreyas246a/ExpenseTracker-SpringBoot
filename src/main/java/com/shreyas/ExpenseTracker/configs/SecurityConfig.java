package com.shreyas.ExpenseTracker.configs;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//public class SecurityConfig {
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable).
//        authorizeHttpRequests(req -> req.anyRequest().permitAll())
//                .formLogin(AbstractHttpConfigurer::disable)
//        .httpBasic(AbstractHttpConfigurer::disable);
//        return http.build();
//    }
//}
