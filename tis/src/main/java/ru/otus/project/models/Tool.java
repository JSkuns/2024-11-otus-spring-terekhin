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
 * Инструменты
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tools")
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tool_type", nullable = false)
    private String type; // Тип инструмента (сверло, метчик, фреза...)

    @Column(name = "diameter", precision = 10, scale = 2)
    private BigDecimal diameter; // Диаметр рабочего элемента инструмента

    @Column(name = "length", precision = 10, scale = 2)
    private BigDecimal length; // Общая длина инструмента

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer; // Производитель инструмента

}