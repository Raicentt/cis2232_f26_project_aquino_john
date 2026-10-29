# cis2232_f26_project_aquino_john

# Volleyball Score App

## Project Description

The Volleyball Score App tracks completed best-of-five volleyball matches between two teams. Each match records the team names, match date, sets played, sets won, total points scored, and service aces. The application determines the winner and calculates performance statistics. Users can generate a team performance report for a selected date range, view a points comparison chart, and save the report results to a text file.

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

Enter a team name and a start and end date. The report returns matches for that tracked team within the selected date range, including both dates. Team-name matching ignores letter case and surrounding spaces.

Results show the match date, team and opponent names, sets played, sets won, points scored, service aces, and winner. They also include the team's set win percentage, point share percentage, and each team's average aces per set. Matches appear in date order.

A grouped bar chart compares both teams' points for each match.

The report uses a DAO class with JDBC and saves the results to a text file in C:\cis2232\reports\. Filenames use MatchReport followed by the creation date and time, such as MatchReport20261029093045.txt.

If no matches are found, a message is displayed and saved to the report file.
