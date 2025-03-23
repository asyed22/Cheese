package com.csc;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CheeseAnalyzer {

    public boolean checkQuality(String cheeseData) {
        return cheeseData.toLowerCase().contains("high quality");
    }

    public boolean checkAging(String cheeseData) {
        return cheeseData.toLowerCase().contains("aged");
    }

    public boolean checkFlavor(String cheeseData) {
        return cheeseData.toLowerCase().contains("rich");
    }

    public boolean checkTexture(String cheeseData) {
        return cheeseData.toLowerCase().contains("smooth");
    }

    public boolean checkColor(String cheeseData) {
        return cheeseData.toLowerCase().contains("golden");
    }
    
    public boolean checkSmell(String cheeseData) {
        return cheeseData.toLowerCase().contains("pleasant");
    }
    public static void main(String[] args) {
        String inputFile = "cheese_data.csv";
        String outputFile = "output.txt";
        String missingIdsFile = "missing_ids.txt";
        String cheeseWithoutHeadersFile = "cheese_without_headers.csv";
        String cheeseWithoutIdsFile = "cheese_without_ids.csv";

        try {
            List<String[]> cheeseData = readCSV(inputFile);
            writeCheeseWithoutHeaders(cheeseData, cheeseWithoutHeadersFile);
            writeCheeseWithoutIds(cheeseData, cheeseWithoutIdsFile);

            int pasteurizedCount = 0;
            int rawMilkCount = 0;
            int organicHighMoistureCount = 0;
            Map<String, Integer> milkTypeCounts = new HashMap<>();
            double totalMoisture = 0;
            int moistureCount = 0;
            int lacticCheeseCount = 0;
            Set<Integer> existingIds = new HashSet<>();
            List<Integer> missingIds = new ArrayList<>();

            for (String[] row : cheeseData.subList(1, cheeseData.size())) {
                if (row.length > 8 && !row[8].isEmpty()) {
                    if (row[8].equalsIgnoreCase("Pasteurized")) {
                        pasteurizedCount++;
                    } else if (row[8].equalsIgnoreCase("Raw Milk")) {
                        rawMilkCount++;
                    }
                }

                if (row.length > 6 && !row[6].isEmpty() && row[6].matches("\\d+")) {
                    int organic = Integer.parseInt(row[6]);
                    if (row.length > 3 && !row[3].isEmpty()) {
                        try {
                            double moisture = Double.parseDouble(row[3]);
                            if (organic == 1 && moisture > 41.0) {
                                organicHighMoistureCount++;
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid moisture value in row: " + Arrays.toString(row));
                        }
                    }
                }

                if (row.length > 7 && !row[7].isEmpty()) {
                    String milkType = row[7];
                    milkTypeCounts.put(milkType, milkTypeCounts.getOrDefault(milkType, 0) + 1);
                }

                if (row.length > 3 && !row[3].isEmpty()) {
                    try {
                        totalMoisture += Double.parseDouble(row[3]);
                        moistureCount++;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid moisture value in row: " + Arrays.toString(row));
                    }
                }

                if (row.length > 4 && !row[4].isEmpty()) {
                    if (Pattern.compile("\\blactic\\b", Pattern.CASE_INSENSITIVE).matcher(row[4]).find()) {
                        lacticCheeseCount++;
                    }
                }

                if (row.length > 0 && !row[0].isEmpty()) {
                    try {
                        existingIds.add(Integer.parseInt(row[0]));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid ID value in row: " + Arrays.toString(row));
                    }
                }
            }

            int minId = existingIds.stream().min(Integer::compare).orElse(0);
            int maxId = existingIds.stream().max(Integer::compare).orElse(0);
            for (int i = minId; i <= maxId; i++) {
                if (!existingIds.contains(i)) {
                    missingIds.add(i);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("Number of cheeses using pasteurized milk: " + pasteurizedCount + "\n");
                writer.write("Number of cheeses using raw milk: " + rawMilkCount + "\n");
                writer.write("Number of organic cheeses with moisture > 41.0%: " + organicHighMoistureCount + "\n");
                writer.write("Most common milk type: " + Collections.max(milkTypeCounts.entrySet(), Map.Entry.comparingByValue()).getKey() + "\n");
                writer.write("Average moisture percent: " + (totalMoisture / moistureCount) + "\n");
                writer.write("Number of lactic cheeses: " + lacticCheeseCount + "\n");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(missingIdsFile))) {
                for (int id : missingIds) {
                    writer.write(id + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        return data;
    }

    private static void writeCheeseWithoutHeaders(List<String[]> cheeseData, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 1; i < cheeseData.size(); i++) {
                writer.write(String.join(",", cheeseData.get(i)) + "\n");
            }
        }
    }

    private static void writeCheeseWithoutIds(List<String[]> cheeseData, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 1; i < cheeseData.size(); i++) {
                String[] row = cheeseData.get(i);
                String[] newRow = Arrays.copyOfRange(row, 1, row.length);
                writer.write(String.join(",", newRow) + "\n");
            }
        }
    }
}