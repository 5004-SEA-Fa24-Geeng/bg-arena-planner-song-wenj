package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Planner class.
 */
class PlannerTest {
    /** The planner instance used for testing. */
    private Planner planner;
    /** The set of board games used for testing. */
    private Set<BoardGame> games;

    /**
     * Initializes a set of board games and the planner.
     */
    @BeforeEach
    public void setUp() {
        games = new HashSet<>();
        games.add(new BoardGame("13 Clues", 208766, 2, 6, 30,
                30, 1.8966, 3365, 6.60806, 2016));
        games.add(new BoardGame("15 Days", 298619, 1, 4, 20,
                20, 1.8235, 6461, 6.43977, 2020));
        games.add(new BoardGame("24/7: The Game", 25182, 2, 4, 20,
                20, 1.8889, 9188, 6.20901, 2006));
        planner = new Planner(games);
    }

    /**
     * Tests filtering by name containing a specific substring.
     */
    @Test
    public void testFilterByNameContains() {
        Stream<BoardGame> result = planner.filter("name~=s");
        Set<BoardGame> filteredGames = result.collect(Collectors.toSet());
        assertEquals(2, filteredGames.size());
    }

    /**
     * Tests filtering by rating greater than a specific value.
     */
    @Test
    public void testFilterByRatingGreaterThan() {
        Stream<BoardGame> result = planner.filter("rating>6.4");
        Set<BoardGame> filteredGames = result.collect(Collectors.toSet());
        assertEquals(2, filteredGames.size());
        assertTrue(filteredGames.stream().anyMatch(game -> game.getName().equals("13 Clues")));
        assertTrue(filteredGames.stream().anyMatch(game -> game.getName().equals("15 Days")));
    }

    /**
     * Tests filtering by multiple criteria.
     */
    @Test
    public void testFilterByMultipleCriteria() {
        Stream<BoardGame> result = planner.filter("minPlayers>=2, maxPlayers<=6");
        Set<BoardGame> filteredGames = result.collect(Collectors.toSet());
        assertEquals(2, filteredGames.size());
    }

    /**
     * Tests default sorting after filtering.
     */
    @Test
    public void testFilterDefaultSorting() {
        Stream<BoardGame> result = planner.filter("minPlayers>=1");
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);
        assertEquals("13 Clues", sortedGames[0].getName());
        assertEquals("15 Days", sortedGames[1].getName());
        assertEquals("24/7: The Game", sortedGames[2].getName());
    }

    /**
     * Tests default sorting order after filtering.
     */
    @Test
    public void testFilterDefaultSortingOrder() {
        Stream<BoardGame> result = planner.filter("minPlayers>=1", GameData.RATING);
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);
        assertEquals("24/7: The Game", sortedGames[0].getName());
        assertEquals("15 Days", sortedGames[1].getName());
        assertEquals("13 Clues", sortedGames[2].getName());
    }

    /**
     * Tests sorting by rating in descending order after filtering.
     */
    @Test
    public void testFilterSortByRatingDesc() {
        Stream<BoardGame> result = planner.filter("minPlayers>=1", GameData.RATING, false);
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);
        assertEquals("13 Clues", sortedGames[0].getName());
        assertEquals("15 Days", sortedGames[1].getName());
        assertEquals("24/7: The Game", sortedGames[2].getName());
    }

    /**
     * Tests filtering and resetting the planner.
     */
    @Test
    public void testFilterAndReset() {
        Stream<BoardGame> result = planner.filter("rating>=6.4");
        Set<BoardGame> filteredGames = result.collect(Collectors.toSet());
        assertEquals(2, filteredGames.size());

        planner.reset();

        assertEquals(3, planner.getGames().size());
        assertTrue(planner.getGames().containsAll(games));
    }
}