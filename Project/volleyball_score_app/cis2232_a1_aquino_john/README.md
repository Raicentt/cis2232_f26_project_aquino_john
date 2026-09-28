# cis2232_f26_project_aquino_john

# Volleyball Score App

## Project Description

The Volleyball Score App keeps track of the scores of two volleyball players across five games. For each match, it records the match date, both players’ names, and each player’s score in every game.

## Project Team

| Role | Name |
| --- | --- |
| BA / Business Client | Aidan Chappelle |
| Developer | John Raicent Aquino |
| Project Manager / QA | Connor Chappelle |

## Project Base Color

Pastel green

## Data Fields

| Field | Data Type | Description |
| --- | --- | --- |
| `id` | int | Unique identifier for the database record |
| `matchDate` | String | Date the match was played |
| `createdDateTime` | String | Date and time the record was entered |
| `player1Name` | String | Name of player 1 |
| `player2Name` | String | Name of player 2 |
| `player1Game1Score` | int | Player 1’s score in game 1 |
| `player2Game1Score` | int | Player 2’s score in game 1 |
| `player1Game2Score` | int | Player 1’s score in game 2 |
| `player2Game2Score` | int | Player 2’s score in game 2 |
| `player1Game3Score` | int | Player 1’s score in game 3 |
| `player2Game3Score` | int | Player 2’s score in game 3 |
| `player1Game4Score` | int | Player 1’s score in game 4 |
| `player2Game4Score` | int | Player 2’s score in game 4 |
| `player1Game5Score` | int | Player 1’s score in game 5 |
| `player2Game5Score` | int | Player 2’s score in game 5 |
| `winnerName` | String | Name of the winning player |

## Planned Calculation

When a new match record is entered, the application will determine which player won three games. It will then store that player’s name in `winnerName`.
