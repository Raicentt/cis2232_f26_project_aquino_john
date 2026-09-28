package ca.hccis.files;

import ca.hccis.files.entity.VolleyballMatch;
import ca.hccis.files.util.CisUtility;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


/**
 *
 *  Main Controller of the Volleyball Match Scoring App
 *
 *  From: Aidan Chappelle
 *  Developed by: John Aquino
 *  Managed by: Connor Chappelle
 *  Date: 9-25-2026
 *
 */

public class Controller {

    public static final String MENU =
            "A) Add" + System.lineSeparator()
                    + "V) View" + System.lineSeparator()
                    + "X) Exit" + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Invalid menu option.";
    public static final String MESSAGE_EXIT = "Goodbye!";

    private static HashMap<Integer, VolleyballMatch> matchMap = new HashMap<>();
    private static Gson gson = new Gson();

    // Required directory and filename
    public static final String DIRECTORY_NAME = "C:\\cis2232";
    public static final String PATH_NAME =
            "C:\\cis2232\\data_aquino_john.json";

    public static void main(String[] args) {

        initialize();

        String menuOption;

        do {

            menuOption = CisUtility.getInputString(MENU);

            switch (menuOption.toUpperCase()) {

                case "A":
                    add();
                    break;

                case "V":
                    viewAll();
                    break;

                case "X":
                    System.out.println(MESSAGE_EXIT);
                    break;

                default:
                    System.out.println(MESSAGE_ERROR);
                    break;
            }

        } while (!menuOption.equalsIgnoreCase("X"));
    }

    /**
     * Add a new volleyball match.
     */
    public static void add() {

        System.out.println();
        System.out.println("-- Add Volleyball Match --");

        VolleyballMatch newMatch = new VolleyballMatch();

        // Automatically assign the next ID.
        newMatch.setId(getNextId());

        // Ask user for match information.
        newMatch.getInformation();

        // Add match to HashMap.
        matchMap.put(newMatch.getId(), newMatch);

        // Save all records to JSON file.
        writeAll();

        System.out.println();
        System.out.println("Match successfully added.");
    }

    /**
     * View all saved volleyball matches.
     */
    public static void viewAll() {

        // Read latest information from file.
        readAll();

        System.out.println();
        System.out.println("-- Volleyball Matches --");

        if (matchMap.isEmpty()) {

            System.out.println("No volleyball matches found.");

        } else {

            for (VolleyballMatch current : matchMap.values()) {

                System.out.println(current);
                System.out.println("----------------------------------");
            }
        }
    }

    /**
     * Save all matches to the JSON file.
     */
    public static void writeAll() {

        try {

            FileWriter writer = new FileWriter(PATH_NAME, false);

            for (VolleyballMatch current : matchMap.values()) {

                writer.append(gson.toJson(current));
                writer.append(System.lineSeparator());
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error writing to file.");
            e.printStackTrace();
        }
    }

    /**
     * Read all matches from the JSON file.
     */
    public static void readAll() {

        try {

            Path path = Paths.get(PATH_NAME);

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);

            matchMap.clear();

            for (String line : lines) {

                if (!line.isBlank()) {

                    VolleyballMatch matchFromJson =
                            gson.fromJson(line, VolleyballMatch.class);

                    matchMap.put(
                            matchFromJson.getId(),
                            matchFromJson
                    );
                }
            }

        } catch (IOException e) {

            System.out.println("Error reading from file.");
            e.printStackTrace();
        }
    }

    /**
     * Create directory/file if needed and load saved data.
     */
    public static void initialize() {

        try {

            Path directory = Paths.get(DIRECTORY_NAME);
            Path file = Paths.get(PATH_NAME);

            // Create C:\cis2232 if it does not exist.
            if (!Files.exists(directory)) {

                Files.createDirectories(directory);

                System.out.println(
                        "Directory created: " + DIRECTORY_NAME
                );
            }

            // Create JSON file if it does not exist.
            if (!Files.exists(file)) {

                Files.createFile(file);

                System.out.println(
                        "Data file created: " + PATH_NAME
                );

            } else {

                // Load previous data.
                readAll();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error initializing data file."
            );

            e.printStackTrace();
        }
    }

    /**
     * Determine next available ID.
     */
    public static int getNextId() {

        int highestId = 0;

        for (Integer id : matchMap.keySet()) {

            if (id > highestId) {
                highestId = id;
            }
        }

        return highestId + 1;
    }
}
