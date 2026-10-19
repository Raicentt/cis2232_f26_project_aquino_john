package ca.hccis.files.bo;

import ca.hccis.files.entity.VolleyballMatch;

/**
 * Business calculations for completed best-of-five volleyball matches.
 * @author John Raicent Aquino / ChatGPT assistance
 * @since 20261008
 */
public class VolleyballMatchBO {

    /**
     * Calculates the tracked team's set win percentage without rounding.
     * @param match completed match whose inputs satisfy entity validation
     * @return percentage between 0 and 100
     * @throws IllegalArgumentException if the match is null or invalid
     */
    public static double calculate(VolleyballMatch match) {
        validateMatch(match);
        return match.getTeamSetsWon() * 100.0 / match.getSetsPlayed();
    }

    /**
     * Calculates the tracked team's share of all points scored in the match.
     * @param match valid completed match
     * @return point share percentage without rounding
     * @throws IllegalArgumentException if the match is null or invalid
     */
    public static double calculatePointSharePercentage(VolleyballMatch match) {
        validateMatch(match);
        double totalPoints = (double) match.getTeamPointsScored()
                + match.getOpponentPointsScored();
        return match.getTeamPointsScored() * 100.0 / totalPoints;
    }

    /**
     * Calculates the tracked team's average service aces per set.
     * @param match valid completed match
     * @return average aces per set without roundi
     * @throws IllegalArgumentException if the match is null or invalid
     */
    public static double calculateTeamAcesPerSet(VolleyballMatch match) {
        validateMatch(match);
        return (double) match.getTeamServiceAces() / match.getSetsPlayed();
    }

    /**
     * Calculates the opponent's average service aces per set.
     * @param match valid completed match
     * @return average aces per set without rounding
     * @throws IllegalArgumentException if the match is null or invalid
     */
    public static double calculateOpponentAcesPerSet(VolleyballMatch match) {
        validateMatch(match);
        return (double) match.getOpponentServiceAces() / match.getSetsPlayed();
    }

    /**
     * Finds the winner by sets won, regardless of each team's total points.
     * @param match valid completed match
     * @return winning team's name
     * @throws IllegalArgumentException if the match is null or invalid
     */
    public static String determineWinner(VolleyballMatch match) {
        validateMatch(match);
        return match.getTeamSetsWon() == 3
                ? match.getTeamName() : match.getOpponentName();
    }

    /** Centralizes the same precondition check for every calculation method. */
    private static void validateMatch(VolleyballMatch match) {
        if (match == null) {
            throw new IllegalArgumentException("Match is required.");
        }
        match.validate();
    }
}
