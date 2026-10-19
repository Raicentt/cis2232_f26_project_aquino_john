package ca.hccis.files.entity;

import ca.hccis.files.bo.VolleyballMatchBO;
import ca.hccis.util.CisUtility;
import javax.swing.JOptionPane;
import java.util.Locale;
import java.util.concurrent.CancellationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Contains all the variables, validations and other data needed for this project
 * @author John Aquino
 * @since 20261011
 */

public class VolleyballMatch {

        private int id;
        private String teamName;
        private String opponentName;
        private String matchDate;
        private int setsPlayed;
        private int teamSetsWon;
        private int opponentSetsWon;
        private int teamPointsScored;
        private int opponentPointsScored;
        private int teamServiceAces;
        private int opponentServiceAces;

        public VolleyballMatch() {
        }

        /**
         * Collects the ten match fields from the console.
         */
        public void getInformation() {
            getInformation(false);
        }

        /** Collects the same fields using the calling thread's interface. */
        public void getInformation(boolean isGUI) {
            teamName = readRequiredText(isGUI, "Team name");
            opponentName = readRequiredText(isGUI, "Opponent name");

            while (true) {
                matchDate = readInput(isGUI, 
                        "Match date (yyyy-mm-dd)").trim();

                try {
                    validateDate();
                    break;
                } catch (IllegalArgumentException e) {
                    display(isGUI, e.getMessage());
                }
            }

            while (true) {
                setsPlayed = readWholeNumber(isGUI, "Sets played (3-5)", 3, 5);
                teamSetsWon = readWholeNumber(isGUI, "Team sets won (0-3)", 0, 3);
                opponentSetsWon = readWholeNumber(isGUI,
                        "Opponent sets won (0-3)", 0, 3);

                try {
                    validateSets();
                    break;
                } catch (IllegalArgumentException e) {
                    display(isGUI, 
                            e.getMessage()
                                    + " Please re-enter the set information.");
                }
            }

            teamPointsScored = readWholeNumber(isGUI,
                    "Team total points scored", 1, Integer.MAX_VALUE);

            opponentPointsScored = readWholeNumber(isGUI,
                    "Opponent total points scored", 1, Integer.MAX_VALUE);

            teamServiceAces = readWholeNumber(isGUI,
                    "Team service aces", 0, teamPointsScored);

            opponentServiceAces = readWholeNumber(isGUI,
                    "Opponent service aces", 0, opponentPointsScored);

            validate();
        }

        private static String readRequiredText(boolean isGUI, String prompt) {
            while (true) {
                String value = readInput(isGUI, prompt).trim();

                if (!value.isEmpty()) {
                    return value;
                }

                display(isGUI, "This field is required.");
            }
        }

        private static int readWholeNumber(boolean isGUI, String prompt, int min, int max) {
            while (true) {
                String value = readInput(isGUI, prompt).trim();

                try {
                    int number = Integer.parseInt(value);

                    if (number >= min && number <= max) {
                        return number;
                    }
                } catch (NumberFormatException e) {
                    // Retry invalid text, decimals, or overflowing numbers.
                }

                display(isGUI, 
                        "Enter a whole number from " + min + " to " + max + ".");
            }
        }

        /** false uses the console; true uses JOptionPane for this entry only. */
        private static String readInput(boolean isGUI, String prompt) {
            if (!isGUI) {
                return CisUtility.getInputString(prompt);
            }
            String value = JOptionPane.showInputDialog(null, prompt,
                    "Volleyball Score App - GUI", JOptionPane.QUESTION_MESSAGE);
            if (value == null) {
                throw new CancellationException("Match entry cancelled.");
            }
            return value;
        }

        private static void display(boolean isGUI, String message) {
            if (isGUI) {
                JOptionPane.showMessageDialog(null, message);
            } else {
                System.out.println(message);
            }
        }

        private void validateDate() {
            if (matchDate == null
                    || !matchDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                throw new IllegalArgumentException(
                        "Enter a valid date using yyyy-mm-dd.");
            }

            try {
                LocalDate.parse(matchDate);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "That date does not exist. Use yyyy-mm-dd.");
            }
        }

        private void validateSets() {
            if (setsPlayed < 3 || setsPlayed > 5
                    || teamSetsWon < 0 || teamSetsWon > 3
                    || opponentSetsWon < 0 || opponentSetsWon > 3
                    || setsPlayed != teamSetsWon + opponentSetsWon
                    || !((teamSetsWon == 3 && opponentSetsWon <= 2)
                    || (opponentSetsWon == 3 && teamSetsWon <= 2))) {

                throw new IllegalArgumentException(
                        "A completed match has 3-5 sets, one team winning 3 "
                                + "and the other 0-2. Sets played must equal the "
                                + "two sets-won values combined.");
            }
        }

        /**
         * Validates console input and records loaded from JSON.
         */
        public void validate() {
            if (teamName == null || teamName.trim().isEmpty()
                    || opponentName == null || opponentName.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Both team names are required.");
            }

            validateDate();
            validateSets();

            if (teamPointsScored <= 0 || opponentPointsScored <= 0) {
                throw new IllegalArgumentException(
                        "Each team's total points must be greater than zero.");
            }

            if (teamServiceAces < 0 || teamServiceAces > teamPointsScored
                    || opponentServiceAces < 0
                    || opponentServiceAces > opponentPointsScored) {
                throw new IllegalArgumentException(
                        "Service aces must be nonnegative "
                                + "and cannot exceed points.");
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getOpponentName() {
            return opponentName;
        }

        public void setOpponentName(String opponentName) {
            this.opponentName = opponentName;
        }

        public String getMatchDate() {
            return matchDate;
        }

        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }

        public int getSetsPlayed() {
            return setsPlayed;
        }

        public void setSetsPlayed(int setsPlayed) {
            this.setsPlayed = setsPlayed;
        }

        public int getTeamSetsWon() {
            return teamSetsWon;
        }

        public void setTeamSetsWon(int teamSetsWon) {
            this.teamSetsWon = teamSetsWon;
        }

        public int getOpponentSetsWon() {
            return opponentSetsWon;
        }

        public void setOpponentSetsWon(int opponentSetsWon) {
            this.opponentSetsWon = opponentSetsWon;
        }

        public int getTeamPointsScored() {
            return teamPointsScored;
        }

        public void setTeamPointsScored(int teamPointsScored) {
            this.teamPointsScored = teamPointsScored;
        }

        public int getOpponentPointsScored() {
            return opponentPointsScored;
        }

        public void setOpponentPointsScored(int opponentPointsScored) {
            this.opponentPointsScored = opponentPointsScored;
        }

        public int getTeamServiceAces() {
            return teamServiceAces;
        }

        public void setTeamServiceAces(int teamServiceAces) {
            this.teamServiceAces = teamServiceAces;
        }

        public int getOpponentServiceAces() {
            return opponentServiceAces;
        }

        public void setOpponentServiceAces(int opponentServiceAces) {
            this.opponentServiceAces = opponentServiceAces;
        }

        /**
         * Displays the match fields and the Assignment 2 calculation results.
         */
        @Override
        public String toString() {
            return String.format(
                    "Match #%d%n"
                            + "Team: %s%n"
                            + "Opponent: %s%n"
                            + "Match date: %s%n"
                            + "Sets played: %d%n"
                            + "Team sets won: %d%n"
                            + "Opponent sets won: %d%n"
                            + "Team points scored: %d%n"
                            + "Opponent points scored: %d%n"
                            + "Team service aces: %d%n"
                            + "Opponent service aces: %d",
                    id, teamName, opponentName, matchDate,
                    setsPlayed, teamSetsWon, opponentSetsWon,
                    teamPointsScored, opponentPointsScored,
                    teamServiceAces, opponentServiceAces)
                    + String.format(Locale.CANADA,
                            "%nWinner: %s%nTeam set win percentage: %.2f%%%n"
                                    + "Team point share: %.2f%%%nTeam aces per set: %.2f%n"
                                    + "Opponent aces per set: %.2f",
                            VolleyballMatchBO.determineWinner(this),
                            VolleyballMatchBO.calculate(this),
                            VolleyballMatchBO.calculatePointSharePercentage(this),
                            VolleyballMatchBO.calculateTeamAcesPerSet(this),
                            VolleyballMatchBO.calculateOpponentAcesPerSet(this));
        }

}
