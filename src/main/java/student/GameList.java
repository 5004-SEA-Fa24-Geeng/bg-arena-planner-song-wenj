package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of the IGameList interface.
 * This class manages a list of BoardGame objects and provides various methods to manipulate
 * and query the list.
 */
public class GameList implements IGameList {

    /** The list of BoardGame objects being managed. */
    private List<BoardGame> games;

    /**
     * Constructor for the GameList.
     * Initializes an empty list of BoardGame objects.
     */
    public GameList() {
        this.games = new ArrayList<>();
    }

    /**
     * Gets the contents of the game list as a list of names (Strings) in ascending order, ignoring case.
     * @return the list of game names in ascending order, ignoring case.
     */
    @Override
    public List<String> getGameNames() {
        List<String> gameNames = new ArrayList<>();
        for (BoardGame game : games) {
            gameNames.add(game.getName());
        }
        gameNames.sort(String.CASE_INSENSITIVE_ORDER);
        return gameNames;
    }

    /**
     * Adds games to the list based on the specified criteria.
     * The criteria can be a game name, a number indicating the position in the filtered list,
     * a range of numbers, or "all" to add all games.
     * @param str the criteria for adding games.
     * @param filtered the stream of filtered BoardGame objects to add from.
     * @throws IllegalArgumentException if the criteria is invalid or the game is not found.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Invalid game name");
        }

        List<BoardGame> filteredGames = filtered.collect(Collectors.toList());

        if (str.equalsIgnoreCase(IGameList.ADD_ALL)) {
            for (BoardGame game : filteredGames) {
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
        } else if (str.matches("\\d+-\\d+")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;
            if (start < 0 || end >= filteredGames.size() || start > end) {
                throw new IllegalArgumentException("Invalid range");
            }
            for (int i = start; i <= end; i++) {
                BoardGame game = filteredGames.get(i);
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
        } else if (str.matches("\\d+")) {
            int index = Integer.parseInt(str) - 1;
            if (index < 0 || index >= filteredGames.size()) {
                throw new IllegalArgumentException("Invalid index");
            }
            BoardGame game = filteredGames.get(index);
            if (!games.contains(game)) {
                games.add(game);
            }
        } else {
            boolean found = false;
            for (BoardGame game : filteredGames) {
                if (game.getName().equalsIgnoreCase(str.trim())) {
                    if (!games.contains(game)) {
                        games.add(game);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Game not found in the filtered list: " + str);
            }
        }
    }

    /**
     * Removes games from the list based on the specified criteria.
     * The criteria can be a game name, a number indicating the position in the list,
     * a range of numbers, or "all" to remove all games.
     * @param str the criteria for removing games.
     * @throws IllegalArgumentException if the criteria is invalid or the game is not found.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Invalid game name");
        }

        if (str.equalsIgnoreCase(IGameList.ADD_ALL)) {
            games.clear();
        } else if (str.matches("\\d+-\\d+")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;
            if (start < 0 || end >= games.size() || start > end) {
                throw new IllegalArgumentException("Invalid range");
            }
            for (int i = end; i >= start; i--) {
                games.remove(i);
            }
        } else if (str.matches("\\d+")) {
            int index = Integer.parseInt(str) - 1;
            if (index < 0 || index >= games.size()) {
                throw new IllegalArgumentException("Invalid index");
            }
            games.remove(index);
        } else {
            boolean found = false;
            for (Iterator<BoardGame> iterator = games.iterator(); iterator.hasNext();) {
                BoardGame game = iterator.next();
                if (game.getName().equalsIgnoreCase(str.trim())) {
                    iterator.remove();
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Game not found in the list: " + str);
            }
        }
    }

    /**
     * Clears the entire list of games.
     */
    @Override
    public void clear() {
        games.clear();
    }

    /**
     * Saves the list of games to a file.
     * The contents of the file will be each game name on a new line. It will
     * overwrite the file if it already exists.
     * Saves them in the same order as getGameNames.
     * @param filename The name of the file to save the list to.
     */
    @Override
    public void saveGame(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            List<String> gameNames = getGameNames();
            for (String name : gameNames) {
                writer.write(name);
                writer.newLine();
            }
            System.out.println("Games have been saved to " + filename);
        } catch (IOException e) {
            System.err.println("An error occurred while saving the game.");
        }
    }

    /**
     * Returns the number of games in the list.
     * @return the number of games in the list.
     */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * Returns a copy of the list of BoardGames.
     * This method is primarily for testing purposes.
     * @return a copy of the list of BoardGames.
     */
    List<BoardGame> getGames() {
        return new ArrayList<>(games);
    }
}
