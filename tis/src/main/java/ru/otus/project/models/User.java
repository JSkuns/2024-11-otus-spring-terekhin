package ru.otus.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Пользователь
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = {"roles"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, unique = false)
    private String password;

    /**
     * Множество ролей пользователя
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Название промежуточной таблицы
            joinColumns = {@JoinColumn(name = "user_id")}, // Связь с колонкой user_id
            inverseJoinColumns = {@JoinColumn(name = "role_id")} // Связь с колонкой role_id
    )
    private Set<Role> roles = new HashSet<>(); // Использование Set для уникальности ролей

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public boolean hasRole(String roleName) {
        return this.roles.stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
    }

}
