package com.nallani.util;

import com.nallani.model.Transactions;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionUtil {

    public static void printInt(List<Transactions> transactions){
        List<Transactions> filteredTransactions = new ArrayList<>();

        transactions.forEach( trans -> {
            if (trans.getDescription().contains("Interest")){
                filteredTransactions.add(trans);
            }
        });
        // Print headers with consistent formatting
        System.out.printf("%-12s %-12s %-10s %-12s %-40s %-10s %-10s %-10s %-12s%n",
                "Activity", "Process", "Settle", "Instrument",
                "Description", "TransCode", "Quantity", "Price", "Amount");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");

        // Print filtered transactions
        filteredTransactions.forEach(System.out::println);
        // total is

        double totalAmount = filteredTransactions.stream()
                .mapToDouble(t -> {
                    String amountString = t.getAmount().replace("$", ""); // Remove the dollar sign
                    return Double.parseDouble(amountString); // Parse the cleaned string to double
                })
                .sum();

        List<String> tot = filteredTransactions.stream()
                .map(t -> {
                    // Remove the dollar sign
                    return t.getAmount().replace("$", ""); // Parse the cleaned string to double
                }).toList();
        //System.out.println(tot);
        // Print the total amount
        System.out.println();
        System.out.printf("%-108s %-7s %-7s %-10.2f%n", "Total Amount", "", "", totalAmount);

    }

    public static void printDeposits(List<Transactions> transactions){
        List<Transactions> filteredTransactions = new ArrayList<>();

        transactions.forEach( trans -> {
            if (trans.getDescription().contains("ACH Deposit")){
                filteredTransactions.add(trans);
            }
        });
        // Print headers with consistent formatting
        System.out.printf("%-12s %-12s %-10s %-12s %-40s %-10s %-10s %-10s %-12s%n",
                "Activity", "Process", "Settle", "Instrument",
                "Description", "TransCode", "Quantity", "Price", "Amount");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");

        // Print filtered transactions
        filteredTransactions.forEach(System.out::println);

        double totalAmount = filteredTransactions.stream()
                .mapToDouble(t -> {
                    String amountString = t.getAmount().replace("$", "").replace(",", ""); // Remove the dollar and comma sign
                    return Double.parseDouble(amountString); // Parse the cleaned string to double
                })
                .sum();
        // Print the total amount
        System.out.println();
        System.out.printf("%-108s %-7s %-7s %-10.2f%n", "Total Amount", "", "", totalAmount);

    }

    public static void groupByTransCode(List<Transactions> transactions){
        List<Transactions> filteredTransactions = new ArrayList<>();

        transactions.forEach( trans -> {
            if (StringUtils.isNoneBlank(trans.getAmount())){
                filteredTransactions.add(trans);
            }
        });
        // Print headers with consistent formatting
        System.out.printf("%-12s %-12s %-10s %-12s %-40s %-10s %-10s %-10s %-12s%n",
                "Activity", "Process", "Settle", "Instrument",
                "Description", "TransCode", "Quantity", "Price", "Amount");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

        // Grouping by transCode and summing amounts
        Map<String, Double> amountByTransCode = filteredTransactions.stream()
                .collect(Collectors.groupingBy(
                        Transactions::getTransCode,
                        Collectors.summingDouble(t -> {
                            String amountString = t.getAmount().replace("$", "")
                                    .replace(",", "")
                                    .replace("(", "")
                                    .replace(")", "");
                            return Double.parseDouble(amountString); // Parse the cleaned string to double
                        })
                ));

        // Sort the grouped results by transaction code
        List<Map.Entry<String, Double>> sortedEntries = amountByTransCode.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sort by transaction code
                // To sort by total amount, use this line instead:
                // .sorted(Map.Entry.comparingByValue()) // Sort by total amount
                .toList();

        sortedEntries.forEach(entry -> {
            String transCode = entry.getKey();
            double totalAmount = entry.getValue();
            long count = filteredTransactions.stream()
                    .filter(t -> t.getTransCode().equals(transCode))
                    .count();
            System.out.printf("%-95s %-10s %-10d %-6s $%-10.2f%n", "", transCode, count, "", totalAmount);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

        });

    }

    public static void printAll(List<Transactions> transactions){
        // Print headers with consistent formatting
        System.out.printf("%-12s %-12s %-10s %-12s %-40s %-10s %-10s %-10s %-12s%n",
                "Activity", "Process", "Settle", "Instrument",
                "Description", "TransCode", "Quantity", "Price", "Amount");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        // Print filtered transactions
        transactions.forEach(System.out::println);
    }

    public static void formatAndDisplayData(List<Transactions> transactions) {
        // Filter transactions by description containing the specified substring
        List<Transactions> filteredTransactions = transactions.stream()
                .sorted(Comparator.comparing(Transactions::getInstrument)) // Sort by Symbol
                .toList();

        // Print headers with consistent formatting
        System.out.printf("%-12s %-12s %-10s %-12s %-40s %-10s %-10s %-10s %-12s%n",
                "Activity", "Process", "Settle", "Instrument",
                "Description", "TransCode", "Quantity", "Price", "Amount");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");

        // Print filtered transactions
        filteredTransactions.forEach(System.out::println);
    }
}
