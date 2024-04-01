package com.example.StudentToDo.service.convert;

import com.example.StudentToDo.aggregation.entity.Student;
import org.dhatim.fastexcel.BorderSide;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class DataToExcel {
    public Map<Integer, List<String>> readExcel(String fileLocation) throws IOException {
        Map<Integer, List<String>> data = new HashMap<>();

        try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(r -> {
                    data.put(r.getRowNum(), new ArrayList<>());
                    for (Cell cell : r) {
                        data.get(r.getRowNum()).add(cell.getRawValue());
                    }
                });
            }
        }
        return data;
    }

    @Bean
    public byte[] createExcelFile(List<Student> studentList) throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "studentInfo.xlsx";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (Workbook wb = new Workbook(baos, "MyApplication", "1.0")) {
                Worksheet ws = wb.newWorksheet("Sheet 1");
                ws.width(0, 5);
                ws.width(1, 25);
                ws.width(2, 25);
                ws.width(3, 25);

                // Заголовки таблицы
                ws.range(0, 0, 0, 3).style().verticalAlignment("center").fontName("Arial").fontSize(16).bold().fillColor("3366FF").horizontalAlignment("center").set();
                ws.value(0, 0, "№");
                ws.value(0, 1, "First name");
                ws.value(0, 2, "Middle name");
                ws.value(0, 3, "Last name");

                // Заполнение данными
                for (int i = 0; i < studentList.size(); i++) {
                    ws.range(i+1, 0, 0, 3).style().horizontalAlignment("center").verticalAlignment("center").borderColor(BorderSide.DIAGONAL,"black").set();
                    ws.value(i + 1, 0, i+1);
                    ws.value(i + 1, 1, studentList.get(i).getFirstName());
                    ws.value(i + 1, 2, studentList.get(i).getMiddleName());
                    ws.value(i + 1, 3, studentList.get(i).getLastName());
                }
            }
            // Получение байтового массива после заполнения данных
            return baos.toByteArray();
        }
    }
}
