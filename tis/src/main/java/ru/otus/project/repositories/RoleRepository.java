package ru.otus.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}