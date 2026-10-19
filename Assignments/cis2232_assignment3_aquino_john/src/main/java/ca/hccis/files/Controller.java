package ca.hccis.files;

import ca.hccis.files.entity.VolleyballMatch;
import ca.hccis.files.threads.Thread1Console;
import ca.hccis.files.threads.Thread2GUI;
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
import java.util.Set;

/**
 * Controls the overall flow of the program.
 * @author John Aquino
 * @since 20261011
 */
public class Controller {

    public static final String PATH_NAME =
            "c:\\cis2232\\data_aquino_john.json";

    private static final Path DATA_FILE = Paths.get(
                    System.getProperty("cis2232.dataFile", PATH_NAME))
            .toAbsolutePath();

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setStrictness(Strictness.STRICT)
            .create();

    // Both threads use this same list and accumulator.
    private static final List<VolleyballMatch> matches =
            new ArrayList<>();

    private static int totalMatches;

    public static void main(String[] args) {

        // Load the shared data before starting either thread.
        try {
            initialize();
        } catch (IOException | JsonParseException
                 | IllegalArgumentException e) {

            System.out.println("Unable to load data: " + e.getMessage());
            System.out.println(
                    "Program stopped. Existing data has not been overwritten.");
            return;
        }

        Thread1Console console = new Thread1Console();

        Thread gui = new Thread(
                new Thread2GUI(), "Volleyball-JOptionPane");

        // Start both workers before waiting for either to finish.
        console.start();
        gui.start();

        try {
            console.join();
            gui.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Creates the directory and loads previously saved matches.
     */
    public static synchronized void initialize() throws IOException {

        Files.createDirectories(DATA_FILE.getParent());

        if (Files.exists(DATA_FILE)) {
            readAll();
        } else {
            writeAll();
        }

        totalMatches = matches.size();
    }

    /**
     * Saves a match and updates the combined accumulator.
     * Only one thread can perform this operation at a time.
     *
     * @param match completed match to save
     * @return combined number of saved matches
     * @throws IOException if the file cannot be saved
     */
    public static synchronized int add(VolleyballMatch match)
            throws IOException {

        match.validate();

        int maxId = 0;

        for (VolleyballMatch existing : matches) {
            maxId = Math.max(maxId, existing.getId());
        }

        if (maxId == Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "No match IDs remain available.");
        }

        int originalId = match.getId();

        match.setId(maxId + 1);
        matches.add(match);

        try {
            writeAll();

            // Increase the accumulator only after a successful save.
            totalMatches = matches.size();

            return totalMatches;

        } catch (IOException e) {

            // Remove the unsaved entry if writing fails.
            matches.remove(match);
            match.setId(originalId);

            throw e;
        }
    }

    /**
     * Returns the shared matches for either interface to display.
     */
    public static synchronized String viewAll() {

        StringBuilder report =
                new StringBuilder("-- Saved Volleyball Matches --\n");

        report.append("Combined saved matches: ")
                .append(totalMatches)
                .append("\n\n");

        if (matches.isEmpty()) {
            report.append(
                    "No matches saved yet. Choose A to add a match.");
        }

        for (VolleyballMatch match : matches) {
            report.append(match).append("\n\n");
        }

        return report.toString();
    }

    /**
     * Returns the accumulated number of saved matches.
     */
    public static synchronized int getTotalMatches() {
        return totalMatches;
    }

    /**
     * Returns the shared data-file location.
     */
    public static Path getDataFile() {
        return DATA_FILE;
    }

    /**
     * Writes the combined list as one JSON array.
     */
    private static void writeAll() throws IOException {

        Path temporary = Files.createTempFile(
                DATA_FILE.getParent(), "matches-", ".tmp");

        try {
            try (Writer writer = Files.newBufferedWriter(
                    temporary, StandardCharsets.UTF_8)) {

                GSON.toJson(matches, writer);
            }

            try {
                Files.move(
                        temporary,
                        DATA_FILE,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);

            } catch (AtomicMoveNotSupportedException e) {

                Files.move(
                        temporary,
                        DATA_FILE,
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    /**
     * Reads and validates previously saved matches.
     */
    private static void readAll() throws IOException {

        VolleyballMatch[] loaded;

        try (Reader reader = Files.newBufferedReader(
                DATA_FILE, StandardCharsets.UTF_8)) {

            loaded = GSON.fromJson(reader, VolleyballMatch[].class);
        }

        if (loaded == null) {
            throw new IllegalArgumentException(
                    "Expected a JSON array; use [] for an empty file.");
        }

        Set<Integer> ids = new HashSet<>();

        for (VolleyballMatch match : loaded) {

            if (match == null
                    || match.getId() <= 0
                    || !ids.add(match.getId())) {

                throw new IllegalArgumentException(
                        "Saved matches require unique positive IDs.");
            }

            match.validate();
        }

        matches.clear();
        matches.addAll(Arrays.asList(loaded));
    }
}