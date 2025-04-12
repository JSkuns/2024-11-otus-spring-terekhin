package ru.otus.hw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    /**
     * Создаём бин PasswordEncoder для шифрования паролей пользователей перед сохранением в БД
     * Используем алгоритм шифрования BCrypt с уровнем сложности 10
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(
                                "/",
                                "/login",
                                "/logout/**").permitAll()

                        .requestMatchers(
                                "/books/create/**",
                                "/books/update/**").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(
                                "/comments/create/**",
                                "/comments/find_by_book_id/**",
                                "/comments/find/**").authenticated()

                        .requestMatchers(
                                "/comments/delete/**",
                                "/comments/update/**").hasAnyRole("ADMIN", "USER")

                        .anyRequest().authenticated()
                )
                .formLogin(setupLogin())
                .logout(setupLogout())
                .exceptionHandling(setupAccessDeniedHandler())
                .build();
    }

    @Bean
    public Customizer<FormLoginConfigurer<HttpSecurity>> setupLogin() {
        return formLoginConfigurer -> formLoginConfigurer
                .loginPage("/login")
                .usernameParameter("userName")
                .passwordParameter("password")
                .permitAll();
    }

    @Bean
    public Customizer<LogoutConfigurer<HttpSecurity>> setupLogout() {
        return logoutConfigurer -> logoutConfigurer
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    public Customizer<ExceptionHandlingConfigurer<HttpSecurity>> setupAccessDeniedHandler() {
        return exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

}
