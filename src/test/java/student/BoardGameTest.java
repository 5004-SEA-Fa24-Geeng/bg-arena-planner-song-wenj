package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the BoardGame class.
 */
class BoardGameTest {
    /** The BoardGame instance used for testing. */
    private BoardGame game;

    /**
     * Sets up a BoardGame instance before each test.
     */
    @BeforeEach
    public void setUp() {
        game = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
    }

    /**
     * Tests the constructor of the BoardGame class.
     * Verifies that all fields are correctly initialized.
     */
    @Test
    public void testConstructor() {
        assertEquals("13 Clues", game.getName());
        assertEquals(208766, game.getId());
        assertEquals(2, game.getMinPlayers());
        assertEquals(6, game.getMaxPlayers());
        assertEquals(30, game.getMinPlayTime());
        assertEquals(30, game.getMaxPlayTime());
        assertEquals(1.8966, game.getDifficulty());
        assertEquals(3365, game.getRank());
        assertEquals(6.60806, game.getRating());
        assertEquals(2016, game.getYearPublished());
    }

    /**
     * Tests the getter methods of the BoardGame class.
     * Verifies that each getter method returns the correct value for its corresponding field.
     */
    @Test
    public void testGetters() {
        assertEquals("13 Clues", game.getName());
        assertEquals(208766, game.getId());
        assertEquals(2, game.getMinPlayers());
        assertEquals(6, game.getMaxPlayers());
        assertEquals(30, game.getMaxPlayTime());
        assertEquals(30, game.getMinPlayTime());
        assertEquals(1.8966, game.getDifficulty());
        assertEquals(3365, game.getRank());
        assertEquals(6.60806, game.getRating());
        assertEquals(2016, game.getYearPublished());
    }

    /**
     * Tests the toStringWithInfo method of the BoardGame class.
     * Verifies that the method returns the expected string representation based on the GameData column.
     */
    @Test
    public void testToStringWithInfo() {
        assertEquals("13 Clues", game.toStringWithInfo(GameData.NAME));
        assertEquals("13 Clues (6.61)", game.toStringWithInfo(GameData.RATING));
        assertEquals("13 Clues (1.90)", game.toStringWithInfo(GameData.DIFFICULTY));
        assertEquals("13 Clues (3365)", game.toStringWithInfo(GameData.RANK));
        assertEquals("13 Clues (2)", game.toStringWithInfo(GameData.MIN_PLAYERS));
        assertEquals("13 Clues (6)", game.toStringWithInfo(GameData.MAX_PLAYERS));
        assertEquals("13 Clues (30)", game.toStringWithInfo(GameData.MIN_TIME));
        assertEquals("13 Clues (30)", game.toStringWithInfo(GameData.MAX_TIME));
        assertEquals("13 Clues (2016)", game.toStringWithInfo(GameData.YEAR));
    }

    /**
     * Tests the getNumericValueInt method of the BoardGame class.
     * Verifies that the method returns the correct numeric value based on the GameData column.
     */
    @Test
    public void testGetNumericValueInt() {
        assertEquals(Integer.valueOf(3365), game.getNumericValueInt(GameData.RANK));
        assertEquals(Integer.valueOf(2), game.getNumericValueInt(GameData.MIN_PLAYERS));
        assertEquals(Integer.valueOf(6), game.getNumericValueInt(GameData.MAX_PLAYERS));
        assertEquals(Integer.valueOf(30), game.getNumericValueInt(GameData.MIN_TIME));
        assertEquals(Integer.valueOf(30), game.getNumericValueInt(GameData.MAX_TIME));
        assertEquals(Integer.valueOf(2016), game.getNumericValueInt(GameData.YEAR));
    }

    /**
     * Tests the getNumericValueInt method of the BoardGame class with invalid GameData.
     * Verifies that the method handles invalid input correctly.
     */
    @Test
    public void testGetNumericValueIntInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> game.getNumericValueInt(GameData.NAME));
        assertEquals("Invalid column: NAME", e.getMessage());
    }

    /**
     * Tests the getNumericValueDouble method of the BoardGame class.
     * Verifies that the method returns the correct numeric value based on the GameData column.
     */
    @Test
    public void testGetNumericValueDouble() {
        assertEquals(Double.valueOf(1.8966), game.getNumericValueDouble(GameData.DIFFICULTY));
        assertEquals(Double.valueOf(6.60806), game.getNumericValueDouble(GameData.RATING));
    }

    /**
     * Tests the getNumericValueDouble method of the BoardGame class with invalid GameData.
     * Verifies that the method handles invalid input correctly.
     */
    @Test
    public void testGetNumericValueDoubleInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> game.getNumericValueDouble(GameData.NAME));
        assertEquals("Invalid column: NAME", e.getMessage());
    }

    /**
     * Tests the getStringValue method of the BoardGame class.
     * Verifies that the method returns the correct string value based on the GameData column.
     */
    @Test
    public void testGetStringValue() {
        assertEquals("13 Clues", game.getStringValue(GameData.NAME));
    }

    /**
     * Tests the getStringValue method of the BoardGame class with invalid GameData.
     * Verifies that the method handles invalid input correctly.
     */
    @Test
    public void testGetStringValueInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> game.getStringValue(GameData.RANK));
        assertEquals("Invalid column: RANK", e.getMessage());
    }

    /**
     * Tests the toString method of the BoardGame class.
     * Verifies that the method returns the correct string representation of the object.
     */
    @Test
    public void testToString() {
        String expected = "BoardGame{name='13 Clues', id=208766, minPlayers=2, maxPlayers=6, maxPlayTime=30, " +
                "minPlayTime=30, difficulty=1.8966, rank=3365, averageRating=6.60806, yearPublished=2016}";
        assertEquals(expected, game.toString());
    }

    /**
     * Tests the equals method of the BoardGame class.
     * Verifies that the method correctly determines equality based on the specified fields.
     */
    @Test
    public void testEquals() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        assertEquals(game, g1);

        BoardGame g2 = new BoardGame("13 Clues", 1, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        assertNotEquals(game, g2);
    }

    /**
     * Tests the hashCode method of the BoardGame class.
     * Verifies that the method returns a consistent hash code based on the specified fields.
     */
    @Test
    public void testHashCode() {
        BoardGame g1 = new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        assertEquals(game.hashCode(), g1.hashCode());

        BoardGame g2 = new BoardGame("13 Clues", 1, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016);
        assertNotEquals(game.hashCode(), g2.hashCode());
    }
}