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

## PB-10 — Refactor Circle and Rectangle

**Acceptance criteria**

```gherkin
Feature: Refactor Circle and Rectangle into Ball and Paddle

  Scenario: Refactor Circle and Rectangle into Ball and Paddle
    Given Circle and Rectangle have evolved into more complex records
    And having Superclasses Circle and Rectangle would be redundant
    Then rename Circle and Rectangle into Ball and Paddle
    And all the code related to them is updated accordingly
    And all the tests related to them are updated accordingly
```

## PB-11 — Refactor records into mutable classes

**Acceptance criteria**

```gherkin
Feature: Right now, records are being used to represent dynamic objects. Since record fields are immutable, the current updater substitutes the old objects with new instantiations, at each game update. We want to swap records with mutable classes, so that the updater only needs to change the object's fields, without returning the a new instantiation.

  Scenario: Refactor records into mutable classes
    Given Ball, Paddle and Updater logic work with records
    Then update their structure using mutable classes 
    And any update logic does not need to return a new instance
    And any update logic only needs to update the needed fields of the same instance
    * Example: BallMovement.move() will update the Ball's fields instead of returning a new Ball object  
    And all the tests related to them are updated accordingly
```

## PB-12 — Unify application configuration

**Developer story**

As a developer, I want the application’s static settings to be loaded from a YAML file, so that configuration has a single source of truth and can be changed without modifying the source code.

**Acceptance criteria**

```gherkin
Feature: Right now, static application settings are distributed across GameConfig, game objects, and hard-coded key mappings. We want to move these settings into a config.yaml file loaded into a structured and immutable AppConfig, so that the application has a single configuration source.

  Scenario: Load and apply the application configuration
    Given config.yaml contains the viewport, arena, ball, paddle, and control settings
    When the application starts
    Then YamlConfigLoader loads config.yaml once
    And the loaded settings are represented by an immutable AppConfig
    And AppConfig is composed of ViewportConfig, GamePageConfig, and ControlsConfig
    And GamePageConfig is composed of ArenaConfig, BallConfig, and PaddleConfig
    And GameSession uses the relevant AppConfig settings to initialize the arena, ball, and paddles
    And GameSession passes only the required primitive values to the domain object constructors
    And Arena, Ball, and Paddle are independent of AppConfig and its configuration records
    And ControlsConfig is used to create the paddle key bindings
    And the renderer and movement logic use the domain objects initialized by GameSession
    And the old GameConfig and hard-coded key mappings are removed
    And the default configuration preserves the current game behaviour
    And existing tests are updated accordingly
    And tests for configuration loading and validation are added

  Scenario: Reject an invalid application configuration
    Given config.yaml contains missing, unknown, or invalid properties
    When YamlConfigLoader loads the configuration
    Then a clear configuration error is produced
    And the application does not start
    And dimensions, radius, inset, and speed are required to be positive

  Scenario: Change application settings without modifying the source code
    Given config.yaml contains valid configuration values
    When a viewport, arena, ball, paddle, or control value is changed
    And the application is restarted
    Then the application uses the updated value without requiring source-code changes
```

### Technical details
Since this PB describes a critical code refactoring, the main architectural requirements are shown.

**Config file structure**

```yaml
viewport:
  screenWidth: 800
  screenHeight: 600

gamePage:
  arena:
    spacingTop: 80
    spacingOther: 20
    boundaryThickness: 4

  ball:
    radius: 8
    initialVelocityX: 100.0
    initialVelocityY: 100.0

  paddle:
    width: 10
    height: 80
    inset: 100
    speed: 300.0

controls:
  leftPaddle:
    up: W
    down: S
  rightPaddle:
    up: UP
    down: DOWN
```

**Basic architectural structure**
- `YamlConfigLoader` loads `config.yaml` and returns an immutable `AppConfig`.
- `AppConfig` contains `ViewportConfig`, `GamePageConfig`, and `ControlsConfig`.
- `GamePageConfig` contains `ArenaConfig`, `BallConfig`, and `PaddleConfig`.
- `GameSession` acts as the abstraction layer between configuration and domain objects.
- `GameSession` contains the `Arena`, `Ball`, left `Paddle`, and right `Paddle`.
- `GameSession` passes primitive configuration values to the domain object constructors.
- `Arena`, `Ball`, and `Paddle` do not depend on configuration classes.
- `ControlsConfig` is converted into the game’s key bindings.
- Rendering and movement logic operate on the initialized domain objects.

## PB-13 — Main menu and application navigation

**Acceptance criteria**

```gherkin
Feature: Main menu navigation

  Scenario: Start a game from the main menu
    Given the application is displaying the main menu
    When the player chooses to start a game
    Then the gameplay scene is displayed
    And a new match uses the configured initial game state
```

## PB-14 — Pause gameplay

**Acceptance criteria**

```gherkin
Feature: Pause gameplay

  Scenario: Pause and resume a match
    Given a match is in progress
    When the player presses Esc
    Then the pause menu is displayed
    And ball and paddle movement stop
    When the player presses Esc again
    Then the pause menu is hidden
    And gameplay resumes from the preserved match state
```

## PB-15 — Options menu

**Acceptance criteria**

```gherkin
Feature: Options navigation

  Scenario: Return from options to the main menu
    Given the application is displaying the main menu
    When the player opens the options menu
    Then the options scene is displayed
    When the player returns from options
    Then the main menu scene is displayed
```

## PB-16 — Match finished menu navigation

**Acceptance criteria**

```gherkin
Feature: Match finished menu

  Scenario: Start a new match after victory
    Given a player has won the match
    When the match finished menu is displayed
    And the player starts a new match
    Then the match finished menu is hidden
    And both scores are zero
    And normal gameplay resumes
```

## PB-17 — Persist player configuration

**Acceptance criteria**

```gherkin
Feature: Persistent player configuration

  Scenario: Load saved configuration
    Given a valid user configuration file exists
    When the application starts
    Then the saved configuration is used

  Scenario: Fall back to defaults
    Given no user configuration file exists
    When the application starts
    Then the bundled default configuration is used
```

## PB-18 — Configure match and movement settings

**Acceptance criteria**

```gherkin
Feature: Match and movement settings

  Scenario: Start a match with saved gameplay settings
    Given the player saves a winning score, ball speed, and paddle speed in Options
    When the player starts a new match from the main menu
    Then the match uses the saved winning score
    And the ball uses the saved speed for both initial velocity axes
    And the paddles use the saved speed
```

## PB-19 — Configure paddle controls

**Acceptance criteria**

```gherkin
Feature: Configurable paddle controls

  Scenario: Use saved paddle controls
    Given the player saves valid paddle control bindings in Options
    When the player starts a new match from the main menu
    And the player presses a saved movement key
    Then the intended paddle moves in the configured direction
```
