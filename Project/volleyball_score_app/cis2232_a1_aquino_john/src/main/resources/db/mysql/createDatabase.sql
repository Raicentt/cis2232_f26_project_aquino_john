# For localhost
DROP DATABASE IF EXISTS cis2232_volleyball_scorer;
CREATE DATABASE cis2232_volleyball_scorer;
use cis2232_volleyball_scorer;

-- ------------------------------------------------------------------------------
-- Table to hold data associated with the Volleyball Scoring App
-- ------------------------------------------------------------------------------

CREATE TABLE volleyball_match (
                                  id                  INT                 NOT NULL AUTO_INCREMENT,
                                  matchDate           VARCHAR(10)         NOT NULL,
                                  createdDateTime     VARCHAR(100)        NOT NULL,
                                  player1Name         VARCHAR(100)        NOT NULL,
                                  player2Name         VARCHAR(100)        NOT NULL,

                                  player1Game1Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,
                                  player2Game1Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,

                                  player1Game2Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,
                                  player2Game2Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,

                                  player1Game3Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,
                                  player2Game3Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,

                                  player1Game4Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,
                                  player2Game4Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,

                                  player1Game5Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,
                                  player2Game5Score   TINYINT UNSIGNED    NOT NULL DEFAULT 0,

                                  winnerName          VARCHAR(100)        NOT NULL,

                                  PRIMARY KEY (id)
);