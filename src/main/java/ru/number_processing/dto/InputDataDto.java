package ru.number_processing.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ru.number_processing.technical.validation.ValidLocalPath;

@Setter
@Getter
public class InputDataDto {
    /**
     * Путь к локальному файлу в формате xlsx
     */
    @ValidLocalPath
    private String localPath;

    /**
     * Порядковый номер
     */
    @Positive
    private Integer ordinalNumber;
}
