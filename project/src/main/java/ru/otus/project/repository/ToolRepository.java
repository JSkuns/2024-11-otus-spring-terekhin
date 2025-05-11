package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.entity.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {
}
