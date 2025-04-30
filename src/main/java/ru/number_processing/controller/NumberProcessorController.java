package ru.number_processing.controller;

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
public class NumberProcessorController {

    private final NumberProcessorService numberProcessorService;

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
