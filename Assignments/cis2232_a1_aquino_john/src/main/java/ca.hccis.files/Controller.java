package ca.hccis.files;

import ca.hccis.files.entity.VolleyballMatch;
import ca.hccis.util.CisUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.Strictness;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Console menu and JSON file storage for the Volleyball Score App.
 * Adapted from the CIS2232 class file I/O reference.
 * @author John Raicent Aquino
 * @since 20261008
 */
public class Controller {
    public static final String PATH_NAME = "c:\\cis2232\\data_aquino_john.json";
    public static final String MENU = "A) Add\nV) View\nX) Exit";

    // The default is the required Windows path. The property permits isolated testing.
    private static final Path DATA_FILE = Paths.get(
            System.getProperty("cis2232.dataFile", PATH_NAME)).toAbsolutePath();
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().setStrictness(Strictness.STRICT).create();
    private static final List<VolleyballMatch> matches = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Volleyball Score App - John Raicent Aquino");
        try {
            initialize();
        } catch (IOException | JsonParseException | IllegalArgumentException e) {
            System.out.println("Unable to load data: " + e.getMessage());
            System.out.println("Program stopped. Existing data has not been overwritten.");
            return;
        }

        try {
            String option;
            do {
                option = CisUtility.getInputString(MENU).trim().toUpperCase(Locale.ROOT);
                switch (option) {
                    case "A":
                        add();
                        break;
                    case "V":
                        viewAll();
                        break;
                    case "X":
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose A, V, or X.");
                }
            } while (!option.equals("X"));
        } catch (NoSuchElementException e) {
            System.out.println("Input ended. Previously saved records are retained.");
        }
    }

    /** Creates the required directory and loads previously saved matches. */
    public static void initialize() throws IOException {
        Files.createDirectories(DATA_FILE.getParent());
        if (Files.exists(DATA_FILE)) {
            readAll();
        } else {
            writeAll();
        }
        System.out.println("Loaded " + matches.size() + " match record(s).");
        System.out.println("Data file: " + DATA_FILE);
    }

    /** Collects and validates one match, then immediately saves it. */
    public static void add() {
        VolleyballMatch match = new VolleyballMatch();
        System.out.println("-- Add Completed Volleyball Match --");
        match.getInformation();
        int maxId = 0;
        for (VolleyballMatch existing : matches) {
            maxId = Math.max(maxId, existing.getId());
        }
        if (maxId == Integer.MAX_VALUE) {
            System.out.println("Cannot add a record: no match IDs remain available.");
            return;
        }
        match.setId(maxId + 1);
        matches.add(match);
        try {
            writeAll();
            System.out.println("Match #" + match.getId() + " saved successfully.");
        } catch (IOException e) {
            matches.remove(match);
            System.out.println("Match was not saved: " + e.getMessage());
        }
    }

    /** Displays all matches restored at startup or added during this session. */
    public static void viewAll() {
        System.out.println("-- Saved Volleyball Matches --");
        if (matches.isEmpty()) {
            System.out.println("No matches saved yet. Choose A to add a match.");
        } else {
            for (VolleyballMatch match : matches) {
                System.out.println(match);
                System.out.println();
            }
        }
    }

    /** Stores the entire list as one valid JSON array, replacing only after writing. */
    public static void writeAll() throws IOException {
        Path temporary = Files.createTempFile(DATA_FILE.getParent(), "matches-", ".tmp");
        try {
            try (Writer writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                GSON.toJson(matches, writer);
            }
            try {
                Files.move(temporary, DATA_FILE, StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, DATA_FILE, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    /** Reads JSON and rejects damaged records before accepting the saved list. */
    public static void readAll() throws IOException {
        VolleyballMatch[] loaded;
        try (Reader reader = Files.newBufferedReader(DATA_FILE, StandardCharsets.UTF_8)) {
            loaded = GSON.fromJson(reader, VolleyballMatch[].class);
        }
        if (loaded == null) {
            throw new IllegalArgumentException("Expected a JSON array; use [] for an empty file.");
        }
        Set<Integer> ids = new HashSet<>();
        for (VolleyballMatch match : loaded) {
            if (match == null || match.getId() <= 0 || !ids.add(match.getId())) {
                throw new IllegalArgumentException("Saved matches require unique positive IDs.");
            }
            match.validate();
        }
        matches.clear();
        matches.addAll(Arrays.asList(loaded));
    }
}
