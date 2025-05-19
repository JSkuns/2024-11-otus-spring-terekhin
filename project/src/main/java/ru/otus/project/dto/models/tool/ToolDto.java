package ru.otus.project.dto.models.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolDto {

    private Long id;

    private String type; // Тип инструмента (сверло, метчик, фреза...)

    private BigDecimal diameter; // Диаметр рабочего элемента инструмента

    private BigDecimal length; // Общая длина инструмента

    private String manufacturer; // Производитель инструмента

}
