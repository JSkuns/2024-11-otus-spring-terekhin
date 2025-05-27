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

import java.math.BigDecimal;

/**
 * Резьба
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "threads")
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thread_diameter", precision = 10, scale = 2)
    private BigDecimal threadDiameter; // Наружный диаметр резьбы

    @Column(name = "pitch", precision = 10, scale = 2)
    private BigDecimal pitch; // Шаг резьбы

    @Column(name = "tolerance_class", nullable = false)
    private String toleranceClass; // Класс точности резьбы

}