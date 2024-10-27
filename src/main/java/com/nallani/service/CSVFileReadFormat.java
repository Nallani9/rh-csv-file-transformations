package com.nallani.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class CSVFileReadFormat {

    public void read() {
        var csvFile = Paths.get("data.csv").toString();

        try (var reader = new CSVReader(new FileReader(csvFile))) {
            var records = reader.readAll();
            if (!records.isEmpty()) formatAndDisplayData(records);
            else System.out.println("No data found in CSV file.");
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public void formatAndDisplayData(List<String[]> records) {
        var headers = records.getFirst();
        var dataRows = records.subList(1, records.size());
        var columnWidths = calculateColumnWidths(headers, dataRows);

        printRow(headers, columnWidths);
        printSeparator(columnWidths);
        dataRows.forEach(row -> printRow(row, columnWidths));
    }

    private static int[] calculateColumnWidths(String[] headers, List<String[]> rows) {
        return IntStream.range(0, headers.length)
                .map(i -> Math.max(
                        headers[i].length(),
                        rows.stream()
                                .filter(row -> row.length > i)  // Ensure the row has enough columns
                                .mapToInt(row -> row[i].length())
                                .max()
                                .orElse(0)))
                .toArray();
    }

    private static void printRow(String[] row, int[] widths) {
        IntStream.range(0, row.length)
                .forEach(i -> {
                    // Replace newline characters with spaces in each cell
                    var content = row[i] != null ? row[i].replace("\n", " ") : "";
                    System.out.printf("%-" + (widths[i] + 2) + "s", content);
                });
        System.out.println();
    }

    private static void printSeparator(int[] widths) {
        IntStream.of(widths).forEach(w -> System.out.print("-".repeat(w + 2)));
        System.out.println();
    }
}