# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
classDiagram
    BGArenaPlanner ..> IPlanner
    BGArenaPlanner ..> IGameList
    BGArenaPlanner ..> GamesLoader
    BGArenaPlanner --> ConsoleApp

    IPlanner ..> BoardGame
    IPlanner ..> GameData
    IGameList ..> BoardGame
    
    GamesLoader ..> BoardGame
    
    BoardGame ..> GameData
    
    ConsoleApp ..> IPlanner
    ConsoleApp ..> IGameList
    ConsoleApp ..> ConsoleText
    ConsoleApp ..> BoardGame
    ConsoleApp ..> GameData

    class BGArenaPlanner {
        -static final String DEFAULT_COLLECTION
        -BGArenaPlanner()
        +main(String[] args)
    }

    class IPlanner {
        <<interface>>
        +filter(String filter) : Stream<BoardGame>
        +filter(String filter, GameData sortOn) : Stream<BoardGame>
        +filter(String filter, GameData sortOn, boolean ascending) : Stream<BoardGame>
        +reset()
    }
    
    class IGameList {
        <<interface>>
        +addToList(String gameName, Stream<BoardGame> games)
        +removeFromList(String gameName)
        +clear()
        +count() : int
        +getGameNames() : List<String>
        +saveGame(String fileName)
        +static final String ADD_ALL
    }

    class GamesLoader {
        <<static>>
        +loadGamesFile(String fileName) : Set<BoardGame>
    }

    class ConsoleApp {
        -static final Scanner IN
        -static final String DEFAULT_FILENAME
        -static final Random RND
        -Scanner current
        -IGameList gameList
        -IPlanner planner
        +ConsoleApp(IGameList gameList, IPlanner planner)
        +start()
        -randomNumber()
        -processHelp()
        -processFilter()
        -printFilterStream(Stream<BoardGame> games, GameData sortON)
        -processListCommands()
        -printCurrentList()
        -nextCommand()
        -remainder()
        -getInput(String format, Object... args)
        -printOutput(String format, Object... output)
    }

    class ConsoleText {
        WELCOME, HELP, INVALID, GOODBYE, PROMPT, NO_FILTER,
        NO_GAMES_LIST, FILTERED_CLEAR, LIST_HELP, FILTER_HELP,
        INVALID_LIST, EASTER_EGG, CMD_EASTER_EGG, CMD_EXIT,
        CMD_HELP, CMD_QUESTION, CMD_FILTER, CMD_LIST, CMD_SHOW,
        CMD_ADD, CMD_REMOVE, CMD_CLEAR, CMD_SAVE, CMD_OPTION_ALL,
        CMD_SORT_OPTION, CMD_SORT_OPTION_DIRECTION_ASC,
        CMD_SORT_OPTION_DIRECTION_DESC
        -static final Properties CTEXT
        +toString() : String
        +fromString(String str) : ConsoleText
    }

    class BoardGame {
        -String name
        -int id
        -int minPlayers
        -int maxPlayers
        -int maxPlayTime
        -int minPlayTime
        -double difficulty
        -int rank
        -double averageRating
        -int yearPublished
        +BoardGame(String name, int id, int minPlayers,\nint maxPlayers, int minPlayTime, int maxPlayTime,\ndouble difficulty, int rank, double averageRating,\nint yearPublished)
        +getName() : String
        +getId() : int
        +getMinPlayers() : int
        +getMaxPlayers() : int
        +getMaxPlayTime() : int
        +getMinPlayTime() : int
        +getDifficulty() : double
        +getRank() : int
        +getRating() : double
        +getYearPublished() : int
        +toStringWithInfo(GameData col) : String
        +toString() : String
        +equals(Object obj) : boolean
        +hashCode() : int
    }

    class GameData {
        NAME, ID, RATING, DIFFICULTY, RANK, MIN_PLAYERS, MAX_PLAYERS,
        MIN_TIME, MAX_TIME, YEAR
        -static final String columnName
        +getColumnName() : String
        +fromColumnName(String columnName) : GameData
        +fromString(String name) : GameData
    }

    class Operations {
        EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_THAN_EQUALS,
        LESS_THAN_EQUALS, CONTAINS
        -static final String operator
        +getOperator() : String
        +fromOperator(String operator) : Operations
        +getOperatorFromStr(String str) : Operations
    }
```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 

```mermaid
classDiagram
    Planner --|> IPlanner
    Planner ..> BoardGame
    Planner ..> GameData
    Planner ..> Operations

    GameList --|> IGameList
    GameList ..> BoardGame

    class IPlanner {
        <<interface>>
        +filter(String filter) : Stream<BoardGame>
        +filter(String filter, GameData sortOn) : Stream<BoardGame>
        +filter(String filter, GameData sortOn, boolean ascending) : Stream<BoardGame>
        +reset()
    }

    class IGameList {
        <<interface>>
        +addToList(String gameName, Stream<BoardGame> games)
        +removeFromList(String gameName)
        +clear()
        +count() : int
        +getGameNames() : List<String>
        +saveGame(String fileName)
        +static final String ADD_ALL
    }

    class BoardGame {
        -String name
        -int id
        -int minPlayers
        -int maxPlayers
        -int maxPlayTime
        -int minPlayTime
        -double difficulty
        -int rank
        -double averageRating
        -int yearPublished
        +BoardGame(String name, int id, int minPlayers,\nint maxPlayers, int minPlayTime, int maxPlayTime,\ndouble difficulty, int rank, double averageRating,\nint yearPublished)
        +getName() : String
        +getId() : int
        +getMinPlayers() : int
        +getMaxPlayers() : int
        +getMaxPlayTime() : int
        +getMinPlayTime() : int
        +getDifficulty() : double
        +getRank() : int
        +getRating() : double
        +getYearPublished() : int
        +toStringWithInfo(GameData col) : String
        +toString() : String
        +equals(Object obj) : boolean
        +hashCode() : int
    }

    class GameData {
        NAME, ID, RATING, DIFFICULTY, RANK, MIN_PLAYERS, MAX_PLAYERS,
        MIN_TIME, MAX_TIME, YEAR
        -static final String columnName
        +getColumnName() : String
        +fromColumnName(String columnName) : GameData
        +fromString(String name) : GameData
    }

    class Operations {
        EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_THAN_EQUALS,
        LESS_THAN_EQUALS, CONTAINS
        -static final String operator
        +getOperator() : String
        +fromOperator(String operator) : Operations
        +getOperatorFromStr(String str) : Operations
    }
```


## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. BoardGame Class
* Test Constructor: verifies that all fields are correctly initialized
* Test Getters: verifies that each getter method returns the correct value for its corresponding field
* Test toStringWithInfo: verifies that the method returns the expected string representation based on the GameData column
* Test toString: verifies that the method returns the correct string representation of the object
* Test equals: verifies that the method correctly determines equality based on the specified fields
* Test hashCode: verifies that the method returns a consistent hash code based on the specified fields

2. GamesLoader Class
* testLoadInvalidFile(): test loading from an invalid file path, assert games is an empty set
* testLoadEmptyFile(): test loading an empty csv file, assert games is an empty set
* testLoadGamesFile(): test loading from a correct file path, assert games is non-empty and the
BoardGame object has its attributes set correctly

3. Planner Class
* Test filter(String filter): verify that the method filters the stream correctly
* Test filter(String filter, GameData sortOn)
* Test filter(String filter, GameData sortOn, boolean ascending)
* Test reset: verify that the reset method clears all filters

4. GameList Class
* Test addToList: verify that the method adds a game correctly
* Test removeFromList: verify that the method removes a game correctly
* Test clear: verify that the method empties the game list
* Test count: verify that the method returns the correct number of games in the list
* Test getGameNames: verify that the method returns the list of game names correctly
* Test saveGame: verify that the method can correctly save the list of games to a file

5. ConsoleApp Class
* Test processHelp: verify that the correct message is displayed
* Test processFilter: verify that correct filtering results are achieved
* Test processListCommands: verify that the list commands (show, clear, add, remove, and save) work correctly
* Test start: verify that the program starts correctly

## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.


```mermaid
classDiagram
    BGArenaPlanner ..> IPlanner
    BGArenaPlanner ..> IGameList
    BGArenaPlanner ..> GamesLoader
    BGArenaPlanner --> ConsoleApp

    IPlanner ..> BoardGame
    IPlanner ..> GameData
    IGameList ..> BoardGame
    
    GamesLoader ..> BoardGame
    
    BoardGame ..> GameData
    
    ConsoleApp ..> IPlanner
    ConsoleApp ..> IGameList
    ConsoleApp ..> ConsoleText
    ConsoleApp ..> BoardGame
    ConsoleApp ..> GameData

    Planner --|> IPlanner
    Planner ..> BoardGame
    Planner ..> GameData
    Planner ..> Operations

    GameList --|> IGameList
    GameList ..> BoardGame

    class BGArenaPlanner {
        -static final String DEFAULT_COLLECTION
        -BGArenaPlanner()
        +main(String[] args)
    }

    class GamesLoader {
        <<static>>
        +loadGamesFile(String fileName) : Set<BoardGame>
    }

    class ConsoleApp {
        -static final Scanner IN
        -static final String DEFAULT_FILENAME
        -static final Random RND
        -Scanner current
        -IGameList gameList
        -IPlanner planner
        +ConsoleApp(IGameList gameList, IPlanner planner)
        +start()
        -randomNumber()
        -processHelp()
        -processFilter()
        -printFilterStream(Stream<BoardGame> games, GameData sortON)
        -processListCommands()
        -printCurrentList()
        -nextCommand()
        -remainder()
        -getInput(String format, Object... args)
        -printOutput(String format, Object... output)
    }

    class ConsoleText {
        WELCOME, HELP, INVALID, GOODBYE, PROMPT, NO_FILTER,
        NO_GAMES_LIST, FILTERED_CLEAR, LIST_HELP, FILTER_HELP,
        INVALID_LIST, EASTER_EGG, CMD_EASTER_EGG, CMD_EXIT,
        CMD_HELP, CMD_QUESTION, CMD_FILTER, CMD_LIST, CMD_SHOW,
        CMD_ADD, CMD_REMOVE, CMD_CLEAR, CMD_SAVE, CMD_OPTION_ALL,
        CMD_SORT_OPTION, CMD_SORT_OPTION_DIRECTION_ASC,
        CMD_SORT_OPTION_DIRECTION_DESC
        -static final Properties CTEXT
        +toString() : String
        +fromString(String str) : ConsoleText
    }

    class BoardGame {
        -String name
        -int id
        -int minPlayers
        -int maxPlayers
        -int maxPlayTime
        -int minPlayTime
        -double difficulty
        -int rank
        -double averageRating
        -int yearPublished
        +BoardGame(String name, int id, int minPlayers,\nint maxPlayers, int minPlayTime, int maxPlayTime,\ndouble difficulty, int rank, double averageRating,\nint yearPublished)
        +getName() : String
        +getId() : int
        +getMinPlayers() : int
        +getMaxPlayers() : int
        +getMaxPlayTime() : int
        +getMinPlayTime() : int
        +getDifficulty() : double
        +getRank() : int
        +getRating() : double
        +getYearPublished() : int
        +toStringWithInfo(GameData col) : String
        +toString() : String
        +equals(Object obj) : boolean
        +hashCode() : int
    }

    class GameData {
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        - static final String columnName
        + getColumnName() : String
        + fromColumnName(String columnName) : GameData
        + fromString(String name) : GameData
    }

    class Operations {
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        - static final String operator
        + getOperator() : String
        + fromOperator(String operator) : Operations
        + getOperatorFromStr(String str) : Operations
    }
    
    class IPlanner {
        <<interface>>
        + filter(String filter) : Stream<BoardGame>
        + filter(String filter, GameData sortOn) : Stream<BoardGame>
        + filter(String filter, GameData sortOn, boolean ascending) : Stream<BoardGame>
        + reset() : void
    }
    
    class Planner {
        - Set<BoardGame> games
        - Set<BoardGame> initialGames
        + Planner(Set<BoardGame> games)
        + filter(String filter) : Stream<BoardGame>
        + filter(String filter, GameData sortOn) : Stream<BoardGame>
        + filter(String filter, GameData sortOn, boolean ascending) : Stream<BoardGame>
        - compare(BoardGame g1, BoardGame g2, GameData sortOn) : int
        - filterStringCompare(Stream<BoardGame> stream, GameData col, String value, Comparator<String> comparator, Operations operator) : Stream<BoardGame>
        - filterStringContains(Stream<BoardGame> stream, GameData col, String value, BiPredicate<String, String> predicate) : Stream<BoardGame>
        - filterNumericDouble(Stream<BoardGame> stream, GameData col, String value, Comparator<Double> comparator, Operations operator) : Stream<BoardGame>
        - filterNumericInt(Stream<BoardGame> stream, GameData col, String value, Comparator<Integer> comparator, Operations operator) : Stream<BoardGame>
        + reset() : void
    }
    
    class IGameList {
        <<interface>>
        + addToList(String gameName, Stream<BoardGame>) : void
        + removeFromList(String gameName) : void
        + clear() : void
        + count() : int
        + getGameNames() : List<String>
        + saveGame(String fileName) : void
        + static final String ADD_ALL
    }
    
    class GameList {
        - List<BoardGame> games
        + GameList()
        + getGameNames() : List<String>
        + addToList(String str, Stream<BoardGame> filtered) : void
        + removeFromList(String str) : void
        + clear() : void
        + saveGame(String filename) : void
        + count() : int
        + getGames() : List<BoardGame>
    }
```


## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

The major change from the initial design is how the filtering based on various types data is implemented. The filter method in the Planner class initially contains a lot 
of repeated codes, whereas in the final version is much cleaner. In the final version, the filtering functionality is split into four parts, filter string by comparison, 
filter string by containing, filter numeric by double value, and filter numeric by integer value. These four parts of functionality are abstracted as private helper functions 
and called in appropriate cases in the filter method.

Another change is how the progressive filtering and reset functionalities are implemented. Initially, the program failed to perform filtering progressively. This was because 
the initial program did not update the games set with the filteredStream after filtering. In the final design, the games set of the Planner class is correctly updated after 
filtering. Another attribute, initialGames, is added to the Planner class for resetting the games set to original when the filters need to be cleared.