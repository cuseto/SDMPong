package com.cuseto.pong.game.update;

import com.cuseto.pong.game.session.GameSession;

public final class ScoreGameUpdater implements GameUpdater {
    private Double previousX;

    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
        if (gameSession.isMatchOver()) {
            return;
        }

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

    private void resetForNextRoundIfNeeded(GameSession gameSession) {
        if (!gameSession.isMatchOver()) {
            gameSession.resetRound();
        }
    }
}
