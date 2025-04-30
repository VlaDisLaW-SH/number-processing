package ru.number_processing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CalculationResultDto {
    /**
     * Результат вычислений
     */
    @Schema(description = "Результат вычислений")
    private Integer result;
}
