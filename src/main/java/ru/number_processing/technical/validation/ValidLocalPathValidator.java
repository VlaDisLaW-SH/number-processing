package ru.number_processing.technical.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.number_processing.technical.exception.FieldsValidationException;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ValidLocalPathValidator implements ConstraintValidator<ValidLocalPath, String> {

    private boolean checkExists;
    private boolean checkIsFile;
    private boolean checkReadable;

    @Override
    public void initialize(ValidLocalPath constraintAnnotation) {
        this.checkExists = true;
        this.checkIsFile = true;
        this.checkReadable = true;
    }

    @Override
    public boolean isValid(String path, ConstraintValidatorContext context) {
        if (path == null || path.trim().isEmpty()) {
            throw new FieldsValidationException("Путь к файлу не может быть null или пустым");
        }

        try {
            Path filePath = Paths.get(path);

            if (checkExists && !Files.exists(filePath)) {
                context.buildConstraintViolationWithTemplate("Файл не существует")
                        .addConstraintViolation();
                return false;
            }

            if (checkIsFile && !Files.isRegularFile(filePath)) {
                context.buildConstraintViolationWithTemplate("Путь не ведёт к файлу")
                        .addConstraintViolation();
                return false;
            }

            if (checkReadable && !Files.isReadable(filePath)) {
                context.buildConstraintViolationWithTemplate("Файл не доступен для чтения")
                        .addConstraintViolation();
                return false;
            }
            return true;

        } catch (InvalidPathException e) {
            context.buildConstraintViolationWithTemplate("Неверный формат пути: " + e.getMessage())
                    .addConstraintViolation();
            return false;
        } catch (SecurityException e) {
            context.buildConstraintViolationWithTemplate("Доступ запрещен: " + e.getMessage())
                    .addConstraintViolation();
            return false;
        }
    }
}
