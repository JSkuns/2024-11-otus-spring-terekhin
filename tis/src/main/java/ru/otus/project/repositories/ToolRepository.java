package ru.otus.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.models.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {

}