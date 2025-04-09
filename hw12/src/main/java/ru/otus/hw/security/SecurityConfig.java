package ru.otus.hw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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
        http
//                .csrf(AbstractHttpConfigurer::disable) // отключить защиту от CSRF
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                                //
                                .requestMatchers("/", "/login").permitAll()
                                // Книги
                                .requestMatchers("/books/delete/**").hasRole("ADMIN")
                                .requestMatchers("/books/create/**",
                                        "/books/update/**").hasAnyRole("ADMIN", "USER")
                                // Авторы и жанры доступны всем авторизованным
                                // Комментарии
                                .requestMatchers("/comments/create/**",
                                        "/comments/find_by_book_id/**",
                                        "/comments/find/**").authenticated()
                                .requestMatchers("/comments/delete/**",
                                        "/comments/update/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .permitAll()
                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessUrl("/")
//                        .permitAll())
                .exceptionHandling(configurer -> configurer.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

}
