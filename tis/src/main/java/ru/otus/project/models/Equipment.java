package ru.otus.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Оборудование / Станок
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipments")
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipment_type", nullable = false)
    private String type; // Тип станка (токарный, фрезерный, сверлильный и др.)

    @Column(name = "model", nullable = false)
    private String model; // Модель станка

}