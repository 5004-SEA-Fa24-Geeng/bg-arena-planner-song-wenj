package student;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Loads the games from the csv file into a set of BoardGame objects.
 * This file is stored in the resources folder, and while it is
 * passed in - often tends to be more fixed.
 * It assumes there are no comma's in the data (and does not handle errors if
 * there are extra commas like in the name).
 */
public final class GamesLoader {
    /** Standard csv delim. */
    private static final String DELIMITER = ",";

    /** Private constructor to prevent instantiation. */
    private GamesLoader() {
    }

    /**
     * Loads the games from the csv file into a set of BoardGame objects.
     * @param fileName the name of the file to load
     * @return a set of BoardGame objects
     */
    public static Set<BoardGame> loadGamesFile(String fileName) {
        Set<BoardGame> games = new HashSet<>();

        List<String> lines;

        try {
            // This is so we can store the files in the resources folder
            InputStream is = GamesLoader.class.getResourceAsStream(fileName);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            lines = reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return games;
        }

        if (lines.isEmpty()) {
            return games;
        }

        Map<GameData, Integer> columnMap = processHeader(lines.remove(0));

        games = lines.stream().map(line -> toBoardGame(line, columnMap))
                .filter(game -> game != null).collect(Collectors.toSet());

        return games;
    }

    /**
     * Converts a line from the csv file into a BoardGame object.
     * @param line the line to convert
     * @param columnMap the map of columns to index
     * @return a BoardGame object
     */
    private static BoardGame toBoardGame(String line, Map<GameData, Integer> columnMap) {
        // Split the CSV line into columns based on the delimiter
        String[] columns = line.split(DELIMITER);

        // Find the maximum index in the columnMap
        Optional<Integer> maxIndex = columnMap.values().stream().max(Integer::compareTo);
        if (maxIndex.isPresent()) {
            int max = maxIndex.get();
            // If the number of columns is less than or equal to the maximum index, return null
            if (columns.length <= max) {
                return null;
            }
        }

        try {
            // Validate that each required column has non-empty data
            for (GameData col : columnMap.keySet()) {
                if (columns[columnMap.get(col)].trim().isEmpty()) {
                    return null;
                }
            }

            // Create a new BoardGame object using the data from the columns
            BoardGame game = new BoardGame(columns[columnMap.get(GameData.NAME)],
                    Integer.parseInt(columns[columnMap.get(GameData.ID)]),
                    Integer.parseInt(columns[columnMap.get(GameData.MIN_PLAYERS)]),
                    Integer.parseInt(columns[columnMap.get(GameData.MAX_PLAYERS)]),
                    Integer.parseInt(columns[columnMap.get(GameData.MIN_TIME)]),
                    Integer.parseInt(columns[columnMap.get(GameData.MAX_TIME)]),
                    Double.parseDouble(columns[columnMap.get(GameData.DIFFICULTY)]),
                    Integer.parseInt(columns[columnMap.get(GameData.RANK)]),
                    Double.parseDouble(columns[columnMap.get(GameData.RATING)]),
                    Integer.parseInt(columns[columnMap.get(GameData.YEAR)]));
            return game;
        } catch (NumberFormatException e) {
            // Skip the line if there is an issue parsing the numbers
            return null;
        }
    }

    /**
     * Processes the header line to determine the column mapping.
     * It is common to do this for csv files as the columns can be in any order.
     * This makes it order independent by taking a moment to link the columns
     * with their actual index in the file.
     * @param header the header line
     * @return a map of column to index
     */
    private static Map<GameData, Integer> processHeader(String header) {
        Map<GameData, Integer> columnMap = new HashMap<>();
        String[] columns = header.split(DELIMITER);
        for (int i = 0; i < columns.length; i++) {
            try {
                GameData col = GameData.fromColumnName(columns[i]);
                columnMap.put(col, i);
            } catch (IllegalArgumentException e) {
                // Ignore columns that do not match any GameData enum constant
            }
        }
        return columnMap;
    }
}
