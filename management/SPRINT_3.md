# Sprint 3 — Match Lifecycle and Scoring

## 1. Sprint Information

-   **Sprint name/number:** `Sprint 3 - Match Lifecycle and Scoring`
-   **Start date:** `2026-08-10`
-   **End date:** `2026-08-17`
-   **Duration:** `7 days`
-   **Scrum Master:** `Giacomo Serafini`
-   **Developers:** `Lorenzo Cusin, Andrea Tonello`
-   **Status:** `In Progress`

## 2. Sprint Goal

> Complete the Pong match lifecycle by awarding points when a player misses, resetting the round while preserving the score, ending the match when a player reaches five points, and allowing the players to start a new match without restarting the application.

### Success indicators

-   A player receives exactly one point when the opponent misses.
-   A new round starts automatically after a point while preserving both scores.
-   The match stops when a player reaches five points and the winner is clearly identified.
-   Players can start a new match after victory, resetting both scores and the game state.

## 3. Selected Product Backlog Items

| ID | Product Backlog Item | User value | Acceptance criteria summary |
| -- | -------------------- | ---------- | --------------------------- |
| `PB-06` | Score a point | Gives the match a measurable result when a player successfully defends their side. | When the ball crosses the opponent's scoring boundary, the correct player's score increases by one and exactly one point is awarded. |
| `PB-07` | Start the next round | Allows the match to continue after a point without requiring the application to restart. | After a point, the ball and paddles return to valid starting positions while both scores are preserved. |
| `PB-08` | Finish the match | Gives the match a clear conclusion and identifies the winner. | When a player reaches five points, that player is declared the winner and normal gameplay stops. |
| `PB-09` | Start a new match | Allows players to play another match without restarting the application. | After a match ends, starting a new match resets both scores to zero and restores the initial ball and paddle positions. |

## 4. Sprint Backlog

### PB-06 — Score a point

-   [ ] Define the left and right scoring boundaries.
-   [ ] Define which player is awarded a point for each scoring boundary.
-   [ ] Detect when the ball crosses the left scoring boundary.
-   [ ] Detect when the ball crosses the right scoring boundary.
-   [ ] Increment the correct player's score after a valid scoring event.
-   [ ] Ensure a single scoring event awards exactly one point.
-   [ ] Prevent the same ball crossing from awarding multiple points.
-   [ ] Separate scoring detection from round-reset logic where practical.
-   [ ] Add automated tests for a point scored by the left player.
-   [ ] Add automated tests for a point scored by the right player.
-   [ ] Add an automated test for the repeated-scoring edge case.
-   [ ] Integrate scoring with the existing ball update and game loop.

#### Acceptance criteria

```gherkin
Feature: Point scoring

  Scenario: Left player scores
    Given a match is active
    When the ball crosses the right scoring boundary
    Then the left player's score increases by one
    And exactly one point is awarded

  Scenario: Right player scores
    Given a match is active
    When the ball crosses the left scoring boundary
    Then the right player's score increases by one
    And exactly one point is awarded
```

### PB-07 — Start the next round

-   [ ] Define the initial ball position for a new round.
-   [ ] Define valid initial positions for both paddles.
-   [ ] Preserve the current scores during a round reset.
-   [ ] Reset the ball position after a point has been awarded.
-   [ ] Reset the ball velocity or prepare the ball for the next round according to the agreed game rules.
-   [ ] Reset both paddle positions to valid starting positions.
-   [ ] Ensure the reset does not modify either player's score.
-   [ ] Ensure normal gameplay resumes after the round reset.
-   [ ] Add automated tests for ball position after a point.
-   [ ] Add automated tests for paddle positions after a point.
-   [ ] Add automated tests proving that scores are preserved.
-   [ ] Add an integration test covering scoring followed by the next-round reset.

#### Acceptance criteria

```gherkin
Feature: Round reset

  Scenario: Reset after a point
    Given a point has been awarded
    When the next round is prepared
    Then the ball returns to its starting area
    And the paddles return to valid positions
    And the scores are preserved
```

### PB-08 — Finish the match

-   [ ] Define the winning score as five points.
-   [ ] Detect when either player reaches the winning score.
-   [ ] Declare the player who reaches five points as the winner.
-   [ ] Stop normal ball movement after the match ends.
-   [ ] Stop normal paddle movement after the match ends.
-   [ ] Prevent additional points from being awarded after the match has ended.
-   [ ] Preserve the final scores while the winner state is displayed.
-   [ ] Add automated tests for a player reaching five points.
-   [ ] Add automated tests proving that gameplay stops after victory.
-   [ ] Add an automated test proving that no additional points are awarded after the match ends.
-   [ ] Integrate match completion with the scoring and round-reset logic.
-   [ ] Perform a manual check that the winner and end-of-match state are clear to the players.

#### Acceptance criteria

```gherkin
Feature: Match completion

  Scenario: Reach the winning score
    Given a player has four points
    When that player scores another point
    Then that player is declared the winner
    And normal gameplay stops
```

### PB-09 — Start a new match

-   [ ] Define the transition from the finished state to a new match.
-   [ ] Provide a user action for starting a new match.
-   [ ] Reset the left player's score to zero.
-   [ ] Reset the right player's score to zero.
-   [ ] Reset the ball to its initial position.
-   [ ] Reset both paddles to their initial positions.
-   [ ] Clear the previous winner or finished-match state.
-   [ ] Restore normal gameplay for the new match.
-   [ ] Ensure the new match does not require restarting the application.
-   [ ] Add automated tests for both scores being reset to zero.
-   [ ] Add automated tests for the initial ball and paddle positions.
-   [ ] Add an integration test covering victory followed by a new match.
-   [ ] Verify that a new match starts with the same initial game configuration as the first match.

#### Acceptance criteria

```gherkin
Feature: New match

  Scenario: Restart after victory
    Given a match has ended
    When the players start a new match
    Then both scores are reset to zero
    And the ball and paddles return to their initial positions
```

## Additional PBs

### APB-1 — Prevent Ball Compenetration with Horizontal Walls

- [ ] Fix the horizontal wall collision handling so that the ball cannot remain beyond the wall boundary after a collision.
- [ ] When the ball crosses the lower horizontal boundary, reposition it to the corresponding position inside the arena based on the penetration depth.
- [ ] When the ball crosses the upper horizontal boundary, reposition it to the corresponding position inside the arena based on the penetration depth.
- [ ] Reverse the ball's vertical velocity when a horizontal wall collision occurs.
- [ ] Ensure that the collision correction works correctly even when the ball moves more than one distance unit beyond the wall between two updates.
- [ ] Add automated tests covering collisions with both horizontal walls and different penetration depths.
- [ ] Run the complete test suite and verify that existing ball movement and wall-bouncing behaviour is preserved.

#### Acceptance criteria

```gherkin
Feature: Prevent ball compenetration with horizontal walls

  Scenario: Ball crosses the lower horizontal wall
    Given the ball is moving towards the lower horizontal wall
    And the ball crosses the lower wall during an update
    When the horizontal wall collision is handled
    Then the ball is repositioned inside the arena
    And the ball does not remain below the lower wall
    And the vertical velocity is reversed

  Scenario: Ball crosses the upper horizontal wall
    Given the ball is moving towards the upper horizontal wall
    And the ball crosses the upper wall during an update
    When the horizontal wall collision is handled
    Then the ball is repositioned inside the arena
    And the ball does not remain above the upper wall
    And the vertical velocity is reversed

  Scenario: Ball crosses a wall by more than one distance unit
    Given the ball crosses a horizontal wall with a penetration depth greater than one distance unit
    When the horizontal wall collision is handled
    Then the ball is repositioned inside the arena according to the penetration depth
    And the ball does not remain beyond the wall boundary
```

### Shared sprint tasks

-   [ ] Review the existing game-state design before adding score and match-state responsibilities.
-   [ ] Keep scoring, round reset, and match completion responsibilities separated where practical.
-   [ ] Define explicit game states needed for active play, round reset, match completion, and new match.
-   [ ] Agree on how the winner and scores are represented in the game state.
-   [ ] Create or link GitHub Issues for each Product Backlog Item and its implementation tasks.
-   [ ] Use short-lived branches or another agreed integration strategy.
-   [ ] Follow TDD: write a failing test, implement the smallest change that makes it pass, then refactor while keeping the tests green.
-   [ ] Run the complete local test suite before opening or updating a pull request.
-   [ ] Require the continuous integration workflow to pass before merging.
-   [ ] Review each change for readability, duplication, coupling, cohesion, and unnecessary complexity.
-   [ ] Refactor in small steps while keeping all tests passing.
-   [ ] Pay particular attention to duplicated state-reset logic and responsibilities becoming concentrated in a large class or method.
-   [ ] Verify that scoring and match-state logic remain independent from JavaFX rendering where practical.
-   [ ] Perform a complete manual play-through from a new match to scoring, round reset, victory, and starting another match.
-   [ ] Update the README and Sprint documentation before the Sprint Review.

## 5. Sprint Improvements

--
