# Sprint 2 — Model and Configuration Refactoring

## 1. Sprint Information

- **Sprint name/number:** `Sprint 2 — Model and Configuration Refactoring`
- **Start date:** `<2026-07-28>`
- **End date:** `<2026-08-01>`
- **Duration:** `4 days`
- **Scrum Master:** `Lorenzo Cusin`
- **Developers:** `Andrea Tonello, Giacomo Serafini`
- **Status:** `Planned`

## 2. Sprint Goal

> Improve the internal game architecture by introducing explicit domain objects, adopting a consistent mutable update model, and centralizing static application settings in an external configuration file.

### Success indicators

- Ball and paddle behaviour is represented by dedicated mutable domain classes.
- Game updates modify existing domain objects without replacing them at every frame.
- Static application settings and controls are loaded from one validated YAML configuration source.
- Existing game behaviour is preserved and verified by automated tests.

## 3. Selected Product Backlog Items

| ID      | Product Backlog Item                  | Developer value                                                                 | Acceptance criteria summary                                                                                                      |
| ------- | ------------------------------------- | ------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| `PB-10` | Refactor Circle and Rectangle         | Gives game entities clear domain-specific names and removes redundant abstractions. | Circle and Rectangle are replaced by Ball and Paddle, and all related code and tests are updated.                                 |
| `PB-11` | Refactor records into mutable classes | Simplifies game updates by allowing existing domain objects to change directly. | Ball, Paddle, and updater logic use mutable classes, and updates no longer create replacement objects.                            |
| `PB-12` | Unify application configuration       | Provides a single, persistent, and validated source for static application settings. | `config.yaml` is loaded into typed configuration objects and used to initialize the game and configured controls.                 |

## 4. Sprint Backlog

### PB-10 — Refactor Circle and Rectangle

- [ ] Replace the `Circle` record with a domain-specific `Ball` type.
- [ ] Replace the `Rectangle` record with a domain-specific `Paddle` type.
- [ ] Rename position and dimension properties where needed to reflect their domain meaning.
- [ ] Update movement, collision, rendering, and game-state code to use `Ball` and `Paddle`.
- [ ] Remove the obsolete `Circle` and `Rectangle` types.
- [ ] Update all related automated tests.
- [ ] Run the complete test suite and verify that existing game behaviour is preserved.

#### Acceptance criteria

```gherkin
Feature: Refactor Circle and Rectangle into Ball and Paddle

  Scenario: Refactor Circle and Rectangle into Ball and Paddle
    Given Circle and Rectangle have evolved into more complex records
    And having Superclasses Circle and Rectangle would be redundant
    Then rename Circle and Rectangle into Ball and Paddle
    And all the code related to them is updated accordingly
    And all the tests related to them are updated accordingly
```

### PB-11 — Refactor records into mutable classes

- [ ] Convert `Ball` from a record into a mutable class.
- [ ] Convert `Paddle` from a record into a mutable class.
- [ ] Keep stable properties immutable where appropriate and encapsulate mutable fields.
- [ ] Add domain operations for controlled position and velocity changes.
- [ ] Change ball movement logic to update the existing `Ball` instance.
- [ ] Change paddle movement logic to update the existing `Paddle` instances.
- [ ] Adapt updater composition and the game loop to the mutable update model.
- [ ] Remove object replacement that is no longer required during game updates.
- [ ] Update all related automated tests to verify both behaviour and instance preservation.
- [ ] Run the complete test suite and verify that gameplay remains unchanged.

#### Acceptance criteria

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

### PB-12 — Unify application configuration

- [ ] Create `config.yaml` with the default viewport, arena, ball, paddle, and control settings.
- [ ] Introduce immutable `AppConfig`, `ViewportConfig`, `GamePageConfig`, `ArenaConfig`, `BallConfig`, `PaddleConfig`, and `ControlsConfig` records.
- [ ] Implement `YamlConfigLoader` and load `config.yaml` once at application startup.
- [ ] Validate required properties, unknown properties, numeric values, and cross-field dimensions.
- [ ] Produce clear startup errors for invalid configuration.
- [ ] Introduce `GameSession` as the abstraction between configuration and domain objects.
- [ ] Initialize the arena, ball, and paddles by passing primitive values from `GameSession` to their constructors.
- [ ] Ensure `Arena`, `Ball`, and `Paddle` do not depend on or retain configuration objects.
- [ ] Convert `ControlsConfig` into the paddle key bindings.
- [ ] Refactor `App` to assemble the game from `AppConfig` and `GameSession`.
- [ ] Adapt rendering and movement logic to operate on the initialized domain objects.
- [ ] Remove the old `GameConfig` and hard-coded key mappings.
- [ ] Add automated tests for valid, missing, unknown, and invalid configuration values.
- [ ] Update existing tests and verify that the default configuration preserves current behaviour.

#### Acceptance criteria

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

### Shared sprint tasks

- [ ] Implement the selected Product Backlog Items in dependency order: PB-10, PB-11, then PB-12.
- [ ] Perform each refactoring in small, reviewable steps while keeping the project buildable.
- [ ] Run the complete local test suite before opening or updating a pull request.
- [ ] Require the continuous integration workflow to pass before merging.
- [ ] Review each change for readability, encapsulation, coupling, and unnecessary duplication.
- [ ] Confirm that the refactoring does not alter existing gameplay behaviour.
- [ ] Update relevant project and architectural documentation.
- [ ] Integrate all selected backlog items into the `dev` branch.
- [ ] Update the project version to `0.2` before merging the completed Sprint into `main`.
- [ ] Update this Sprint document before the Sprint Review.

### Emergent refactoring work

- During the implementation of PB-10, PB-11, and PB-12, the selected refactorings may reveal additional useful code improvements.
- Changes required to satisfy the selected Product Backlog Items are added as tasks to the relevant item in this Sprint Backlog.
- Useful but non-essential improvements are documented as new Product Backlog Items with their own scope and acceptance criteria.
- New Product Backlog Items are added to the Product Backlog and prioritized before being selected for a Sprint; they do not automatically expand the scope of Sprint 2.

## 5. Sprint Improvements

...