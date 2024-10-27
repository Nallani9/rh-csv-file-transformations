package com.nallani.service;

import com.nallani.model.Transactions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RobinhoodCSVReader {
    public List<Transactions> readCSV() {
        var csvFile = Paths.get("data.csv").toString();

        try (var reader = new CSVReader(new FileReader(csvFile))) {
            var records = reader.readAll();
            if (!records.isEmpty()) {
                // Convert records to list of Transaction POJOs
                List<Transactions> transactions = records.subList(1, records.size()).stream()
                        .filter(row -> row.length >= 9)
                        .map(row -> new Transactions(
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4],
                                row[5],
                                row[6],
                                row[7],
                                row[8]
                        ))
                        .collect(Collectors.toList());
                return transactions;
            } else {
                System.out.println("No data found in CSV file.");
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
