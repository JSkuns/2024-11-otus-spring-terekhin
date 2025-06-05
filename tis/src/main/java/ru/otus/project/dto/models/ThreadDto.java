package ru.otus.project.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThreadDto {

    private Long id;

    private BigDecimal threadDiameter; // Наружный диаметр резьбы

    private BigDecimal pitch;          // Шаг резьбы

    private String toleranceClass;     // Класс точности резьбы

}