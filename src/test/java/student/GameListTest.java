package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameList class.
 */
class GameListTest {

    /** The GameList instance used for testing. */
    private GameList gameList;

    /**
     * Sets up the GameList instance before each test.
     */
    @BeforeEach
    public void setUp() {
        gameList = new GameList();
    }

    /**
     * Tests the getGameNames method with a populated list.
     * Ensures that the names are correctly returned in the expected order.
     */
    @Test
    public void testGetGameNames() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("all", filteredStream);

        List<String> gameNames = gameList.getGameNames();
        assertEquals(2, gameNames.size());
        assertEquals("13 Clues", gameNames.get(0));
        assertEquals("15 Days", gameNames.get(1));
    }

    /**
     * Tests the getGameNames method with an empty list.
     * Ensures that the returned list is empty.
     */
    @Test
    public void testGetGameNamesEmptyList() {
        List<String> gameNames = gameList.getGameNames();
        assertTrue(gameNames.isEmpty());
    }

    /**
     * Tests the addToList method with the "all" keyword.
     * Ensures that all games are added to the list.
     */
    @Test
    public void testAddToListAll() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        BoardGame g3 = new BoardGame("24/7: The Game", 25182, 2, 4, 20,
                20, 1.8889, 9188, 6.20901, 2006);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2, g3);

        gameList.addToList("all", filteredStream);

        List<BoardGame> games = gameList.getGames();
        assertEquals(3, games.size());
        assertTrue(games.contains(g1));
        assertTrue(games.contains(g2));
        assertTrue(games.contains(g3));
    }

    /**
     * Tests the addToList method with a range of numbers.
     * Ensures that the specified range of games is added to the list.
     */
    @Test
    public void testAddToListByRange() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        BoardGame g3 = new BoardGame("24/7: The Game", 25182, 2, 4, 20,
                20, 1.8889, 9188, 6.20901, 2006);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2, g3);

        gameList.addToList("1-2", filteredStream);

        List<BoardGame> games = gameList.getGames();
        assertEquals(2, games.size());
        assertTrue(games.contains(g1));
        assertTrue(games.contains(g2));
    }

    /**
     * Tests the addToList method with a specific number.
     * Ensures that the game at the specified position is added to the list.
     */
    @Test
    public void testAddToListByNumber() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("2", filteredStream);

        List<BoardGame> games = gameList.getGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(g2));
    }

    /**
     * Tests the addToList method with a specific game name.
     * Ensures that the game with the specified name is added to the list.
     */
    @Test
    public void testAddToListByName() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("13 Clues", filteredStream);

        List<BoardGame> games = gameList.getGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(g1));
    }

    /**
     * Tests the addToList method with invalid game names.
     * Ensures that an IllegalArgumentException is thrown for null or empty game names.
     */
    @Test
    public void testAddToListInvalidGameName() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList(null, filteredStream);
        });
        assertEquals("Invalid game name", e1.getMessage());

        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("", filteredStream);
        });
        assertEquals("Invalid game name", e2.getMessage());
    }

    /**
     * Tests the addToList method with an invalid range.
     * Ensures that an IllegalArgumentException is thrown for an invalid range.
     */
    @Test
    public void testAddToListInvalidRange() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("2-3", filteredStream);
        });
        assertEquals("Invalid range", e.getMessage());
    }

    /**
     * Tests the addToList method with an invalid index.
     * Ensures that an IllegalArgumentException is thrown for an invalid index.
     */
    @Test
    public void testAddToListInvalidIndex() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("2", filteredStream);
        });
        assertEquals("Invalid index", e.getMessage());
    }

    /**
     * Tests the addToList method when the game is not found in the filtered list.
     * Ensures that an IllegalArgumentException is thrown if the game name does not match any in the list.
     */
    @Test
    public void testAddToListGameNotFound() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("15 Days", filteredStream);
        });
        assertEquals("Game not found in the filtered list: 15 Days", e.getMessage());
    }

    /**
     * Tests the removeFromList method with the "all" keyword.
     * Ensures that all games are removed from the list.
     */
    @Test
    public void testRemoveFromListAll() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        BoardGame g3 = new BoardGame("24/7: The Game", 25182, 2, 4, 20,
                20, 1.8889, 9188, 6.20901, 2006);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2, g3);

        gameList.addToList("all", filteredStream);

        gameList.removeFromList("all");

        List<BoardGame> games = gameList.getGames();
        assertEquals(0, games.size());
    }

    /**
     * Tests the removeFromList method with a range of numbers.
     * Ensures that the specified range of games is removed from the list.
     */
    @Test
    public void testRemoveFromListByRange() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        BoardGame g3 = new BoardGame("24/7: The Game", 25182, 2, 4, 20,
                20, 1.8889, 9188, 6.20901, 2006);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2, g3);

        gameList.addToList("all", filteredStream);

        gameList.removeFromList("1-2");

        List<BoardGame> games = gameList.getGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(g3));
    }

    /**
     * Tests the removeFromList method with a specific number.
     * Ensures that the game at the specified position is removed from the list.
     */
    @Test
    public void testRemoveFromListByNumber() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("all", filteredStream);

        gameList.removeFromList("2");

        List<BoardGame> games = gameList.getGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(g1));
    }

    /**
     * Tests the removeFromList method with a specific game name.
     * Ensures that the game with the specified name is removed from the list.
     */
    @Test
    public void testRemoveFromListByName() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("all", filteredStream);

        gameList.removeFromList("13 Clues");

        List<BoardGame> games = gameList.getGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(g2));
    }

    /**
     * Tests the removeFromList method with an invalid game name.
     * Ensures that an IllegalArgumentException is thrown when the game name is null or empty.
     */
    @Test
    public void testRemoveFromListInvalidGameName() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        gameList.addToList("all", filteredStream);

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList(null);
        });
        assertEquals("Invalid game name", e1.getMessage());

        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("");
        });
        assertEquals("Invalid game name", e2.getMessage());
    }

    /**
     * Tests the removeFromList method with an invalid range.
     * Ensures that an IllegalArgumentException is thrown for an invalid range.
     */
    @Test
    public void testRemoveFromListInvalidRange() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        gameList.addToList("all", filteredStream);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("2-3");
        });
        assertEquals("Invalid range", e.getMessage());
    }

    /**
     * Tests the removeFromList method with an invalid index.
     * Ensures that an IllegalArgumentException is thrown for an invalid index.
     */
    @Test
    public void testRemoveFromListInvalidIndex() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        gameList.addToList("all", filteredStream);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("2");
        });
        assertEquals("Invalid index", e.getMessage());
    }

    /**
     * Tests the removeFromList method when the game is not found in the list.
     * Ensures that an IllegalArgumentException is thrown if the game name does not match any in the list.
     */
    @Test
    public void testRemoveFromListGameNotFound() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        Stream<BoardGame> filteredStream = Stream.of(g1);

        gameList.addToList("all", filteredStream);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("15 Days");
        });
        assertEquals("Game not found in the list: 15 Days", e.getMessage());
    }

    /**
     * Tests the clear method.
     * Ensures that all games are removed from the list.
     */
    @Test
    public void testClear() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("all", filteredStream);

        gameList.clear();
        List<BoardGame> games = gameList.getGames();
        assertEquals(0, games.size());
    }

    /**
     * Tests the saveGame method.
     * Ensures that the game list is saved to a file.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testSaveGame() throws IOException {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("all", filteredStream);

        String filename = "test_games_list.txt";
        gameList.saveGame(filename);

        List<String> lines = Files.readAllLines(new File(filename).toPath());
        assertEquals(2, lines.size());
        assertTrue(lines.contains("13 Clues"));
        assertTrue(lines.contains("15 Days"));

        new File(filename).delete();
    }

    /**
     * Tests the saveGame method with an empty list.
     * Ensures that an empty file is created when the game list is empty.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testSaveGameWithEmptyList() throws IOException {
        String filename = "test_empty_list.txt";
        gameList.saveGame(filename);

        List<String> lines = Files.readAllLines(new File(filename).toPath());
        assertEquals(0, lines.size());

        new File(filename).delete();
    }

    /**
     * Tests the saveGame method with an invalid filename.
     * Ensures that an error message is printed to the standard error stream.
     */
    @Test
    public void testSaveGameWithInvalidFilename() {
        String invalidFilename = "/invalid/path/test_invalid.txt";

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        gameList.saveGame(invalidFilename);

        String expectedMessage = "An error occurred while saving the game.";
        String actualMessage = errContent.toString();

        assertTrue(actualMessage.contains(expectedMessage));

        System.setErr(originalErr);
    }

    /**
     * Tests the count method.
     * Ensures that the correct number of games is returned.
     */
    @Test
    public void testCount() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        BoardGame g2 = new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020);
        Stream<BoardGame> filteredStream = Stream.of(g1, g2);

        gameList.addToList("all", filteredStream);
        assertEquals(2, gameList.count());

        gameList.removeFromList("1");
        assertEquals(1, gameList.count());
    }
}