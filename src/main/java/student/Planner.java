package student;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A planner class that manages a set of board games and allows filtering and sorting of the games.
 */
public class Planner implements IPlanner {
    /** The set of all board games managed by the planner. */
    private Set<BoardGame> games;
    /** The initial set of board games, used for resetting the state. */
    private Set<BoardGame> initialGames;

    /**
     * Constructs a new Planner with the specified set of board games.
     * @param games the set of board games to be managed by the planner
     */
    public Planner(Set<BoardGame> games) {
        this.games = new HashSet<>(games);
        this.initialGames = new HashSet<>(games);
    }

    /**
     * Filters the board games based on the specified filter string and default sorting criteria.
     * @param filter the filter string
     * @return a stream of filtered and sorted board games
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        // Call the detailed filter method with default sorting criteria
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters the board games based on the specified filter string and sorting criteria.
     * @param filter the filter string
     * @param sortOn the game data field to sort on
     * @return a stream of filtered and sorted board games
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // Call the detailed filter method with default ascending order
        return filter(filter, sortOn, true);
    }

    /**
     * Filters and sorts the board games based on the specified filter string, sorting criteria, and sorting order.
     * @param filter the filter string
     * @param sortOn the game data field to sort on
     * @param ascending true if the sort order is ascending, false if descending
     * @return a stream of filtered and sorted board games
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        String[] filters = filter.split(",");

        Stream<BoardGame> filteredStream = games.stream();

        if (!filter.isEmpty()) {
            for (String singleFilter : filters) {
                String[] parts = singleFilter.split("(?<=[a-zA-Z])\\s*(?=[><=!~])");

                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid filter: " + singleFilter);
                }

                String columnName = parts[0].trim();
                String operatorAndValue = parts[1].trim();

                String operator;
                String value;
                if (operatorAndValue.contains(">=") || operatorAndValue.contains("<=")
                        || operatorAndValue.contains("==") || operatorAndValue.contains("!=")
                        || operatorAndValue.contains("~=")) {
                    operator = operatorAndValue.substring(0, 2);
                    value = operatorAndValue.substring(2).trim();
                } else {
                    operator = operatorAndValue.substring(0, 1);
                    value = operatorAndValue.substring(1).trim();
                }

                GameData col = GameData.fromString(columnName);

                switch (operator) {
                    case ">":
                    case "<":
                    case ">=":
                    case "<=":
                        if (col == GameData.NAME) {
                            filteredStream = filterStringCompare(filteredStream, col, value,
                                    String::compareToIgnoreCase, operator);
                        } else {
                            filteredStream = applyNumericFilter(filteredStream, col, operator, value);
                        }
                        break;
                    case "==":
                        if (col == GameData.NAME) {
                            filteredStream = filterStringEquality(filteredStream, col, value, true);
                        } else {
                            filteredStream = applyNumericFilter(filteredStream, col, operator, value);
                        }
                        break;
                    case "!=":
                        if (col == GameData.NAME) {
                            filteredStream = filterStringEquality(filteredStream, col, value, false);
                        } else {
                            filteredStream = applyNumericFilter(filteredStream, col, operator, value);
                        }
                        break;
                    case "~=":
                        if (col != GameData.NAME) {
                            throw new IllegalArgumentException(
                                    "The ~= operator can only be applied to the name field."
                            );
                        }
                        filteredStream = filterString(filteredStream, col, value, String::contains);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + operator);
                }
            }
        }

        // Update the games set with the filtered results
        games = filteredStream.collect(Collectors.toSet());

        // Sort the filtered stream of board games based on the specified sorting criteria
        return games.stream().sorted((g1, g2) -> {
            int comparison = compare(g1, g2, sortOn);
            return ascending ? comparison : -comparison;
        });
    }

    /**
     * Compares two board games based on the specified sorting criteria.
     * @param g1 the first board game
     * @param g2 the second board game
     * @param sortOn the game data field to sort on
     * @return a negative integer, zero, or a positive integer as the first board game
     * is less than, equal to, or greater than the second board game
     */
    private int compare(BoardGame g1, BoardGame g2, GameData sortOn) {
        switch (sortOn) {
            case NAME:
                return g1.getName().compareToIgnoreCase(g2.getName());
            case RATING:
                return Double.compare(g1.getRating(), g2.getRating());
            case DIFFICULTY:
                return Double.compare(g1.getDifficulty(), g2.getDifficulty());
            case RANK:
                return Integer.compare(g1.getRank(), g2.getRank());
            case MIN_PLAYERS:
                return Integer.compare(g1.getMinPlayers(), g2.getMinPlayers());
            case MAX_PLAYERS:
                return Integer.compare(g1.getMaxPlayers(), g2.getMaxPlayers());
            case MIN_TIME:
                return Integer.compare(g1.getMinPlayTime(), g2.getMinPlayTime());
            case MAX_TIME:
                return Integer.compare(g1.getMaxPlayTime(), g2.getMaxPlayTime());
            case YEAR:
                return Integer.compare(g1.getYearPublished(), g2.getYearPublished());
            default:
                throw new IllegalArgumentException("Invalid GameData: " + sortOn);
        }
    }

    /**
     * Applies a numeric filter to the specified stream of board games.
     * @param stream the stream of board games
     * @param col the game data field to filter on
     * @param operator the operator to use for filtering
     * @param value the value to filter on
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> applyNumericFilter(Stream<BoardGame> stream, GameData col,
                                                 String operator, String value) {
        if (col == GameData.NAME) {
            throw new IllegalArgumentException("The " + operator + " operator can only be applied to numeric fields.");
        } else if (col == GameData.RATING || col == GameData.DIFFICULTY) {
            double doubleValue = Double.parseDouble(value);
            return applyDoubleFilter(stream, col, operator, doubleValue);
        } else {
            int intValue = Integer.parseInt(value);
            return applyIntFilter(stream, col, operator, intValue);
        }
    }

    /**
     * Applies a double filter to the specified stream of board games.
     * @param stream the stream of board games
     * @param col the game data field to filter on
     * @param operator the operator to use for filtering
     * @param value the double value to filter on
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> applyDoubleFilter(Stream<BoardGame> stream, GameData col, String operator, double value) {
        switch (operator) {
            case ">":
                return filterNumericDouble(stream, value, (g, v) -> g.getNumericValueDouble(col) > v);
            case "<":
                return filterNumericDouble(stream, value, (g, v) -> g.getNumericValueDouble(col) < v);
            case ">=":
                return filterNumericDouble(stream, value, (g, v) -> g.getNumericValueDouble(col) >= v);
            case "<=":
                return filterNumericDouble(stream, value, (g, v) -> g.getNumericValueDouble(col) <= v);
            case "==":
                return filterNumericDouble(stream, value, (g, v) -> g.getNumericValueDouble(col).equals(v));
            case "!=":
                return filterNumericDouble(stream, value, (g, v) -> !g.getNumericValueDouble(col).equals(v));
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    /**
     * Applies an integer filter to the specified stream of board games.
     * @param stream the stream of board games
     * @param col the game data field to filter on
     * @param operator the operator to use for filtering
     * @param value the integer value to filter on
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> applyIntFilter(Stream<BoardGame> stream, GameData col, String operator, int value) {
        switch (operator) {
            case ">":
                return filterNumericInt(stream, value, (g, v) -> g.getNumericValueInt(col) > v);
            case "<":
                return filterNumericInt(stream, value, (g, v) -> g.getNumericValueInt(col) < v);
            case ">=":
                return filterNumericInt(stream, value, (g, v) -> g.getNumericValueInt(col) >= v);
            case "<=":
                return filterNumericInt(stream, value, (g, v) -> g.getNumericValueInt(col) <= v);
            case "==":
                return filterNumericInt(stream, value, (g, v) -> g.getNumericValueInt(col).equals(v));
            case "!=":
                return filterNumericInt(stream, value, (g, v) -> !g.getNumericValueInt(col).equals(v));
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    /**
     * Filters the stream of board games based on a numeric double value.
     * @param stream the stream of board games
     * @param value the numeric double value to filter on
     * @param predicate the predicate to apply for filtering
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> filterNumericDouble(Stream<BoardGame> stream, double value,
                                                  BiPredicate<BoardGame, Double> predicate) {
        return stream.filter(g -> predicate.test(g, value));
    }

    /**
     * Filters the stream of board games based on a numeric integer value.
     * @param stream the stream of board games
     * @param value the numeric integer value to filter on
     * @param predicate the predicate to apply for filtering
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> filterNumericInt(Stream<BoardGame> stream, int value,
                                               BiPredicate<BoardGame, Integer> predicate) {
        return stream.filter(g -> predicate.test(g, value));
    }

    /**
     * Filters the stream of board games based on a string value.
     * @param stream the stream of board games
     * @param col the game data field to filter on
     * @param value the string value to filter on
     * @param predicate the predicate to apply for filtering
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> filterString(Stream<BoardGame> stream, GameData col, String value,
                                           BiPredicate<String, String> predicate) {
        return stream.filter(g -> predicate.test(g.getStringValue(col).toLowerCase(), value.toLowerCase()));
    }

    /**
     * Filters a stream of board games based on string equality or inequality.
     * @param stream the stream of board games
     * @param col the game data field to filter on
     * @param value the string value to filter on
     * @param isEqual if true, filters the stream to include games where the column value equals
     *                the provided value; if false, filters the stream to include games where the
     *                column value does not equal the provided value
     * @return a stream of filtered board games
     */
    private Stream<BoardGame> filterStringEquality(Stream<BoardGame> stream, GameData col,
                                                   String value, boolean isEqual) {
        if (isEqual) {
            return stream.filter(g -> g.getStringValue(col).equalsIgnoreCase(value));
        } else {
            return stream.filter(g -> !g.getStringValue(col).equalsIgnoreCase(value));
        }
    }

    /**
     * Filters a stream of board games based on string comparison.
     * @param stream the stream of board games
     * @param col the game data field to filter on
     * @param value the string value to filter on
     * @param comparator the comparator used for string comparison
     * @param operator the comparison operator (">", "<", ">=", "<=")
     * @return a stream of filtered board games
     * @throws IllegalArgumentException if an invalid operator is provided
     */
    private Stream<BoardGame> filterStringCompare(Stream<BoardGame> stream, GameData col, String value,
                                                  Comparator<String> comparator, String operator) {
        switch (operator) {
            case ">":
                return stream.filter(
                        g -> comparator.compare(g.getStringValue(col).toLowerCase(), value.toLowerCase()) > 0
                );
            case "<":
                return stream.filter(
                        g -> comparator.compare(g.getStringValue(col).toLowerCase(), value.toLowerCase()) < 0
                );
            case ">=":
                return stream.filter(
                        g -> comparator.compare(g.getStringValue(col).toLowerCase(), value.toLowerCase()) >= 0
                );
            case "<=":
                return stream.filter(
                        g -> comparator.compare(g.getStringValue(col).toLowerCase(), value.toLowerCase()) <= 0
                );
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    /**
     * Resets the board games to the initial state.
     */
    @Override
    public void reset() {
        games = new HashSet<>(initialGames);
    }
}
