package student;

/**
 * Main entry point for the program.
 */
public final class BGArenaPlanner {
    /** Default location of collection - relative to resources. */
    private static final String DEFAULT_COLLECTION = "/collection.csv";

    /** Private constructor as static class. */
    private BGArenaPlanner() {
    }

    /**
     * Main entry point for the program.
     * @param args command line arguments - not used at this time.
     */
    public static void main(String[] args) {
        IGameList gameList = new GameList();
        IPlanner planner = new Planner(GamesLoader.loadGamesFile(DEFAULT_COLLECTION));

        ConsoleApp app = new ConsoleApp(gameList, planner);
        app.start();
    }
}
