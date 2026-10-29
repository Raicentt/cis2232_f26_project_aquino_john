package ca.hccis.volleyball.dao;

import ca.hccis.volleyball.entity.VolleyballMatch;
import ca.hccis.volleyball.util.DatabaseUtility;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Retrieves report rows through JDBC, following the reference DAO pattern.
 * @author John Aquino
 * @since 20261022
 */
public class VolleyballMatchDAO {
    /**
     * Select tracked-team matches within an inclusive date range.
     * @param name exact team name, ignoring case and surrounding spaces
     * @param startDate first included date
     * @param endDate last included date
     * @return matching rows in date and ID order
     * @throws SQLException when database access fails
     */
    public ArrayList<VolleyballMatch> selectAllByNameAndDate(String name,
            LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM VolleyballMatch "
                + "WHERE LOWER(TRIM(teamName)) = LOWER(?) "
                + "AND matchDate BETWEEN ? AND ? ORDER BY matchDate, id";
        try (Connection connection = DatabaseUtility.getConnection("");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name.trim());
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            statement.setQueryTimeout(15);
            try (ResultSet results = statement.executeQuery()) {
                return loadList(results);
            }
        }
    }

    /**
     * Map all result columns to match objects.
     * @param results JDBC result set
     * @return mapped matches
     * @throws SQLException when a column cannot be read
     */
    public ArrayList<VolleyballMatch> loadList(ResultSet results) throws SQLException {
        ArrayList<VolleyballMatch> matches = new ArrayList<>();
        while (results.next()) {
            VolleyballMatch match = new VolleyballMatch();
            match.setId(results.getInt("id"));
            match.setTeamName(results.getString("teamName"));
            match.setOpponentName(results.getString("opponentName"));
            match.setMatchDate(results.getDate("matchDate").toLocalDate());
            match.setSetsPlayed(results.getInt("setsPlayed"));
            match.setTeamSetsWon(results.getInt("teamSetsWon"));
            match.setOpponentSetsWon(results.getInt("opponentSetsWon"));
            match.setTeamPointsScored(results.getInt("teamPointsScored"));
            match.setOpponentPointsScored(results.getInt("opponentPointsScored"));
            match.setTeamServiceAces(results.getInt("teamServiceAces"));
            match.setOpponentServiceAces(results.getInt("opponentServiceAces"));
            matches.add(match);
        }
        return matches;
    }
}
