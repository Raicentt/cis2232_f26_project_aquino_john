package ca.hccis.files.threads;

import ca.hccis.files.Controller;
import ca.hccis.files.entity.VolleyballMatch;
import ca.hccis.util.CisUtility;
import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * All input and output stays in the console.
 * @author John Aquino
 * @since 20261011
 */
public class Thread1Console extends Thread {
    private static final String MENU = "A) Add\nV) View\nX) Exit";

    public Thread1Console() {
        super("Volleyball-Console");
    }

    @Override
    public void run() {
        System.out.println("Volleyball Score App - Console");
        System.out.println("Data file: " + Controller.getDataFile());
        try {
            String option;
            do {
                System.out.println("Combined saved matches: " + Controller.getTotalMatches());
                option = CisUtility.getInputString(MENU).trim().toUpperCase(Locale.ROOT);
                switch (option) {
                    case "A":
                        addMatch();
                        break;
                    case "V":
                        System.out.println(Controller.viewAll());
                        break;
                    case "X":
                        System.out.println("Console finished. The GUI can continue.");
                        break;
                    default:
                        System.out.println("Invalid option. Choose A, V, or X.");
                }
            } while (!option.equals("X"));
        } catch (NoSuchElementException e) {
            System.out.println("Console input ended. Saved matches are retained.");
        }
    }

    private void addMatch() {
        VolleyballMatch match = new VolleyballMatch();
        match.getInformation();
        try {
            int total = Controller.add(match);
            System.out.println("Match #" + match.getId() + " saved successfully.");
            System.out.println("Combined saved matches: " + total);
            System.out.println(match);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Match was not saved: " + e.getMessage());
        }
    }
}
