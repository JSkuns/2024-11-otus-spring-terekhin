package ru.otus.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.models.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

}