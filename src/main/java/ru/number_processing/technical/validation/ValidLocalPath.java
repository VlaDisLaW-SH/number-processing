package ru.number_processing.technical.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidLocalPathValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocalPath {
    String message() default "Неверный путь к локальному файлу";

    boolean checkExists() default true;
    boolean checkIsFile() default true;
    boolean checkReadable() default true;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
