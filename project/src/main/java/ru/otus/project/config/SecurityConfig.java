package ru.otus.project.config;

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
import ru.otus.project.security.CustomAccessDeniedHandler;

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
                        .requestMatchers(actuatorMatchers()).hasRole("ADMIN")
                        .requestMatchers(toolsMatchers()).hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(commentsMatchers()).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(publicMatchers()).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(setupLogin())
                .logout(setupLogout())
                .exceptionHandling(setupAccessDeniedHandler())
                .build();
    }

    private String[] publicMatchers() {
        return new String[]{
                "/",
                "/login",
                "/logout/**",
                "/locale/**"
        };
    }

    private String[] toolsMatchers() {
        return new String[]{
                "/tools/create/**",
                "/tools/update/**"
        };
    }

//    private String[] commentsMatchers() {
//        return new String[]{
//                "/comments/delete/**",
//                "/comments/update/**"
//        };
//    }

    private String[] actuatorMatchers() {
        return new String[]{
                "/actuator/**"
        };
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
