# Initial Product Backlog

The Product Backlog entries may change after feedback, technical discovery, or new client requests.

## PB-00 — Build and verify the project automatically

**User story**
As a development team, we want automated build and test execution so that integration problems are detected quickly.

**Acceptance criteria**

```gherkin
Feature: Automated verification

  Scenario: Verify a repository change
    Given a change is pushed or proposed in a pull request
    When the continuous integration workflow runs
    Then the project is compiled
    And the automated tests are executed
    And the workflow fails if compilation or tests fail
```


## PB-01 — Display the game arena

**User story**
As a player, I want to see the arena, paddles, and ball so that I can understand the game state.

**Acceptance criteria**

```gherkin
Feature: Display the game arena

  Scenario: Open the game
    Given the application starts successfully
    When the main game view is displayed
    Then two paddles are visible
    And one ball is visible
    And the playing boundaries are clear
```

## PB-02 — Move the paddles

**User story**
As a player, I want to move my paddle using the keyboard so that I can return the ball.

**Acceptance criteria**

```gherkin
Feature: Paddle controls

  Scenario: Move the left paddle
    Given a match is active
    When the left player presses W
    Then the left paddle moves upward

  Scenario: Move the right paddle
    Given a match is active
    When the right player presses the Down Arrow
    Then the right paddle moves downward

  Scenario: Keep paddles inside the arena
    Given a paddle is touching the top or bottom boundary
    When the player attempts to move beyond that boundary
    Then the paddle remains inside the arena
```

## PB-03 — Move the ball

**User story**
As a player, I want the ball to move automatically so that the rally progresses.

**Acceptance criteria**

```gherkin
Feature: Ball movement

  Scenario: Begin a round
    Given a round is ready
    When play starts
    Then the ball moves from its initial position
    And the ball has a non-zero horizontal velocity
```

## PB-04 — Bounce from the arena boundaries

**User story**
As a player, I want the ball to bounce from the top and bottom boundaries so that it remains in play.

**Acceptance criteria**

```gherkin
Feature: Wall collision

  Scenario: Hit the upper boundary
    Given the ball is moving upward
    When it reaches the upper boundary
    Then its vertical direction is reversed

  Scenario: Hit the lower boundary
    Given the ball is moving downward
    When it reaches the lower boundary
    Then its vertical direction is reversed
```

## PB-05 — Bounce from paddles

**User story**
As a player, I want the ball to bounce from my paddle so that I can defend my side.

**Acceptance criteria**

```gherkin
Feature: Paddle collision

  Scenario: Hit a paddle
    Given the ball is moving toward a paddle
    When the ball collides with the paddle from the playing side
    Then the ball reverses its horizontal direction
    And the same contact does not produce repeated reversals
```

## PB-06 — Score a point

**User story**
As a player, I want to receive a point when my opponent misses so that the match has a measurable result.

**Acceptance criteria**

```gherkin
Feature: Point scoring

  Scenario: Left player scores
    Given a match is active
    When the ball crosses the right scoring boundary
    Then the left player's score increases by one
    And exactly one point is awarded
```

## PB-07 — Start the next round

**User story**
As a player, I want the next round to begin after a point so that the match can continue.

**Acceptance criteria**

```gherkin
Feature: Round reset

  Scenario: Reset after a point
    Given a point has been awarded
    When the next round is prepared
    Then the ball returns to its starting area
    And the paddles return to valid positions
    And the scores are preserved
```

## PB-08 — Finish the match

**User story**
As a player, I want the game to declare a winner at five points so that the match has a clear conclusion.

**Acceptance criteria**

```gherkin
Feature: Match completion

  Scenario: Reach the winning score
    Given a player has four points
    When that player scores another point
    Then that player is declared the winner
    And normal gameplay stops
```

## PB-09 — Start a new match

**User story**
As a player, I want to start another match after the previous one ends so that I can play again without restarting the application.

**Acceptance criteria**

```gherkin
Feature: New match

  Scenario: Restart after victory
    Given a match has ended
    When the players start a new match
    Then both scores are reset to zero
    And the ball and paddles return to their initial positions
```
