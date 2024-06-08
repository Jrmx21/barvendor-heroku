package com.davidruiz.barvendor.Security;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection if needed
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() // Permitir acceso a
                                                                                                  // estas rutas
                        .requestMatchers("/api/**").permitAll() // Permitir acceso a todas las APIs
                        .requestMatchers("/kitchen/**").hasRole("Cocina") // Permitir acceso a /kitchen/orders solo a
                                                                          // Cocina
                        .requestMatchers("/bartender/**").hasRole("Camarero") // Permitir acceso a /bartender/** solo a
                                                                              // Camarero
                        .anyRequest().hasRole("Admin") // Restringir acceso a cualquier otra solicitud solo para
                                                       // administradores
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // URL por defecto después del login
                        .successHandler((request, response, authentication) -> {
                            // Redirigir según el rol del usuario
                            authentication.getAuthorities().forEach(grantedAuthority -> {
                                try {
                                    if (grantedAuthority.getAuthority().equals("ROLE_Cocina")) {
                                        response.sendRedirect("/kitchen/orders");
                                    } else if (grantedAuthority.getAuthority().equals("ROLE_Camarero")) {
                                        response.sendRedirect("/bartender");
                                    } else if (grantedAuthority.getAuthority().equals("ROLE_Admin")) {
                                        response.sendRedirect("/");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
