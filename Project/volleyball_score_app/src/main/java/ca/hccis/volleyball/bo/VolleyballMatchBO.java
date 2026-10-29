package ca.hccis.volleyball.bo;

import ca.hccis.volleyball.dao.VolleyballMatchDAO;
import ca.hccis.volleyball.entity.ReportInput;
import ca.hccis.volleyball.entity.VolleyballMatch;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Report processing and completed-match calculations.
 * @author John Aquino
 * @since 20261022
 */
public class VolleyballMatchBO {
    /**
     * Validate report inputs before opening a database connection.
     * @param report criteria from the input form
     * @throws IllegalArgumentException for invalid criteria
     */
    public static void validateCriteria(ReportInput report) {
        if (report.getName().isBlank() || report.getName().length() > 100
                || report.getName().chars().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Enter a team name of 1 to 100 characters without control characters.");
        }
        try {
            LocalDate start = LocalDate.parse(report.getStartDate());
            LocalDate end = LocalDate.parse(report.getEndDate());
            if (start.getYear() < 1000 || end.getYear() > 9999 || start.isAfter(end)) {
                throw new IllegalArgumentException("Use dates between years 1000 and 9999, with the start on or before the end.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Enter a valid start date and end date.");
        }
    }

    /**
     * Retrieve and calculate each row of the report.
     * @param report input criteria
     * @return calculated match rows
     * @throws SQLException when retrieval fails
     */
    public ArrayList<VolleyballMatch> processSelectAllByNameAndDate(ReportInput report)
            throws SQLException {
        validateCriteria(report);
        ArrayList<VolleyballMatch> matches = new VolleyballMatchDAO().selectAllByNameAndDate(
                report.getName(), LocalDate.parse(report.getStartDate()), LocalDate.parse(report.getEndDate()));
        for (VolleyballMatch match : matches) {
            calculate(match);
        }
        return matches;
    }

    /**
     * Calculate results only for consistent completed best-of-five matches.
     * @param match database match to calculate
     * @throws IllegalArgumentException when stored data is inconsistent
     */
    public static void calculate(VolleyballMatch match) {
        boolean teamWon = match.getTeamSetsWon() == 3
                && match.getOpponentSetsWon() >= 0 && match.getOpponentSetsWon() <= 2;
        boolean opponentWon = match.getOpponentSetsWon() == 3
                && match.getTeamSetsWon() >= 0 && match.getTeamSetsWon() <= 2;
        long totalPoints = (long) match.getTeamPointsScored() + match.getOpponentPointsScored();
        if ((!teamWon && !opponentWon) || match.getSetsPlayed() < 3 || match.getSetsPlayed() > 5
                || match.getSetsPlayed() != match.getTeamSetsWon() + match.getOpponentSetsWon()
                || match.getTeamPointsScored() < 0 || match.getOpponentPointsScored() < 0 || totalPoints <= 0
                || match.getTeamServiceAces() < 0 || match.getOpponentServiceAces() < 0
                || match.getTeamServiceAces() > match.getTeamPointsScored()
                || match.getOpponentServiceAces() > match.getOpponentPointsScored()) {
            throw new IllegalArgumentException("Match #" + match.getId()
                    + " contains inconsistent scores. Correct the database record before reporting.");
        }
        match.setWinnerName(teamWon ? match.getTeamName() : match.getOpponentName());
        match.setTotalPoints(totalPoints);
        match.setSetWinPercentage(100.0 * match.getTeamSetsWon() / match.getSetsPlayed());
        match.setPointSharePercentage(100.0 * match.getTeamPointsScored() / totalPoints);
        match.setTeamAcesPerSet((double) match.getTeamServiceAces() / match.getSetsPlayed());
        match.setOpponentAcesPerSet((double) match.getOpponentServiceAces() / match.getSetsPlayed());
    }
}
