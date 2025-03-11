# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   
* The difference is that == compares memory addresses whereas .equals compares the contents of two objects.
   
   ```java
   // your code here
  public class BoardGame {
        private String name;
        private int id;
  
        public BoardGame(String name, int id) {
            this.name = name;
            this.id = id;
        }
  
        public String getName() {
            return name;
        }
  
        public int getId() {
            return id;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            BoardGame game = (BoardGame) obj;
            return name.equals(game.getName()) && id == game.getId();
        }
  
        public static void main(String[] args) {
            BoardGame g1 = new BoardGame("13 Clues", 208766);
            BoardGame g2 = new BoardGame("13 Clues", 208766);
            BoardGame g3 = new BoardGame("15 Days", 298619);
  
            System.out.println(g1 == g2); // false
            System.out.println(g1 == g3); // false
  
            System.out.println(g1.equals(g2)); // true
            System.out.println(g1.equals(g3)); // false
        }
  }
   
   ```




2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 

   * Let gameNames be a list of strings. The following line of code can be used to sort the list in a case-insensitive manner:
     * gameNames.sort(String.CASE_INSENSITIVE_ORDER);

   String.CASE_INSENSITIVE_ORDER is a predefined comparator. The CaseInsensitiveComparator class implements the Comparator interface and overrides the compare method to compare two strings 
   in a case-insensitive manner.


3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 

* If, for example, the filter string is "minPlayers >= 4", the operator would be ">=", which would contain ">". In this case, if we write the code in an order where the ">" branch precedes 
the ">=" branch, we would get an Operations enum constant of GREATER_THAN instead of the correct GREATER_THAN_EQUALS.

4. What is the difference between a List and a Set in Java? When would you use one over the other? 

* A Set does not allow duplicates, while a List does. In a case where you might want to maintain a collection of objects where duplicates are not allowed, you may want to use a set.
* A List maintains the order in which elements are inserted, and you can access the elements by index. A Set does not maintain any order of elements (except LinkedHashSet and TreeSet).


5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 

* A Map is a collection that maps keys to values.
* In GamesLoader, a columnMap that maps a GameData enum constant (representing the column name) to an Integer (the index in the CSV file) is used. This columnMap allows us to associate the GameData enum 
   with the column's index in the CSV file. The keys in the map is unique and each key maps to only one value. The lookup time complexity if O(1). Using the columnMap allows us to easily access the values
   in the CSV file through the GameData enum constants.

6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?


* An enum is a special data type that represents a fixed set of constants. The GameData enum constants such as NAME and RANK are instances that are created when the class is loaded.
* In our application, the GameData enum is used to represent the columns in the CSV file. The use of this enum class helps centralize the definition of column names, making the code easier to maintain and update. 
   Using the enum constants instead of the string literals helps reduce the risk of typos and makes the usage of column names more consistent throughout the application.




7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
    if (ct == CMD_QUESTION || ct == CMD_HELP) {
         processHelp();
    } else if (ct == INVALID) {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    } else {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
// your consoles output here
> Task :student.BGArenaPlanner.main()

    *******Bienvenue dans le Planificateur de BoardGame Arena.*******

    Un outil pour aider les gens ࠰lanifier les jeux auxquels ils veulent jouer sur Board Game Arena.

    Pour commencer, entrez votre première commande ci-dessous, ou tapez ? ou help pour voir les options de commande.
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 

#### Importance of Localization

* Localization allows the software product to become accessible to those who speak a different language.
* It improves the user experience by allowing users to easily navigate the software in their own languages.
* It improves the image of the brand by demonstrating care and capability of the company to the customers.
* It helps with the regulatory processes in different regions or countries.
* The company may be able to gain financially by expanding the customer base to different regions of the world.

#### 10 Most Spoken Languages in the World
The following are the 10 most spoken languages in the world today[1].
* English (1,456 million)
* Mandarin (1,138 million)
* Hindi (610 million)
* Spanish (559 million)
* French (310 million)
* Modern Standard Arabic (274 million)
* Bengali (273 million)
* Portuguese (264 million)
* Russian (255 million)
* Urdu (232 million)

#### Global Internet Penetration Rate
The following are the internet penetration rates of regions of the world today[2].
* Northern Europe (97.9%)
* Western Europe (95.1%)
* Northern America (93.3%)
* Southern Europe (91.6%)
* Eastern Europe (90.6%)
* Southern America (83.2%)
* Central Asia (80.8%)
* Central America (79.2%)
* Eastern Asia (78.5%)
* South-Eastern Asia (78.2%)
* Oceania (77.5%)
* Southern Africa (77%)
* Western Asia (75.9%)
* Northern Africa (73%)
* Caribbean (69.8%)
* Southern Asia (53.8%)
* Western Africa (42.5%)
* Middle Africa (33.6%)
* Eastern Africa (28.5%)

Most of the regions where people are speaking the most popular languages do have a decent internet coverage. 
The only region where people are speaking popular languages but may not have as good of an internet coverage may be Southern Asia.
However, given the large base of population in that region, it may still make business sense to develop localized solutions for that region.
Another region that generally has a lower than average internet penetration is Africa. But development of the internet infrastructure may pick up quickly in the near future.

#### Ways to Ensure Flexibility in Localization
* Store the texts in external files such as a properties file
* Use UTF-8 encoding
* Make sure the user interface layout is flexibly designed to adapt to different languages

#### References

[1]: The most spoken languages in the world in 2024. https://www.berlitz.com/blog/most-spoken-languages-world. Accessed: 2025-03-08.

[2]: Global internet penetration rate as of February 2025, by region. https://www.statista.com/statistics/269329/penetration-rate-of-the-internet-by-region/. Accessed: 2025-03-08.