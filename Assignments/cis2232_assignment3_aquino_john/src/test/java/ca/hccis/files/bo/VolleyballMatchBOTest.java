package ca.hccis.files.bo;

import ca.hccis.files.entity.VolleyballMatch;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the VolleyballMatchBO calculation methods.
 *
 * @author John Aquino
 * @since 20261007
 */
class VolleyballMatchBOTest {

    private static final double DELTA = 0.000001;

    /**
     * Test 1: observed failing before implementing the
     * calculation for a straight-sets win.
     */
    @Test
    void testCalculateStraightSetsWin() {
        VolleyballMatch match = createValidMatch();
        match.setSetsPlayed(3);
        match.setTeamSetsWon(3);
        match.setOpponentSetsWon(0);

        double actual = VolleyballMatchBO.calculate(match);

        assertEquals(100.0, actual, DELTA);
    }

    /**
     * Test 2: observed failing before implementing the
     * general set win percentage formula.
     */
    @Test
    void testCalculateFourSetWin() {
        VolleyballMatch match = createValidMatch();

        double actual = VolleyballMatchBO.calculate(match);

        assertTrue(
                Math.abs(actual - 75.0) < DELTA,
                "Winning 3 of 4 sets should give 75 percent.");
    }

    /**
     * Test 3: observed failing before adding validation
     * to reject an inconsistent number of sets.
     */
    @Test
    void testCalculateRejectsInconsistentSets() {
        VolleyballMatch match = createValidMatch();
        match.setOpponentSetsWon(2);

        // Sets won total 5, but sets played is still 4.
        assertThrows(
                IllegalArgumentException.class,
                () -> VolleyballMatchBO.calculate(match));
    }

    // ============================================================
    // REQUIREMENT 2: ADDITIONAL AI-GENERATED TESTS
    // Generated using the project requirements and entity class.
    // ============================================================

    /**
     * Additional AI-Generated Tests
     * @author ChatGPT & Claude
     * @since 20261007
     */

    @Test
    void testCalculateFiveSetWin() {
        VolleyballMatch match = createValidMatch();
        match.setSetsPlayed(5);
        match.setTeamSetsWon(3);
        match.setOpponentSetsWon(2);

        assertEquals(
                60.0,
                VolleyballMatchBO.calculate(match),
                DELTA);
    }

    @Test
    void testCalculateStraightSetsLoss() {
        VolleyballMatch match = createValidMatch();
        match.setSetsPlayed(3);
        match.setTeamSetsWon(0);
        match.setOpponentSetsWon(3);

        assertEquals(
                0.0,
                VolleyballMatchBO.calculate(match),
                DELTA);
    }

    @Test
    void testCalculateFourSetLoss() {
        VolleyballMatch match = createValidMatch();
        match.setTeamSetsWon(1);
        match.setOpponentSetsWon(3);

        assertEquals(
                25.0,
                VolleyballMatchBO.calculate(match),
                DELTA);
    }

    @Test
    void testCalculateFiveSetLoss() {
        VolleyballMatch match = createValidMatch();
        match.setSetsPlayed(5);
        match.setTeamSetsWon(2);
        match.setOpponentSetsWon(3);

        assertEquals(
                40.0,
                VolleyballMatchBO.calculate(match),
                DELTA);
    }

    @Test
    void testCalculatePointSharePercentage() {
        VolleyballMatch match = createValidMatch();

        // 95 / (95 + 85) * 100
        assertEquals(
                52.77777777777778,
                VolleyballMatchBO.calculatePointSharePercentage(match),
                DELTA);
    }

    @Test
    void testEqualPointsGiveFiftyPercent() {
        VolleyballMatch match = createValidMatch();
        match.setTeamPointsScored(90);
        match.setOpponentPointsScored(90);

        assertEquals(
                50.0,
                VolleyballMatchBO.calculatePointSharePercentage(match),
                DELTA);
    }

    @Test
    void testLargePointTotalsDoNotOverflow() {
        VolleyballMatch match = createValidMatch();
        match.setTeamPointsScored(Integer.MAX_VALUE);
        match.setOpponentPointsScored(Integer.MAX_VALUE);

        double actual =
                VolleyballMatchBO.calculatePointSharePercentage(match);

        assertAll(
                () -> assertEquals(50.0, actual, DELTA),
                () -> assertTrue(Double.isFinite(actual)));
    }

    @Test
    void testDetermineWinnerTeam() {
        VolleyballMatch match = createValidMatch();

        assertEquals(
                "Falcons",
                VolleyballMatchBO.determineWinner(match));
    }

    @Test
    void testDetermineWinnerOpponent() {
        VolleyballMatch match = createValidMatch();
        match.setSetsPlayed(5);
        match.setTeamSetsWon(2);
        match.setOpponentSetsWon(3);

        assertEquals(
                "Eagles",
                VolleyballMatchBO.determineWinner(match));
    }

    @Test
    void testWinnerDependsOnSetsRatherThanPoints() {
        VolleyballMatch match = createValidMatch();

        // Falcons win 3-1 despite scoring fewer total points.
        match.setTeamPointsScored(85);
        match.setOpponentPointsScored(95);

        assertEquals(
                "Falcons",
                VolleyballMatchBO.determineWinner(match));
    }

    @Test
    void testCalculateAcesPerSet() {
        VolleyballMatch match = createValidMatch();

        assertAll(
                () -> assertEquals(
                        2.0,
                        VolleyballMatchBO.calculateTeamAcesPerSet(match),
                        DELTA),
                () -> assertEquals(
                        1.0,
                        VolleyballMatchBO.calculateOpponentAcesPerSet(match),
                        DELTA));
    }

    @Test
    void testAcesUseFloatingPointDivision() {
        VolleyballMatch match = createValidMatch();
        match.setSetsPlayed(3);
        match.setTeamSetsWon(3);
        match.setOpponentSetsWon(0);
        match.setTeamServiceAces(5);
        match.setOpponentServiceAces(1);

        assertAll(
                () -> assertEquals(
                        1.6666666666666667,
                        VolleyballMatchBO.calculateTeamAcesPerSet(match),
                        DELTA),
                () -> assertEquals(
                        0.3333333333333333,
                        VolleyballMatchBO.calculateOpponentAcesPerSet(match),
                        DELTA));
    }

    @Test
    void testZeroAcesGiveZeroAverages() {
        VolleyballMatch match = createValidMatch();
        match.setTeamServiceAces(0);
        match.setOpponentServiceAces(0);

        assertAll(
                () -> assertEquals(
                        0.0,
                        VolleyballMatchBO.calculateTeamAcesPerSet(match),
                        DELTA),
                () -> assertEquals(
                        0.0,
                        VolleyballMatchBO.calculateOpponentAcesPerSet(match),
                        DELTA));
    }

    @Test
    void testAcesCanEqualPoints() {
        VolleyballMatch match = createValidMatch();
        match.setTeamServiceAces(95);
        match.setOpponentServiceAces(85);

        assertAll(
                () -> assertEquals(
                        23.75,
                        VolleyballMatchBO.calculateTeamAcesPerSet(match),
                        DELTA),
                () -> assertEquals(
                        21.25,
                        VolleyballMatchBO.calculateOpponentAcesPerSet(match),
                        DELTA));
    }

    @Test
    void testAllMethodsRejectNullMatch() {
        assertRejectedByAllMethods(null);
    }

    @Test
    void testAllMethodsRejectInvalidNamesAndDates() {
        assertAll(
                () -> assertInvalidMatch(
                        match -> match.setTeamName(null)),
                () -> assertInvalidMatch(
                        match -> match.setTeamName("")),
                () -> assertInvalidMatch(
                        match -> match.setOpponentName(null)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentName("   ")),
                () -> assertInvalidMatch(
                        match -> match.setMatchDate(null)),
                () -> assertInvalidMatch(
                        match -> match.setMatchDate("2026-02-30")),
                () -> assertInvalidMatch(
                        match -> match.setMatchDate("08/10/2026")));
    }

    @Test
    void testAllMethodsRejectInvalidSets() {
        assertAll(
                () -> assertInvalidMatch(
                        match -> match.setSetsPlayed(0)),
                () -> assertInvalidMatch(
                        match -> match.setSetsPlayed(2)),
                () -> assertInvalidMatch(
                        match -> match.setSetsPlayed(6)),
                () -> assertInvalidMatch(
                        match -> match.setTeamSetsWon(-1)),
                () -> assertInvalidMatch(
                        match -> match.setTeamSetsWon(4)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentSetsWon(-1)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentSetsWon(4)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentSetsWon(2)),
                () -> assertInvalidMatch(match -> {
                    match.setTeamSetsWon(2);
                    match.setOpponentSetsWon(2);
                }),
                () -> assertInvalidMatch(match -> {
                    match.setSetsPlayed(5);
                    match.setTeamSetsWon(3);
                    match.setOpponentSetsWon(3);
                }));
    }

    @Test
    void testAllMethodsRejectInvalidPointsAndAces() {
        assertAll(
                () -> assertInvalidMatch(
                        match -> match.setTeamPointsScored(0)),
                () -> assertInvalidMatch(
                        match -> match.setTeamPointsScored(-1)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentPointsScored(0)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentPointsScored(-1)),
                () -> assertInvalidMatch(
                        match -> match.setTeamServiceAces(-1)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentServiceAces(-1)),
                () -> assertInvalidMatch(
                        match -> match.setTeamServiceAces(96)),
                () -> assertInvalidMatch(
                        match -> match.setOpponentServiceAces(86)));
    }

    /**
     * Creates a valid match before applying one invalid change.
     */
    private void assertInvalidMatch(
            Consumer<VolleyballMatch> change) {

        VolleyballMatch match = createValidMatch();
        change.accept(match);
        assertRejectedByAllMethods(match);
    }

    /**
     * Confirms that every business method rejects invalid input.
     */
    private void assertRejectedByAllMethods(VolleyballMatch match) {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> VolleyballMatchBO.calculate(match)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> VolleyballMatchBO
                                .calculatePointSharePercentage(match)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> VolleyballMatchBO
                                .calculateTeamAcesPerSet(match)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> VolleyballMatchBO
                                .calculateOpponentAcesPerSet(match)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> VolleyballMatchBO.determineWinner(match)));
    }

    /**
     * Creates the valid baseline match used by the tests.
     */
    private VolleyballMatch createValidMatch() {
        VolleyballMatch match = new VolleyballMatch();

        match.setId(1);
        match.setTeamName("Falcons");
        match.setOpponentName("Eagles");
        match.setMatchDate("2026-10-08");
        match.setSetsPlayed(4);
        match.setTeamSetsWon(3);
        match.setOpponentSetsWon(1);
        match.setTeamPointsScored(95);
        match.setOpponentPointsScored(85);
        match.setTeamServiceAces(8);
        match.setOpponentServiceAces(4);

        return match;
    }
}