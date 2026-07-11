# Sprint 1 — Core Playable Game

## 1. Sprint Information

- **Sprint name/number:** `Sprint 1 — Core Playable Game`
- **Start date:** `<2026-07-11>`
- **End date:** `<2026-07-14>`
- **Duration:** `4 days`
- **Scrum Master:** `Andrea Tonello`
- **Developers:** `Giacomo Serafini, Lorenzo Cusin`
- **Status:** `In Progress`

## 2. Sprint Goal

> Deliver a continuously verified, playable Pong game in which the arena is displayed, both paddles can be controlled, and the ball moves and bounces correctly from walls and paddles.

### Success indicators

- Two players can control their paddles without moving outside the arena.
- The ball moves continuously and bounces correctly from the upper wall, lower wall, and paddles.

## 3. Selected Product Backlog Items

| ID      | Product Backlog Item                       | User value                                                                  | Acceptance criteria summary                                                                                                              |
| ------- | ------------------------------------------ | --------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `PB-00` | Build and verify the project automatically | Gives the team rapid feedback when compilation, tests, or integration fail. | On a push or pull request, the CI workflow compiles the project, runs automated tests, and fails when compilation or tests fail.         |
| `PB-01` | Display the game arena                     | Lets players understand the current game state.                             | When the application opens, two paddles, one ball, and clear playing boundaries are visible.                                             |
| `PB-02` | Move the paddles                           | Lets both players control their paddles and participate in the rally.       | The left and right paddles respond to their assigned keys and remain inside the arena.                                                   |
| `PB-03` | Move the ball                              | Starts and sustains the basic rally.                                        | When play starts, the ball leaves its initial position with a non-zero horizontal velocity.                                              |
| `PB-04` | Bounce from the arena boundaries           | Keeps the ball in play vertically.                                          | The ball reverses its vertical direction when it reaches the upper or lower boundary.                                                    |
| `PB-05` | Bounce from paddles                        | Lets players defend their side and return the ball.                         | A collision with the playing side of a paddle reverses the ball's horizontal direction without repeated reversals from the same contact. |

## 4. Sprint Backlog
### PB-00 — Build and verify the project automatically

- [ ] Confirm or create the standard Gradle project structure.
- [ ] Configure the Java plugin and required test dependencies.
- [ ] Add or verify the Gradle Wrapper in the repository.
- [ ] Ensure `./gradlew build` compiles the application and runs all automated tests locally.
- [ ] Create a GitHub Actions continuous integration workflow for pushes and pull requests.
- [ ] Configure the workflow to use the Gradle Wrapper and an agreed Java version.
- [ ] Verify that the workflow fails for a compilation error.
- [ ] Verify that the workflow fails for a failing automated test.
- [ ] Add brief build and test instructions to the project documentation.

#### Acceptance criteria

```gherkin
Feature: Automated verification

  Scenario: Verify a repository change
    Given a change is pushed or proposed in a pull request
    When the continuous integration workflow runs
    Then the project is compiled
    And the automated tests are executed
    And the workflow fails if compilation or tests fail
```

### PB-01 — Display the game arena

- [ ] Define the initial dimensions and coordinate system of the game arena.
- [ ] Create the main application window and game view.
- [ ] Implement the visual representation of the arena boundaries.
- [ ] Implement the visual representation of the left and right paddles.
- [ ] Implement the visual representation of the ball.
- [ ] Define initial positions and dimensions for the paddles and ball.
- [ ] Separate game-state data from rendering where practical.
- [ ] Add automated tests for initial game-state positions and dimensions.
- [ ] Perform a manual visual check that all required elements appear correctly.
- [ ] Document the selected arena dimensions and control-independent visual assumptions.

#### Acceptance criteria

```gherkin
Feature: Display the game arena

  Scenario: Open the game
    Given the application starts successfully
    When the main game view is displayed
    Then two paddles are visible
    And one ball is visible
    And the playing boundaries are clear
```

### PB-02 — Move the paddles

- [ ] Define the keyboard mappings for both players, including all required movement directions.
- [ ] Implement keyboard input handling for the left paddle.
- [ ] Implement keyboard input handling for the right paddle.
- [ ] Implement upward and downward paddle movement.
- [ ] Define the paddle movement speed.
- [ ] Prevent paddles from crossing the upper arena boundary.
- [ ] Prevent paddles from crossing the lower arena boundary.
- [ ] Add automated tests for upward and downward movement.
- [ ] Add boundary tests proving that paddles remain inside the arena.
- [ ] Integrate input handling with the game update loop and rendering.
- [ ] Document the final keyboard controls.

#### Acceptance criteria

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

### PB-03 — Move the ball

- [ ] Define the ball position and velocity representation.
- [ ] Define the ball's initial position.
- [ ] Define an initial velocity with a non-zero horizontal component.
- [ ] Implement time-based or tick-based ball position updates.
- [ ] Connect ball updates to the game loop.
- [ ] Ensure the rendered ball position reflects the current game state.
- [ ] Add automated tests proving that the ball moves after play starts.
- [ ] Add an automated test proving that the initial horizontal velocity is non-zero.
- [ ] Confirm that movement remains consistent at the chosen update rate.
- [ ] Document assumptions about speed, update frequency, and coordinate units.

#### Acceptance criteria

```gherkin
Feature: Ball movement

  Scenario: Begin a round
    Given a round is ready
    When play starts
    Then the ball moves from its initial position
    And the ball has a non-zero horizontal velocity
```

### PB-04 — Bounce from the arena boundaries

- [ ] Define the ball collision bounds using its size or radius.
- [ ] Detect contact with the upper arena boundary.
- [ ] Reverse the vertical velocity when the ball reaches the upper boundary.
- [ ] Detect contact with the lower arena boundary.
- [ ] Reverse the vertical velocity when the ball reaches the lower boundary.
- [ ] Correct or clamp the ball position after collision to avoid remaining outside the arena.
- [ ] Add an automated test for an upper-boundary collision.
- [ ] Add an automated test for a lower-boundary collision.
- [ ] Add an automated test that confirms horizontal velocity is preserved during a wall bounce.
- [ ] Integrate wall collision handling into the ball update sequence.

#### Acceptance criteria

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

### PB-05 — Bounce from paddles

- [ ] Define paddle and ball collision bounds.
- [ ] Detect collision between the ball and the left paddle.
- [ ] Detect collision between the ball and the right paddle.
- [ ] Restrict valid collisions to the playing side of each paddle.
- [ ] Reverse the ball's horizontal velocity after a valid paddle collision.
- [ ] Reposition or separate the ball after collision to prevent overlap.
- [ ] Prevent the same continuous contact from producing repeated horizontal reversals.
- [ ] Add an automated test for collision with the left paddle.
- [ ] Add an automated test for collision with the right paddle.
- [ ] Add an automated test for the repeated-contact edge case.
- [ ] Add tests proving that invalid rear-side contact does not cause an incorrect bounce, if applicable to the chosen implementation.
- [ ] Integrate paddle collisions with the ball movement and rendering loop.

#### Acceptance criteria

```gherkin
Feature: Paddle collision

  Scenario: Hit a paddle
    Given the ball is moving toward a paddle
    When the ball collides with the paddle from the playing side
    Then the ball reverses its horizontal direction
    And the same contact does not produce repeated reversals
```

### Shared sprint tasks

- [ ] Agree on a small package structure for application, game-state, input, rendering, and tests.
- [ ] Define a lightweight Definition of Done for the Sprint.
- [ ] Create or link GitHub Issues for each Product Backlog Item and its implementation tasks.
- [ ] Use short-lived branches or another agreed integration strategy.
- [ ] Run the complete local test suite before opening or updating a pull request.
- [ ] Require the continuous integration workflow to pass before merging.
- [ ] Review each change for readability, duplication, coupling, and unnecessary complexity.
- [ ] Refactor in small steps while keeping all tests passing.
- [ ] Integrate the selected backlog items into one demonstrable game increment.
- [ ] Update the README and Sprint documentation before the Sprint Review.

## 5. Sprint Review Result

- **Sprint Goal achieved:** `No`
- **Completed backlog items:** `<IDs>`
- **Incomplete backlog items:** `<IDs>`
- **Increment demonstrated:** `< >`
- **Acceptance criteria not satisfied:** `<None or list criteria>`
- **Stakeholder feedback:** `<summary>`
- **Product Backlog changes resulting from feedback:** `<new, removed, reordered, or revised items>`

## 8. Sprint Improvements

### What went well

- `<positive point>`
- `<positive point>`

### What did not go well

- `<problem>`
- `<problem>`

### What we learned

- `<lesson>`
- `<lesson>`

### Improvement actions for the next Sprint

| Improvement action  | Responsible person | Target date or Sprint | Status                           |
| ------------------- | ------------------ | --------------------- | -------------------------------- |
| `<specific action>` | `<name>`           | `<date or Sprint>`    | `<Planned / In progress / Done>` |
| `<specific action>` | `<name>`           | `<date or Sprint>`    | `<Planned / In progress / Done>` |
