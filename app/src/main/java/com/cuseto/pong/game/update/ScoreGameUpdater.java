package com.cuseto.pong.game.update;

import com.cuseto.pong.game.session.GameSession;

public final class ScoreGameUpdater implements GameUpdater {
    private Double previousX;

    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
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
        }

        // Ball crossed the left boundary: right player scores.
        else if (previousX >= leftScoringBoundary &&
                 currentX < leftScoringBoundary) {
            gameSession.incrementRightScore();
        }

        previousX = currentX;
    }
}
