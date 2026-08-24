package com.cuseto.pong.game.update;

import com.cuseto.pong.game.session.GameSession;

/**
 * A {@link GameUpdater} that detects when the ball has crossed the arena's
 * left or right inner boundary, awards a point to the opposing player, and
 * starts the next round.
 *
 * <p>Crossing is detected by comparing the ball's x-coordinate between two
 * consecutive updates: a point is scored on the frame where the ball's
 * position moves from one side of a scoring boundary to the other. The
 * first update after construction only records a baseline position and
 * never scores, since there is no previous position yet to compare against.
 *
 * <p>Does nothing while the game is not {@linkplain GameSession#isGameOn() running}.
 * If the resulting score reaches the winning score, the session ends the
 * match instead of starting a new round; see
 * {@link GameSession#incrementLeftScore()} and
 * {@link GameSession#incrementRightScore()}.
 */
public final class ScoreGameUpdater implements GameUpdater {
    private Double previousX;

    /**
     * Creates a new score updater with no recorded previous ball position;
     * the first {@link #update(GameSession, double)} call only establishes
     * the baseline and does not score.
     */
    public ScoreGameUpdater() {
    }

    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
        if (gameSession.isGameOn()) {
            double currentX = gameSession.ball().x();

            // First update: establish the initial position.
            if (previousX == null) {
                previousX = currentX;
                return;
            }

            double rightScoringBoundary = gameSession.arena().innerRightBoundary();
            double leftScoringBoundary = gameSession.arena().innerLeftBoundary();

            // Ball crossed the right boundary: left player scores.
            if (previousX <= rightScoringBoundary &&
                currentX > rightScoringBoundary) {
                gameSession.incrementLeftScore();
                resetForNextRoundIfNeeded(gameSession);
            }

            // Ball crossed the left boundary: right player scores.
            else if (previousX >= leftScoringBoundary &&
                    currentX < leftScoringBoundary) {
                gameSession.incrementRightScore();
                resetForNextRoundIfNeeded(gameSession);
            }

            previousX = gameSession.ball().x();
        }

    }

    private void resetForNextRoundIfNeeded(GameSession gameSession) {
        if (!gameSession.isGameOver()) {
            gameSession.resetRound();
        }
    }
}
