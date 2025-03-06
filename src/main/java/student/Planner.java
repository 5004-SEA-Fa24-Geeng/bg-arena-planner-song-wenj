package student;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Planner implements IPlanner {
    private Set<BoardGame> games;
    private Set<BoardGame> initialGames;

    public Planner(Set<BoardGame> games) {
        this.games = new HashSet<>(games);
        this.initialGames = new HashSet<>(games);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        // Call the detailed filter method with default sorting criteria
        return filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // Call the detailed filter method with default ascending order
        return filter(filter, sortOn, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        String[] filters = filter.split(",");

        Stream<BoardGame> filteredStream = games.stream();

        if (!filter.isEmpty()) {
            for (String singleFilter : filters) {
                String[] parts = singleFilter.split("(?<=[a-zA-Z])(?=[><=!~])");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid filter: " + singleFilter);
                }

                String columnName = parts[0];
                String operatorAndValue = parts[1];

                String operator;
                String value;
                if (operatorAndValue.contains(">=") || operatorAndValue.contains("<=") || operatorAndValue.contains("==") ||
                        operatorAndValue.contains("!=") || operatorAndValue.contains("~=")) {
                    operator = operatorAndValue.substring(0, 2);
                    value = operatorAndValue.substring(2);
                } else {
                    operator = operatorAndValue.substring(0, 1);
                    value = operatorAndValue.substring(1);
                }

                GameData col = GameData.fromString(columnName);

                switch (operator) {
                    case ">":
                    case "<":
                    case ">=":
                    case "<=":
                    case "==":
                    case "!=":
                        filteredStream = applyNumericFilter(filteredStream, col, operator, value);
                        break;
                    case "~=":
                        if (col != GameData.NAME) {
                            throw new IllegalArgumentException("The ~= operator can only be applied to the name field.");
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

    private Stream<BoardGame> applyNumericFilter(Stream<BoardGame> stream, GameData col, String operator, String value) {
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

    private Stream<BoardGame> filterNumericDouble(Stream<BoardGame> stream, double value, BiPredicate<BoardGame, Double> predicate) {
        return stream.filter(g -> predicate.test(g, value));
    }

    private Stream<BoardGame> filterNumericInt(Stream<BoardGame> stream, int value, BiPredicate<BoardGame, Integer> predicate) {
        return stream.filter(g -> predicate.test(g, value));
    }

    private Stream<BoardGame> filterString(Stream<BoardGame> stream, GameData col, String value, BiPredicate<String, String> predicate) {
        return stream.filter(g -> predicate.test(g.getStringValue(col).toLowerCase(), value.toLowerCase()));
    }

    @Override
    public void reset() {
        games = new HashSet<>(initialGames);
    }
}
