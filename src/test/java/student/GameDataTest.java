package student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GameData enum.
 * This class contains unit tests for the GameData enum methods.
 */
class GameDataTest {

    /**
     * Test the getColumnName method.
     * This method verifies that the getColumnName method returns the correct column name
     * for each enum constant.
     */
    @Test
    public void testGetColumnName() {
        assertEquals("objectname", GameData.NAME.getColumnName());
        assertEquals("objectid", GameData.ID.getColumnName());
        assertEquals("average", GameData.RATING.getColumnName());
        assertEquals("avgweight", GameData.DIFFICULTY.getColumnName());
        assertEquals("rank", GameData.RANK.getColumnName());
        assertEquals("minplayers", GameData.MIN_PLAYERS.getColumnName());
        assertEquals("maxplayers", GameData.MAX_PLAYERS.getColumnName());
        assertEquals("minplaytime", GameData.MIN_TIME.getColumnName());
        assertEquals("maxplaytime", GameData.MAX_TIME.getColumnName());
        assertEquals("yearpublished", GameData.YEAR.getColumnName());
    }

    /**
     * Test the fromColumnName method.
     * This method verifies that the fromColumnName method correctly converts column names
     * to the corresponding enum constants.
     */
    @Test
    public void testFromColumnName() {
        assertEquals(GameData.NAME, GameData.fromColumnName("objectname"));
        assertEquals(GameData.ID, GameData.fromColumnName("objectid"));
        assertEquals(GameData.RATING, GameData.fromColumnName("average"));
        assertEquals(GameData.DIFFICULTY, GameData.fromColumnName("avgweight"));
        assertEquals(GameData.RANK, GameData.fromColumnName("rank"));
        assertEquals(GameData.MIN_PLAYERS, GameData.fromColumnName("minplayers"));
        assertEquals(GameData.MAX_PLAYERS, GameData.fromColumnName("maxplayers"));
        assertEquals(GameData.MIN_TIME, GameData.fromColumnName("minplaytime"));
        assertEquals(GameData.MAX_TIME, GameData.fromColumnName("maxplaytime"));
        assertEquals(GameData.YEAR, GameData.fromColumnName("yearpublished"));
    }

    /**
     * Test the fromColumnName method with an invalid column name.
     * This method verifies that the fromColumnName method throws an IllegalArgumentException
     * when given an invalid column name.
     */
    @Test
    public void testFromColumnNameInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> GameData.fromColumnName("invalid"));
        assertEquals("No column with name invalid", e.getMessage());
    }

    /**
     * Test the fromString method.
     * This method verifies that the fromString method correctly converts both enum names
     * and column names to the corresponding enum constants.
     */
    @Test
    public void testFromString() {
        assertEquals(GameData.NAME, GameData.fromString("NAME"));
        assertEquals(GameData.NAME, GameData.fromString("objectname"));
        assertEquals(GameData.RATING, GameData.fromString("rating"));
        assertEquals(GameData.RATING, GameData.fromString("average"));
        assertEquals(GameData.MIN_TIME, GameData.fromString("min_time"));
        assertEquals(GameData.MIN_TIME, GameData.fromString("minplaytime"));
    }

    /**
     * Test the fromString method with an invalid name.
     * This method verifies that the fromString method throws an IllegalArgumentException
     * when given an invalid name.
     */
    @Test
    public void testFromStringInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> GameData.fromString("invalid"));
        assertEquals("No column with name invalid", e.getMessage());
    }
}