package ca.hccis.files.threads;

import ca.hccis.files.Controller;
import ca.hccis.files.entity.VolleyballMatch;

import javax.swing.*;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CancellationException;

/**
 * Independent JOptionPane menu.
 * @author John Aquino
 * @since 20261011
 */
public class Thread2GUI implements Runnable {
    private static final String MENU = "A) Add\nV) View\nX) Exit";

    @Override
    public void run() {
        String option;
        do {
            String input = JOptionPane.showInputDialog(null,
                    "Combined saved matches: " + Controller.getTotalMatches() + "\n" + MENU,
                    "Volleyball Score App - GUI", JOptionPane.QUESTION_MESSAGE);
            // Closing or cancelling this menu ends only the GUI worker.
            if (input == null) {
                return;
            }
            option = input.trim().toUpperCase(Locale.ROOT);
            switch (option) {
                case "A":
                    addMatch();
                    break;

                // I noticed that if there are more than 5 matches, everything will not fit in my screen,
                // so I decided to put scrollbar for convenience (YouTube and Gemini assisted me in this part)
                case "V":
                    JTextArea textArea = new JTextArea(Controller.viewAll(), 20, 50);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setCaretPosition(0);

                    JScrollPane scrollPane = new JScrollPane(textArea);

                    JOptionPane.showMessageDialog(
                            null,
                            scrollPane,
                            "Saved Volleyball Matches",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;


                case "X":
                    JOptionPane.showMessageDialog(null, "GUI finished. The console can continue.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Choose A, V, or X.");
            }
        } while (!option.equals("X"));
    }

    private void addMatch() {
        VolleyballMatch match = new VolleyballMatch();
        try {
            match.getInformation(true);
            int total = Controller.add(match);
            JOptionPane.showMessageDialog(null,
                    "Match #" + match.getId() + " saved successfully.\n"
                    + "Combined saved matches: " + total + "\n\n" + match);
        } catch (CancellationException e) {
            JOptionPane.showMessageDialog(null, "Match cancelled. No record was saved.");
        } catch (IOException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Match was not saved: " + e.getMessage());
        }
    }
}
