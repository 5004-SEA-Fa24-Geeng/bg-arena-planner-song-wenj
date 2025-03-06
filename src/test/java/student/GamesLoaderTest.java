package student;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GamesLoader class.
 * This class contains unit tests for the GamesLoader methods.
 */
class GamesLoaderTest {
    /** File name of the CSV file to load. */
    private static final String FILENAME = "/collection.csv";

    /**
     * Test the loadGamesFile method with a valid file.
     * This method verifies that loading a valid file returns a non-empty set
     * and checks the properties of a specific BoardGame object.
     */
    @Test
    public void testLoadGamesFile() {
        Set<BoardGame> games = GamesLoader.loadGamesFile(FILENAME);
        assertNotNull(games);
        assertFalse(games.isEmpty());

        Optional<BoardGame> game = games.stream().filter(g -> g.getName().equals("13 Clues")).findFirst();
        assertTrue(game.isPresent());
        assertEquals(208766, game.get().getId());
        assertEquals(2, game.get().getMinPlayers());
        assertEquals(6, game.get().getMaxPlayers());
        assertEquals(30, game.get().getMaxPlayTime());
        assertEquals(30, game.get().getMinPlayTime());
        assertEquals(1.8966, game.get().getDifficulty());
        assertEquals(3365, game.get().getRank());
        assertEquals(6.60806, game.get().getRating());
        assertEquals(2016, game.get().getYearPublished());
    }

    /**
     * Test the loadGamesFile method with an invalid file.
     * This method verifies that loading an invalid file returns an empty set.
     */
    @Test
    public void testLoadInvalidFile() {
        Set<BoardGame> games = GamesLoader.loadGamesFile("/test_invalid.csv");
        assertNotNull(games);
        assertTrue(games.isEmpty());
    }

    /**
     * Test the loadGamesFile method with an empty file.
     * This method verifies that loading an empty file returns an empty set.
     */
    @Test
    public void testLoadEmptyFile() {
        Set<BoardGame> games = GamesLoader.loadGamesFile("/test_empty.csv");
        assertNotNull(games);
        assertTrue(games.isEmpty());
    }
}