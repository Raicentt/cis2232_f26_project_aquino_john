-- Optional sample data for Sprint 2; run after creating the database.
-- Run once: repeat imports add duplicate sample matches.
-- Does not delete any existing records.
USE cis2232_volleyball_scorer;
INSERT INTO VolleyballMatch
(teamName, opponentName, matchDate, setsPlayed, teamSetsWon, opponentSetsWon,
 teamPointsScored, opponentPointsScored, teamServiceAces, opponentServiceAces)
VALUES
('Hurricanes', 'Eagles', '2026-10-01', 4, 3, 1, 95, 85, 8, 4),
('Hurricanes', 'Falcons', '2026-10-10', 3, 0, 3, 52, 75, 3, 6),
('Hurricanes', 'Tigers', '2026-10-20', 5, 3, 2, 108, 104, 10, 7),
('Eagles', 'Hurricanes', '2026-10-12', 4, 3, 1, 95, 82, 7, 3);
