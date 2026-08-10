package com.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility parser for handling CSV import and export data formatting.
 */
public class CSVParser {

    /**
     * Parses a CSV input stream into a list of string array rows.
     *
     * @param inputStream CSV input stream
     * @return List of string array rows
     */
    public static List<String[]> parseCSVStream(InputStream inputStream) {
        List<String[]> rows = new ArrayList<String[]>();
        if (inputStream == null) return rows;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    for (int i = 0; i < tokens.length; i++) {
                        tokens[i] = tokens[i].trim().replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                    }
                    rows.add(tokens);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}

// Refactored commit step: feat(utility): implement CSVParser stream parser & DatabaseBackupUtil snapshots
