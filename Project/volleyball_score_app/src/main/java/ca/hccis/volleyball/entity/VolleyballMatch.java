package ca.hccis.volleyball.entity;

import java.time.LocalDate;
import java.util.Locale;

/**
 * Match fields and calculated results used by the JDBC report.
 * @author John Aquino
 * @since 20261022
 */
public class VolleyballMatch {
    private int id;
    private String teamName;
    private String opponentName;
    private LocalDate matchDate;
    private int setsPlayed;
    private int teamSetsWon;
    private int opponentSetsWon;
    private int teamPointsScored;
    private int opponentPointsScored;
    private int teamServiceAces;
    private int opponentServiceAces;
    private String winnerName;
    private double setWinPercentage;
    private long totalPoints;
    private double pointSharePercentage;
    private double teamAcesPerSet;
    private double opponentAcesPerSet;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getOpponentName() { return opponentName; }
    public void setOpponentName(String opponentName) { this.opponentName = opponentName; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public int getSetsPlayed() { return setsPlayed; }
    public void setSetsPlayed(int setsPlayed) { this.setsPlayed = setsPlayed; }

    public int getTeamSetsWon() { return teamSetsWon; }
    public void setTeamSetsWon(int teamSetsWon) { this.teamSetsWon = teamSetsWon; }

    public int getOpponentSetsWon() { return opponentSetsWon; }
    public void setOpponentSetsWon(int opponentSetsWon) { this.opponentSetsWon = opponentSetsWon; }

    public int getTeamPointsScored() { return teamPointsScored; }
    public void setTeamPointsScored(int teamPointsScored) { this.teamPointsScored = teamPointsScored; }

    public int getOpponentPointsScored() { return opponentPointsScored; }
    public void setOpponentPointsScored(int opponentPointsScored) { this.opponentPointsScored = opponentPointsScored; }

    public int getTeamServiceAces() { return teamServiceAces; }
    public void setTeamServiceAces(int teamServiceAces) { this.teamServiceAces = teamServiceAces; }

    public int getOpponentServiceAces() { return opponentServiceAces; }
    public void setOpponentServiceAces(int opponentServiceAces) { this.opponentServiceAces = opponentServiceAces; }

    public String getWinnerName() { return winnerName; }
    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public double getSetWinPercentage() { return setWinPercentage; }
    public void setSetWinPercentage(double setWinPercentage) { this.setWinPercentage = setWinPercentage; }

    public long getTotalPoints() { return totalPoints; }
    public void setTotalPoints(long totalPoints) { this.totalPoints = totalPoints; }

    public double getPointSharePercentage() { return pointSharePercentage; }
    public void setPointSharePercentage(double pointSharePercentage) { this.pointSharePercentage = pointSharePercentage; }

    public double getTeamAcesPerSet() { return teamAcesPerSet; }
    public void setTeamAcesPerSet(double teamAcesPerSet) { this.teamAcesPerSet = teamAcesPerSet; }

    public double getOpponentAcesPerSet() { return opponentAcesPerSet; }
    public void setOpponentAcesPerSet(double opponentAcesPerSet) { this.opponentAcesPerSet = opponentAcesPerSet; }

    /** @return a readable match block for the text report */
    @Override
    public String toString() {
        return String.format(Locale.CANADA,
                "Match #%d | %s | %s vs %s%nSets played: %d | Sets: %d - %d%n"
                + "Points: %d - %d | Total points: %d%nAces: %d - %d%nWinner: %s%n"
                + "Team set win: %.2f%% | Team point share: %.2f%%%n"
                + "Team aces/set: %.2f | Opponent aces/set: %.2f%n",
                id, matchDate, teamName, opponentName, setsPlayed, teamSetsWon, opponentSetsWon,
                teamPointsScored, opponentPointsScored, totalPoints, teamServiceAces,
                opponentServiceAces, winnerName, setWinPercentage, pointSharePercentage,
                teamAcesPerSet, opponentAcesPerSet);
    }
}
