package ru.otus.project.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EquipmentDto implements Serializable {

    private Long id;

    private String type;  // Тип станка (токарный, фрезерный, сверлильный и др.)

    private String model; // Модель станка

}