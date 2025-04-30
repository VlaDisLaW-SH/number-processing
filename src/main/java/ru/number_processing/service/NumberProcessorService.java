package ru.number_processing.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.number_processing.dto.CalculationResultDto;
import ru.number_processing.dto.InputDataDto;
import ru.number_processing.technical.exception.FieldsValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumberProcessorService {
    public CalculationResultDto findMinNumber(InputDataDto inputDataDto) throws IOException {
        var result = new CalculationResultDto();
        var numbersList = readIntegersFromExcelColumn(inputDataDto.getLocalPath());
        if (inputDataDto.getOrdinalNumber() > numbersList.size()) {
            throw new FieldsValidationException("Порядковый номер превышает количество чисел в файле. " +
                    "Введите корректное значение");
        }
        var desiredNumber = quickSort(numbersList).get(inputDataDto.getOrdinalNumber() - 1);
        result.setResult(desiredNumber);
        return result;
    }

    /**
     * Сортирует список целых чисел с использованием алгоритма быстрой сортировки.
     *
     * <p>Алгоритм быстрой сортировки (Quick Sort) - это эффективный алгоритм сортировки, который использует
     * стратегию "разделяй и властвуй". Он выбирает опорный элемент (в данном случае первый элемент списка)
     * и разделяет остальные элементы на две подгруппы: элементы, меньшие или равные опорному, и элементы,
     * большие опорного. Затем рекурсивно сортирует эти подгруппы.</p>
     *
     * @param numbers Список целых чисел, который необходимо отсортировать.
     *                Если список содержит менее двух элементов, он возвращается без изменений.
     * @return Отсортированный список целых чисел в порядке возрастания.
     *
     * @throws NullPointerException Если переданный список равен null.
     */
    private List<Integer> quickSort(List<Integer> numbers) {

        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        if (numbers.size() < 2) {
            return numbers;
        } else {
            Integer foothold = numbers.get(0);

            for (Integer number : numbers.subList(1, numbers.size())) {
                if (number > foothold) {
                    greater.add(number);
                } else {
                    less.add(number);
                }
            }
            result.addAll(quickSort(less));
            result.add(foothold);
            result.addAll(quickSort(greater));

            return result;
        }
    }

    /**
     * Читает целые числа из первого столбца XLSX-файла
     * @param filePath путь к файлу (например, "C:/data/numbers.xlsx")
     * @return список чисел
     * @throws IOException если возникла ошибка при чтении файла
     * @throws IllegalArgumentException если файл не содержит чисел или некорректен
     */
    private List<Integer> readIntegersFromExcelColumn(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Путь к файлу не может быть null или пустым");
        }

        var numbers = new ArrayList<Integer>();
        var file = new File(filePath);

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                if (cell == null) continue;

                switch (cell.getCellType()) {
                    case NUMERIC -> numbers.add((int) cell.getNumericCellValue());
                    case STRING -> {
                        try {
                            numbers.add(Integer.parseInt(cell.getStringCellValue().trim()));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException(
                                    "Ошибка в строке " + (row.getRowNum() + 1) + ": '" +
                                            cell.getStringCellValue() + "' не является целым числом"
                            );
                        }
                    }
                    default -> throw new IllegalArgumentException(
                            "Неподдерживаемый тип данных в строке " + (row.getRowNum() + 1)
                    );
                }
            }
        }

        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Файл не содержит чисел или пуст");
        }

        return numbers;
    }
}
