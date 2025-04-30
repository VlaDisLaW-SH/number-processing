package ru.number_processing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.number_processing.dto.CalculationResultDto;
import ru.number_processing.dto.InputDataDto;
import ru.number_processing.service.NumberProcessorService;
import ru.number_processing.technical.exception.CustomValidationException;

import java.io.IOException;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
@Tag(name = "Обработка чисел", description = "Выполнение операций над множеством чисел")
public class NumberProcessorController {

    private final NumberProcessorService numberProcessorService;

    @Operation(
            summary = "Поиск минимального значения",
            description = "Производит поиск минимального значения с указанием порядкового номера"
    )
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CalculationResultDto> calculate(
            @Valid @RequestBody InputDataDto inputDataDto,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult);
        }
        var result = numberProcessorService.findMinNumber(inputDataDto);
        return ResponseEntity.ok(result);
    }
}
