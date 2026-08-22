# Sprint 5 — Gameplay Refinement, Documentation and Code Quality

## 1. Sprint Information

* **Sprint name/number:** Sprint 5 — Gameplay Refinement, Documentation and Code Quality
* **Start date:** `<2026-08-22>`
* **End date:** `<2026-08-25>`
* **Duration:** `4 days`
* **Scrum Master:** `Giacomo Serafini`
* **Developers:** `Lorenzo Cusin, Andrea Tonello`
* **Status:** `In progress`

## 2. Sprint Goal

> Improve the gameplay experience and overall product quality by refining ball behaviour, improving the user interface, providing user and developer documentation, and organizing the codebase without changing existing gameplay behaviour.

### Success indicators

* The ball starts each round with a valid randomized direction and accelerates only after valid paddle bounces.
* The game's menus and gameplay overlays provide a clear and consistent user experience while preserving existing controls and navigation.
* Users can understand how to play and configure SDMPong through the commercial product documentation.
* Developers can consult generated Javadoc to understand the public application APIs and their behaviour.
* The model package is organized under the game package and the complete test suite remains green.

## 3. Selected Product Backlog Items

| ID        | Product Backlog Item                          | User value                                                                                     | Acceptance criteria                                                                                                                                              |
| --------- | --------------------------------------------- | ---------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **PB-20** | Randomize the ball direction at round start   | Makes each round less predictable while preserving the configured ball speed.                  | At round start, the ball uses the configured velocity magnitudes, selects valid horizontal and vertical directions, and always has non-zero horizontal velocity. |
| **PB-21** | Increase ball speed after paddle bounces      | Makes gameplay progressively faster and more challenging during a rally.                       | A valid paddle bounce reverses the horizontal direction and increases speed; wall bounces only reflect direction and preserve speed.                             |
| **PB-22** | Add commercial product documentation          | Allows players to understand, start, play, and configure the game without technical knowledge. | The product guide explains the game's purpose, gameplay, controls, and available settings.                                                                       |
| **PB-23** | Document the code with Javadoc                | Makes the codebase easier for developers to understand, maintain, and extend.                  | Javadoc is generated successfully and provides useful documentation for the public application APIs and relevant behaviour.                                      |
| **PB-24** | Improve the user interface                    | Provides a clearer and more polished experience when navigating and playing the game.          | Menus and overlays are visually consistent and clear while preserving existing navigation and gameplay behaviour.                                                |
| **PB-25** | Move the model package under the game package | Improves the organization of the application architecture without changing behaviour.          | Production and test code compile and pass after the model package is relocated under the game package.                                                           |

## 4. Sprint Backlog

### PB-20 — Randomize the ball direction at round start

* [ ] Define which velocity components may change direction when a round starts.
* [ ] Preserve the configured initial velocity magnitudes.
* [ ] Randomize the horizontal and vertical velocity signs when a round begins.
* [ ] Apply randomized directions after a point and when a new match starts.
* [ ] Ensure the ball always has a non-zero horizontal velocity.
* [ ] Use an injectable or controllable source of randomness so tests remain deterministic.
* [ ] Add automated tests covering the allowed initial direction combinations.
* [ ] Add automated tests proving that configured velocity magnitudes are preserved.
* [ ] Add automated tests covering round resets after scoring.
* [ ] Verify that a sequence of rounds can produce different valid directions.

#### Acceptance criteria

```gherkin
Feature: Randomized ball direction

  Scenario: Begin a round with a randomized direction
    Given a round is ready to start
    When the round begins
    Then the ball uses the configured initial velocity magnitudes
    And its horizontal and vertical directions are selected from the allowed directions
    And its horizontal velocity is non-zero
```

### PB-21 — Increase ball speed after paddle bounces

* [ ] Define the speed increase rule for a valid paddle bounce.
* [ ] Add the speed increase setting to the ball configuration.
* [ ] Validate that the speed increase produces a faster ball.
* [ ] Increase the ball’s velocity when it validly bounces from either paddle.
* [ ] Reverse the horizontal direction as part of the paddle bounce.
* [ ] Increase the velocity only once for each valid paddle collision.
* [ ] Do not increase speed when the ball bounces from an arena wall.
* [ ] Do not increase speed when the ball contacts a paddle from the invalid side.
* [ ] Preserve the configured direction-randomization behaviour when a new round starts.
* [ ] Add automated tests for speed increase on the left paddle.
* [ ] Add automated tests for speed increase on the right paddle.
* [ ] Add an automated test proving that wall bounces do not increase speed.
* [ ] Add an automated test proving that repeated contact does not apply acceleration more than once.
* [ ] Verify that the speed increase remains compatible with round resets and new matches.

#### Acceptance criteria

```gherkin
Feature: Increase ball speed after paddle bounces

  Scenario: Accelerate after a valid paddle bounce
    Given the ball is moving toward a paddle
    When the ball validly bounces from that paddle
    Then the ball reverses its horizontal direction
    And the ball speed is greater than before the bounce

  Scenario: Do not accelerate after a wall bounce
    Given the ball is moving toward an arena wall
    When the ball bounces from the wall
    Then its direction is reflected
    And its speed is unchanged
```

### PB-22 — Add commercial product documentation

* [ ] Write a user-facing commercial document describing SDMPong and its main benefits.
* [ ] Explain how to start a match and use the Main Menu.
* [ ] Document gameplay, scoring, match completion, and starting a new match.
* [ ] Document the default controls and how to customize them.
* [ ] Document the Options menu and available match settings.
* [ ] Add the document to the `docs` branch in a location accessible to users.
* [ ] Review the document for clear, non-technical language and consistent product terminology.

#### Acceptance criteria

```gherkin
Feature: Commercial product documentation

  Scenario: User reads the product guide
    Given a user opens the SDMPong documentation
    When the user reads the commercial product guide
    Then the user can understand the product's purpose
    And the user can learn how to start and play a match
    And the user can find the available controls and settings
```

### PB-23 — Document the code with Javadoc

* [ ] Identify the public classes, interfaces, records, constructors, and methods that require documentation.
* [ ] Add Javadoc comments using the standard Javadoc format.
* [ ] Document the responsibilities and expected usage of the main application, navigation, game, model, configuration, and rendering APIs.
* [ ] Document non-obvious validation rules, state transitions, and input behaviour.
* [ ] Keep comments accurate, concise, and focused on behaviour rather than implementation details.
* [ ] Generate Javadoc successfully as part of the project build or documentation check.
* [ ] Review generated Javadoc for missing or malformed documentation.

#### Acceptance criteria

```gherkin
Feature: Source code documentation

  Scenario: Developer consults the generated Javadoc
    Given the project source code has been documented
    When a developer generates the Javadoc
    Then the documentation is generated successfully
    And the public application APIs have useful descriptions
    And documented state and validation rules match the implementation
```

### PB-24 — Improve the user interface

* [ ] Review the Main Menu, Options menu, pause menu, and finished-match overlay for visual consistency.
* [ ] Improve button appearance and interaction feedback where appropriate.
* [ ] Improve menu spacing, alignment, sizing, and layout hierarchy.
* [ ] Make labels, actions, and navigation cues clear to players.
* [ ] Preserve all existing menu actions and keyboard behaviour.
* [ ] Keep the UI usable at the configured application window size.
* [ ] Perform a manual visual review of every menu and gameplay overlay.

#### Acceptance criteria

```gherkin
Feature: User interface improvements

  Scenario: Player uses the game menus
    Given the player opens a game menu
    When the player views and uses its controls
    Then the layout is clear and visually consistent
    And the available actions are easy to identify
    And existing navigation and gameplay behaviour still work
```

### PB-25 — Move the model package under the game package

* [ ] Move the model source package from `com.cuseto.pong.model` to the corresponding package under `com.cuseto.pong.game`.
* [ ] Update package declarations for the model classes and view interfaces.
* [ ] Update imports and references throughout the production source code.
* [ ] Update test package declarations, imports, and file locations to match the new package.
* [ ] Update any documentation or configuration references to the old model package.
* [ ] Remove stale references to the original model package.
* [ ] Run the complete test suite and confirm that behaviour is unchanged.

#### Acceptance criteria

```gherkin
Feature: Model package organization

  Scenario: Application uses the relocated model package
    Given the model classes have been moved under the game package
    When the project is compiled and tested
    Then all production and test references resolve successfully
    And the application retains its existing gameplay behaviour
```

### Shared sprint tasks

* [ ] Run the complete Gradle test suite after each relevant implementation change.
* [ ] Review code changes for consistency with the existing project architecture.
* [ ] Perform integration testing across gameplay, menus, configuration, and round transitions.
* [ ] Review documentation for consistency with the final implementation.
* [ ] Perform a final manual playthrough of the application.
* [ ] Perform code review for all Sprint 5 changes.
* [ ] Confirm that no stale package references, undocumented public APIs, or outdated user-facing instructions remain.

## 5. Sprint Improvements

### What did not go well

--
