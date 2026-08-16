# Sprint 4 — Application Navigation and Configurable Gameplay

## 1. Sprint Information

-   **Sprint name/number:** `Sprint 4 — Application Navigation and Configurable Gameplay`
-   **Start date:** `2026-08-17`
-   **End date:** `2026-08-21`
-   **Duration:** `5 days`
-   **Scrum Master:** `Andrea Tonello`
-   **Developers:** `Lorenzo Cusin, Giacomo Serafini`
-   **Status:** `Planned`

## 2. Sprint Goal

> Deliver a navigable Pong application with separate main and options scenes, gameplay overlays for pause and match completion, and persisted player-configurable gameplay settings.

### Success indicators

- Players can navigate from the main menu into gameplay and options without restarting the application.
- Players can pause, resume, finish, and restart matches while the gameplay state is preserved or reset at the correct transition.
- Winning score, movement speeds, and paddle controls can be configured, persisted, and applied to newly started matches.
- Navigation, configuration, and gameplay lifecycle behaviour is covered by automated tests.

## 3. Selected Product Backlog Items

| ID | Product Backlog Item | User value | Acceptance criteria summary |
| -- | -------------------- | ---------- | --------------------------- |
| `PB-13` | Main menu and application navigation | Gives players a clear entry point and allows gameplay to start without coupling scene management to game logic. | Starting from the main menu displays gameplay and uses the configured initial game state. |
| `PB-14` | Pause gameplay | Lets players temporarily stop a match without losing its state. | Esc displays the pause menu, stops movement, and a second Esc resumes the preserved match. |
| `PB-15` | Options menu | Gives players a dedicated place to configure the game before starting a match. | Options can be opened from Main Menu and exited back to Main Menu. |
| `PB-16` | Match finished menu navigation | Gives players clear actions after victory and keeps finished-match state separate from active gameplay. | A new match hides the finished menu, resets scores, and resumes gameplay. |
| `PB-17` | Persist player configuration | Keeps player settings across application launches while retaining bundled defaults. | A valid user configuration is loaded; defaults are used when no user file exists. |
| `PB-18` | Configure match and movement settings | Lets players choose the winning score, ball speed, and paddle speed for future matches. | A new match uses the saved winning score, shared ball speed, and paddle speed. |
| `PB-19` | Configure paddle controls | Lets players choose controls that match their preferences. | A new match uses saved paddle bindings to move the intended paddles. |

## 4. Sprint Backlog

### General structure reminder table

| State | JavaFX structure | Allowed transitions |
|---|---|---|
| Main menu | Its own `Scene` | Start game, open Options, quit |
| Options | Its own `Scene` | Return to Main only |
| Gameplay | Its own `Scene` with a `StackPane` root | Pause, finish match, return to Main |
| Pause | Overlay in gameplay’s `StackPane` | Resume gameplay, optionally return to Main |
| Match finished | Overlay in gameplay’s `StackPane` | Start new match, quit |

---

### PB-13 — Main menu and application navigation

- [ ] Define application navigation states for Main Menu, Options, Gameplay, and gameplay overlays.
- [ ] Create an `AppNavigator` as the sole owner of `Stage.setScene(...)`.
- [ ] Create a dedicated Main Menu scene.
- [ ] Make Main Menu the initial application scene.
- [ ] Create a gameplay controller that owns the active `GameSession`, `GameLoop`, and input state.
- [ ] Create a fresh `GameSession` when gameplay starts from Main Menu.
- [ ] Ensure returning to Main Menu stops and disposes the active game loop.
- [ ] Keep scene navigation responsibilities separate from domain and rendering logic.
- [ ] Add automated tests for the initial Main Menu state.
- [ ] Add automated tests for Main Menu to Gameplay navigation.
- [ ] Add an integration test proving a new gameplay session uses the configured initial state.

#### Acceptance criteria

```gherkin
Feature: Main menu navigation

  Scenario: Start a game from the main menu
    Given the application is displaying the main menu
    When the player chooses to start a game
    Then the gameplay scene is displayed
    And a new match uses the configured initial game state
```

### PB-14 — Pause gameplay

- [ ] Add a `StackPane` gameplay root containing the canvas and a dedicated overlay layer.
- [ ] Define a paused gameplay state separate from the finished-match state.
- [ ] Add a Pause Menu view displayed in the gameplay overlay layer.
- [ ] Open Pause Menu when Esc is pressed during active gameplay.
- [ ] Close Pause Menu and resume gameplay when Esc is pressed while paused.
- [ ] Gate game updates while gameplay is paused.
- [ ] Preserve scores, ball state, and paddle state while paused.
- [ ] Ignore and clear paddle input while the Pause Menu is visible.
- [ ] Add automated tests proving that ball and paddle movement stops while paused.
- [ ] Add automated tests proving that pause and resume preserve match state.
- [ ] Add an integration test covering gameplay → pause → gameplay.

#### Acceptance criteria

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

### PB-15 — Options menu

- [ ] Create a dedicated Options Menu scene.
- [ ] Add navigation from Main Menu to Options Menu.
- [ ] Add a Back action from Options Menu to Main Menu.
- [ ] Ensure Options Menu cannot be opened from Gameplay.
- [ ] Ensure Options Menu cannot be opened from Pause Menu.
- [ ] Keep options view components separate from navigation and configuration persistence.
- [ ] Add automated tests for Main Menu → Options Menu → Main Menu navigation.
- [ ] Add automated tests proving that Options Menu is unavailable during gameplay and pause.

#### Acceptance criteria

```gherkin
Feature: Options navigation

  Scenario: Return from options to the main menu
    Given the application is displaying the main menu
    When the player opens the options menu
    Then the options scene is displayed
    When the player returns from options
    Then the main menu scene is displayed
```

### PB-16 — Match finished menu navigation

- [ ] Add a Match Finished Menu view in the gameplay overlay layer.
- [ ] Display the finished menu when a player reaches the winning score.
- [ ] Preserve and display the final scores while the menu is visible.
- [ ] Prevent Pause Menu from opening after the match has finished.
- [ ] Add an action to start a new match from Match Finished Menu.
- [ ] Add an Esc action to quit the application from Match Finished Menu.
- [ ] Hide Match Finished Menu when a new match starts.
- [ ] Restore active gameplay input and updates after a new match begins.
- [ ] Keep match completion and new-match state in `GameSession` rather than menu views.
- [ ] Add an integration test covering gameplay → victory → Match Finished Menu.
- [ ] Add an integration test covering Match Finished Menu → new match.
- [ ] Add an automated test proving Esc quits from Match Finished Menu.

#### Acceptance criteria

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

### PB-17 — Persist player configuration

- [ ] Define a writable user-level `config.yaml` location.
- [ ] Keep the bundled `app/src/main/resources/config.yaml` as the default configuration.
- [ ] Load bundled defaults when no user configuration exists.
- [ ] Load user configuration overrides when a valid user file exists.
- [ ] Add a configuration repository responsible for loading and saving configuration.
- [ ] Save configuration updates atomically.
- [ ] Handle unreadable or invalid user configuration according to the agreed fallback policy.
- [ ] Keep `AppConfig` immutable when applying loaded settings.
- [ ] Add automated tests for default configuration loading.
- [ ] Add automated tests for loading saved user configuration.
- [ ] Add automated tests for saving and reloading configuration.
- [ ] Add automated tests for invalid user configuration handling.

#### Acceptance criteria

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

### PB-18 — Configure match and movement settings

- [ ] Add an editable winning-score control to Options Menu.
- [ ] Add one editable ball-speed control to Options Menu.
- [ ] Apply the ball-speed value to both initial ball velocity axes.
- [ ] Add an editable paddle-speed control to Options Menu.
- [ ] Validate winning score as a positive integer.
- [ ] Validate ball speed as a positive finite value.
- [ ] Validate paddle speed as a positive finite value.
- [ ] Display validation feedback without saving invalid values.
- [ ] Save valid values through the player configuration repository.
- [ ] Apply saved values to newly created matches.
- [ ] Ensure saving options does not mutate an active match.
- [ ] Add automated tests for numeric option validation.
- [ ] Add automated tests for saving match and movement settings.
- [ ] Add an integration test covering Options Menu → Main Menu → new match with saved settings.

#### Acceptance criteria

```gherkin
Feature: Match and movement settings

  Scenario: Start a match with saved gameplay settings
    Given the player saves a winning score, ball speed, and paddle speed in Options
    When the player starts a new match from the main menu
    Then the match uses the saved winning score
    And the ball uses the saved speed for both initial velocity axes
    And the paddles use the saved speed
```

### PB-19 — Configure paddle controls

- [ ] Replace hard-coded paddle mappings with mappings built from `ControlsConfig`.
- [ ] Add editable up and down bindings for both paddles to Options Menu.
- [ ] Capture a key press when assigning a paddle control.
- [ ] Validate that each paddle’s up and down bindings differ.
- [ ] Prevent duplicate bindings across both paddles.
- [ ] Prevent reserved menu keys, including Esc, from being assigned as paddle controls.
- [ ] Display validation feedback without saving invalid bindings.
- [ ] Save valid bindings through the player configuration repository.
- [ ] Apply saved bindings to newly started matches.
- [ ] Add automated tests for configuration-backed paddle mappings.
- [ ] Add automated tests for duplicate and reserved-key validation.
- [ ] Add an integration test proving saved bindings control the intended paddles.

#### Acceptance criteria

```gherkin
Feature: Configurable paddle controls

  Scenario: Use saved paddle controls
    Given the player saves valid paddle control bindings in Options
    When the player starts a new match from the main menu
    And the player presses a saved movement key
    Then the intended paddle moves in the configured direction
```

## 5. Sprint Improvements

### What did not go well

- `<problem>`
- `<problem>`
