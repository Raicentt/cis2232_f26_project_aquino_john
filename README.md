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

## Required Fields

| Field | Data Type | Description |
| --- | --- | --- |
| id | int | Automatically generated unique identifier for the database table. |
| teamName | String | Name of the team whose performance is tracked. |
| opponentName | String | Name of the opposing team. |
| matchDate | Date | Date the match was played. |
| setsPlayed | int | Total sets played in the completed match, between three and five. |
| teamSetsWon | int | Sets won by the tracked team. |
| opponentSetsWon | int | Sets won by the opposing team. |
| teamPointsScored | int | Total points scored by the tracked team across all sets. |
| opponentPointsScored | int | Total points scored by the opposing team across all sets. |
| teamServiceAces | int | Total service aces recorded by the tracked team. |
| opponentServiceAces | int | Total service aces recorded by the opposing team. |

## Calculation

The application will determine the match winner and calculate the following statistics from each valid match record.

### Match Winner

If teamSetsWon equals three, teamName is the winner. If opponentSetsWon equals three, opponentName is the winner.

The winner is determined by sets won, not total points scored.

### Team Set Win Percentage

Set Win Percentage = (teamSetsWon / setsPlayed) × 100

Example: Winning three of four sets gives a set win percentage of 75.00%.

### Team Point Share Percentage

Total Points = teamPointsScored + opponentPointsScored

Point Share Percentage = (teamPointsScored / Total Points) × 100

Example: If the team scores 95 points and the opponent scores 85 points, the team's point share is 52.78%.

### Service Aces per Set

Team Aces per Set = teamServiceAces / setsPlayed

Opponent Aces per Set = opponentServiceAces / setsPlayed

Example: Over four sets, eight team aces and four opponent aces give averages of 2.00 and 1.00 aces per set.

Percentages and averages will be displayed to two decimal places. Calculated results will be derived from the entered fields rather than entered separately by the user.

## Validation

- Team name, opponent name, and a valid match date are required.
- Numerical inputs must be nonnegative whole numbers.
- Sets played must be between three and five.
- Sets played must equal the combined sets won by both teams.
- One team must have won exactly three sets, while the other must have won zero, one, or two sets.
- Combined points scored must be greater than zero.
- Each team's service aces must not exceed that team's points scored.

## Report Details

### Team Match Performance Report

Enter a team name and a start and end date. The report will return matches where the entered name matches the tracked team name and the match date falls within the selected date range, including both dates. Team-name matching will ignore letter case and surrounding spaces.

The report results will display the match date, team name, opponent name, sets played, sets won by each team, points scored by each team, service aces recorded by each team, and match winner. It will also show the tracked team's set win percentage, point share percentage, and each team's average service aces per set.

Matches will be displayed in date order. Percentages and averages will be displayed to two decimal places.

A grouped bar chart will compare the team's points with the opponent's points for each match. The horizontal axis will show the matches, identified by date and opponent, and the vertical axis will show total points scored.

The report will retrieve records using a DAO class with JDBC rather than Spring Data JPA.

The results will also be saved to a text file in C:\cis2232\. The filename will contain the report type and current date/time, such as TeamMatchPerformance202610191430.txt. The file will include the selected criteria and report results.

If no matches are found, the report will display and save a message indicating that no matches met the selected criteria.