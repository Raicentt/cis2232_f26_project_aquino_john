package ca.hccis.files.entity;

import ca.hccis.files.util.CisUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 *  Provides entities and information needed for the Volleyball Match Scoring App
 *
 *  From: Aidan Chappelle
 *  Developed by: John Aquino
 *  Managed by: Connor Chappelle
 *  Date: 9-25-2026
 *
 */

public class VolleyballMatch {

    private int id;
    private String matchDate;
    private String createdDateTime;

    private String player1Name;
    private String player2Name;

    private int player1Game1Score;
    private int player2Game1Score;

    private int player1Game2Score;
    private int player2Game2Score;

    private int player1Game3Score;
    private int player2Game3Score;

    private int player1Game4Score;
    private int player2Game4Score;

    private int player1Game5Score;
    private int player2Game5Score;

    private String winnerName;

    public VolleyballMatch() {

    }

    /**
     * Get volleyball match information from the user.
     */
    public void getInformation() {

        matchDate = CisUtility.getInputString(
                "Match Date (YYYY-MM-DD)"
        );

        player1Name = CisUtility.getInputString(
                "Player 1 Name"
        );

        player2Name = CisUtility.getInputString(
                "Player 2 Name"
        );

        System.out.println();
        System.out.println("Game 1");

        player1Game1Score = CisUtility.getInputInt(
                player1Name + " score"
        );

        player2Game1Score = CisUtility.getInputInt(
                player2Name + " score"
        );

        System.out.println();
        System.out.println("Game 2");

        player1Game2Score = CisUtility.getInputInt(
                player1Name + " score"
        );

        player2Game2Score = CisUtility.getInputInt(
                player2Name + " score"
        );

        System.out.println();
        System.out.println("Game 3");

        player1Game3Score = CisUtility.getInputInt(
                player1Name + " score"
        );

        player2Game3Score = CisUtility.getInputInt(
                player2Name + " score"
        );

        System.out.println();
        System.out.println("Game 4");

        player1Game4Score = CisUtility.getInputInt(
                player1Name + " score"
        );

        player2Game4Score = CisUtility.getInputInt(
                player2Name + " score"
        );

        System.out.println();
        System.out.println("Game 5");

        player1Game5Score = CisUtility.getInputInt(
                player1Name + " score"
        );

        player2Game5Score = CisUtility.getInputInt(
                player2Name + " score"
        );

        // Automatically store date/time record was created.
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        createdDateTime =
                LocalDateTime.now().format(formatter);

        winnerName = "";
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public int getPlayer1Game1Score() {
        return player1Game1Score;
    }

    public void setPlayer1Game1Score(int player1Game1Score) {
        this.player1Game1Score = player1Game1Score;
    }

    public int getPlayer2Game1Score() {
        return player2Game1Score;
    }

    public void setPlayer2Game1Score(int player2Game1Score) {
        this.player2Game1Score = player2Game1Score;
    }

    public int getPlayer1Game2Score() {
        return player1Game2Score;
    }

    public void setPlayer1Game2Score(int player1Game2Score) {
        this.player1Game2Score = player1Game2Score;
    }

    public int getPlayer2Game2Score() {
        return player2Game2Score;
    }

    public void setPlayer2Game2Score(int player2Game2Score) {
        this.player2Game2Score = player2Game2Score;
    }

    public int getPlayer1Game3Score() {
        return player1Game3Score;
    }

    public void setPlayer1Game3Score(int player1Game3Score) {
        this.player1Game3Score = player1Game3Score;
    }

    public int getPlayer2Game3Score() {
        return player2Game3Score;
    }

    public void setPlayer2Game3Score(int player2Game3Score) {
        this.player2Game3Score = player2Game3Score;
    }

    public int getPlayer1Game4Score() {
        return player1Game4Score;
    }

    public void setPlayer1Game4Score(int player1Game4Score) {
        this.player1Game4Score = player1Game4Score;
    }

    public int getPlayer2Game4Score() {
        return player2Game4Score;
    }

    public void setPlayer2Game4Score(int player2Game4Score) {
        this.player2Game4Score = player2Game4Score;
    }

    public int getPlayer1Game5Score() {
        return player1Game5Score;
    }

    public void setPlayer1Game5Score(int player1Game5Score) {
        this.player1Game5Score = player1Game5Score;
    }

    public int getPlayer2Game5Score() {
        return player2Game5Score;
    }

    public void setPlayer2Game5Score(int player2Game5Score) {
        this.player2Game5Score = player2Game5Score;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    @Override
    public String toString() {

        return "Match ID: " + id
                + System.lineSeparator()
                + "Match Date: " + matchDate
                + System.lineSeparator()
                + "Created: " + createdDateTime
                + System.lineSeparator()
                + player1Name + " vs " + player2Name
                + System.lineSeparator()
                + "Game 1: "
                + player1Name + " " + player1Game1Score
                + " - "
                + player2Game1Score + " " + player2Name
                + System.lineSeparator()
                + "Game 2: "
                + player1Name + " " + player1Game2Score
                + " - "
                + player2Game2Score + " " + player2Name
                + System.lineSeparator()
                + "Game 3: "
                + player1Name + " " + player1Game3Score
                + " - "
                + player2Game3Score + " " + player2Name
                + System.lineSeparator()
                + "Game 4: "
                + player1Name + " " + player1Game4Score
                + " - "
                + player2Game4Score + " " + player2Name
                + System.lineSeparator()
                + "Game 5: "
                + player1Name + " " + player1Game5Score
                + " - "
                + player2Game5Score + " " + player2Name;
    }
}