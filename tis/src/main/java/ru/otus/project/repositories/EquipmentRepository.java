package ru.otus.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.models.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}