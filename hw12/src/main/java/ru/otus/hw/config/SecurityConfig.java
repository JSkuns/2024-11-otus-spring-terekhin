package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                        .requestMatchers("/").hasAnyRole("ADMIN", "USER", "GUEST")
                        // Логин
                        .requestMatchers("/login").anonymous()
                        // Книги
                        .requestMatchers("/books/delete").hasRole("ADMIN")
                        .requestMatchers("/books/create", "/books/update").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/books/find").hasAnyRole("ADMIN", "USER", "GUEST")
//                        // Авторы
//                        .requestMatchers("/authors/delete/*", "/author/create").hasRole("ADMIN")
//                        .requestMatchers("/author/all").hasAnyRole("ADMIN", "GUEST", "USER")
//                        .requestMatchers("/author/edit/*").hasAnyRole("ADMIN", "GUEST")
//                        // Жанры
//                        .requestMatchers("/genre/delete/*", "/genre/create").hasRole("ADMIN")
//                        .requestMatchers("/genre/all").hasAnyRole("ADMIN", "GUEST", "USER")
//                        .requestMatchers("/genre/edit/*").hasAnyRole("ADMIN", "GUEST")
//                        // Комментарии
//                        .requestMatchers("/comment/create/*").hasAnyRole("ADMIN", "USER", "GUEST")
//                        .requestMatchers("/comment/**").hasAnyRole("ADMIN", "GUEST")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .permitAll());
        return http.build();
    }

}
