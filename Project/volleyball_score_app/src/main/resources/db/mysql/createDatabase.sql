-- Volleyball Score App | Work Item 4
-- Author: John Aquino

CREATE DATABASE IF NOT EXISTS cis2232_volleyball_scorer;
USE cis2232_volleyball_scorer;

CREATE TABLE IF NOT EXISTS VolleyballMatch (
    id                      INT NOT NULL AUTO_INCREMENT,
    teamName                VARCHAR(100) NOT NULL,
    opponentName            VARCHAR(100) NOT NULL,
    matchDate               DATE NOT NULL,
    setsPlayed              INT NOT NULL,
    teamSetsWon             INT NOT NULL,
    opponentSetsWon         INT NOT NULL,
    teamPointsScored        INT NOT NULL,
    opponentPointsScored    INT NOT NULL,
    teamServiceAces         INT NOT NULL,
    opponentServiceAces     INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_names CHECK (CHAR_LENGTH(TRIM(teamName)) > 0 AND CHAR_LENGTH(TRIM(opponentName)) > 0),
    CONSTRAINT chk_sets CHECK (setsPlayed BETWEEN 3 AND 5 AND setsPlayed = teamSetsWon + opponentSetsWon),
    CONSTRAINT chk_winner CHECK ((teamSetsWon = 3 AND opponentSetsWon BETWEEN 0 AND 2)
                             OR (opponentSetsWon = 3 AND teamSetsWon BETWEEN 0 AND 2)),
    CONSTRAINT chk_points CHECK (teamPointsScored >= 0 AND opponentPointsScored >= 0
                             AND teamPointsScored + opponentPointsScored > 0),
    CONSTRAINT chk_aces CHECK (teamServiceAces BETWEEN 0 AND teamPointsScored
                           AND opponentServiceAces BETWEEN 0 AND opponentPointsScored)
);
