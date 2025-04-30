package ru.number_processing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ru.number_processing.technical.validation.ValidLocalPath;

@Setter
@Getter
@Schema(description = "Входные данные для вычислений")
public class InputDataDto {
    /**
     * Путь к локальному файлу в формате xlsx
     */
    @Schema(description = "Путь к локальному файлу", example = "C:/data/numbers.xlsx")
    @ValidLocalPath
    private String localPath;

    /**
     * Порядковый номер
     */
    @Schema(description = "Порядковый номер")
    @Positive
    private Integer ordinalNumber;
}
