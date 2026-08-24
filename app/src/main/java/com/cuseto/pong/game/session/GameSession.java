package com.cuseto.pong.game.session;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.model.Arena;
import com.cuseto.pong.game.model.Ball;
import com.cuseto.pong.game.model.Paddle;
import com.cuseto.pong.game.model.view.BallView;
import com.cuseto.pong.game.model.view.PaddleView;

/**
 * Holds the full mutable state of a Pong match: the arena, both paddles,
 * the ball, the score, and the match's {@link GameStatus}.
 *
 * <p>The arena, paddle sizes/speed, and ball's initial velocity are derived
 * once from {@link AppConfig} at construction and do not change for the
 * lifetime of the session; paddle/ball positions and the score change as
 * the match is played.
 */
public final class GameSession {
    private final Arena arena;
    private final Paddle leftPaddle;
    private final Paddle rightPaddle;
    private final Ball ball;
    private final double initialBallVelocityX;
    private final double initialBallVelocityY;
    private final BooleanSupplier directionSupplier;
    private final boolean ballSpeedIncreaseEnabled;
    private final int winningScore;
    private GameStatus gameStatus;
    private int leftScore;
    private int rightScore;
    private Player winner;
    private Consumer<Player> onGameOverAction = null;

    /**
     * Builds a new session from configuration: computes the arena and both
     * paddles' starting positions, places the ball at the arena's center
     * with its configured initial velocity, and sets the match status to
     * {@link GameStatus#RUNNING}.
     *
     * @param appConfig the application configuration to derive the arena, paddle, and ball setup from
     */
    public GameSession(AppConfig appConfig) {
        this(appConfig, () -> ThreadLocalRandom.current().nextBoolean());
    }

    GameSession(AppConfig appConfig, BooleanSupplier directionSupplier) {
        /**
         * NOTE:
         * This constructor is needed for test purposes:
         * in this way we can check the randomization of the initial ball direction
         * call GameSession(AppConfig appConfig) for non-test code
         */
        this.directionSupplier = Objects.requireNonNull(directionSupplier);
        this.arena = getArena(appConfig);
        this.leftPaddle = getLeftPaddle(appConfig, arena);
        this.rightPaddle = getRightPaddle(appConfig, arena);

        this.ballSpeedIncreaseEnabled = appConfig.gamePage().ball().speedIncreaseEnabled();
        this.winningScore = appConfig.gamePage().winningScore();

        this.ball = getBall(appConfig, arena);
        this.initialBallVelocityX = ball.velocityX();
        this.initialBallVelocityY = ball.velocityY();
        this.gameStatus = GameStatus.RUNNING;
    }

    private Arena getArena(AppConfig appConfig) {
        int width = appConfig.viewport().screenWidth() - 2 * appConfig.gamePage().arena().spacingOther();
        int height = appConfig.viewport().screenHeight() -
            appConfig.gamePage().arena().spacingTop() -
            appConfig.gamePage().arena().spacingOther();
        int anchorX = appConfig.gamePage().arena().spacingOther();
        int anchorY = appConfig.gamePage().arena().spacingTop();

        return new Arena(
            width, 
            height, 
            anchorX, 
            anchorY, 
            appConfig.gamePage().arena().boundaryThickness()
        );
    }

    private Paddle getLeftPaddle(AppConfig appConfig, Arena arena) {
        double x = arena.innerLeftBoundary() + appConfig.gamePage().paddle().inset();
        double y = arena.innerTopBoundary() + ((arena.innerHeight() - appConfig.gamePage().paddle().height()) / 2);
        return new Paddle(
            x,
            y, 
            appConfig.gamePage().paddle().width(),
            appConfig.gamePage().paddle().height(),
            appConfig.gamePage().paddle().speed()
        );
    }

    private Paddle getRightPaddle(AppConfig appConfig, Arena arena) {
        double x = arena.innerRightBoundary() -
            appConfig.gamePage().paddle().inset() -
            appConfig.gamePage().paddle().width();
        double y = arena.innerTopBoundary() + ((arena.innerHeight() - appConfig.gamePage().paddle().height()) / 2);
        return new Paddle(
            x,
            y, 
            appConfig.gamePage().paddle().width(),
            appConfig.gamePage().paddle().height(),
            appConfig.gamePage().paddle().speed()
        );
    }

    private double initialPaddleY(Paddle paddle) {
        return arena.innerTopBoundary() + ((arena.innerHeight() - paddle.height()) / 2.0);
    }

    private Ball getBall(AppConfig appConfig, Arena arena) {
        double x = initialBallX(arena);
        double y = initialBallY(arena);
        return new Ball(
            x,
            y,
            appConfig.gamePage().ball().radius(),
            appConfig.gamePage().ball().initialVelocityX() * randomDirectionMultiplier(),
            appConfig.gamePage().ball().initialVelocityY() * randomDirectionMultiplier()
        );
    }

    private int randomDirectionMultiplier() {
        return directionSupplier.getAsBoolean() ? 1 : -1;
    }

    private double initialBallX(Arena arena) {
        return arena.innerLeftBoundary() + arena.innerWidth() / 2.0;
    }

    private double initialBallY(Arena arena) {
        return arena.innerTopBoundary() + arena.innerHeight() / 2.0;
    }

    /**
     * Resets the ball to the arena's center with its configured initial
     * velocity, and returns both paddles to their vertically-centered
     * starting positions. Used to begin a new round after a point is
     * scored.
     *
     * <p>Does not affect the score or {@link GameStatus}.
     */
    public void resetRound() {
        ball.setX(initialBallX(arena));
        ball.setY(initialBallY(arena));
        ball.setVelocity(initialBallVelocityX, initialBallVelocityY);

        leftPaddle.moveToY(initialPaddleY(leftPaddle));
        rightPaddle.moveToY(initialPaddleY(rightPaddle));
    }

    /**
     * Starts a fresh match: resets both scores to zero, clears any winner,
     * sets the status to {@link GameStatus#RUNNING}, and resets the round
     * via {@link #resetRound()}.
     */
    public void startNewMatch() {
        leftScore = 0;
        rightScore = 0;
        winner = null;
        resumeGame();
        resetRound();
    }

    /**
     * Registers a callback to be invoked with the winning {@link Player}
     * when the match ends. Replaces any previously registered callback.
     *
     * @param action the callback to invoke on game over, or {@code null} to clear it
     */
    public void setGameOverAction(Consumer<Player> action) {
        onGameOverAction = action;
    }

    // getter
    /**
     * Returns a read-only view of the ball.
     *
     * @return a read-only view of the ball's current state
     */
    public BallView ballInfo() {
        return this.ball;
    }

    /**
     * Returns a read-only view of the left paddle.
     *
     * @return a read-only view of the left paddle's current state
     */
    public PaddleView leftPaddleInfo() {
        return this.leftPaddle;
    }

    /**
     * Returns a read-only view of the right paddle.
     *
     * @return a read-only view of the right paddle's current state
     */
    public PaddleView rightPaddleInfo() {
        return this.rightPaddle;
    }

    /**
     * Returns the arena this match is played in.
     *
     * @return the arena
     */
    public Arena arena() {
        return this.arena;
    }

    /**
     * Returns the mutable ball.
     *
     * @return the mutable ball; intended for use by physics/update code rather than rendering
     */
    public Ball ball() {
        return this.ball;
    }

    /**
     * Returns the mutable left paddle.
     *
     * @return the mutable left paddle; intended for use by physics/update code rather than rendering
     */
    public Paddle leftPaddle() {
        return this.leftPaddle;
    }

    /**
     * Returns the mutable right paddle.
     *
     * @return the mutable right paddle; intended for use by physics/update code rather than rendering
     */
    public Paddle rightPaddle() {
        return this.rightPaddle;
    }

    /**
     * Returns the left player's current score.
     *
     * @return the left player's current score
     */
    public int leftScore() {
        return leftScore;
    }

    /**
     * Returns the right player's current score.
     *
     * @return the right player's current score
     */
    public int rightScore() {
        return rightScore;
    }

    /**
     * Returns the score needed to win the match.
     *
     * @return the score a player must reach to win the match
     */
    public int winningScore() {
        return winningScore;
    }

    public boolean isBallSpeedIncreaseEnabled() {
        return ballSpeedIncreaseEnabled;
    }

    /**
     * Returns the winning player, if the match has finished.
     *
     * @return the winning player, or {@code null} if the match has not finished yet
     */
    public Player winner() {
        return winner;
    }

    /**
     * Awards a point to the left player. Does nothing if the match has
     * already {@linkplain #isGameOver() ended}. If this point reaches the
     * winning score, sets the left player as {@link #winner()} and ends the
     * match via {@link #setGameOver()}.
     */
    public void incrementLeftScore() {
        if (!isGameOver()) {
            leftScore++;
            if (leftScore == winningScore) {
                winner = Player.LEFT;
                setGameOver();
            }
        }
    }

    /**
     * Awards a point to the right player. Does nothing if the match has
     * already {@linkplain #isGameOver() ended}. If this point reaches the
     * winning score, sets the right player as {@link #winner()} and ends
     * the match via {@link #setGameOver()}.
     */
    public void incrementRightScore() {
        if (!isGameOver()) {
            rightScore++;
            if (rightScore == winningScore) {
                winner = Player.RIGHT;
                setGameOver();
            }
        }
    }

    /** Suspends the match by setting its status to {@link GameStatus#PAUSED}. */
    public void pauseGame() {
        gameStatus = GameStatus.PAUSED;
    }

    /** Sets the match status to {@link GameStatus#RUNNING}, e.g. to resume after a pause. */
    public void resumeGame() {
        gameStatus = GameStatus.RUNNING;
    }

    /**
     * Ends the match by setting its status to {@link GameStatus#FINISHED},
     * then invokes the game-over callback registered via
     * {@link #setGameOverAction(Consumer)}, if any, passing the current
     * {@link #winner()}.
     */
    public void setGameOver() {
        gameStatus = GameStatus.FINISHED;
        if (onGameOverAction != null) onGameOverAction.accept(winner);
    }
    
    /**
     * Checks whether the match is currently running.
     *
     * @return {@code true} if the match status is {@link GameStatus#RUNNING}
     */
    public boolean isGameOn() {
        return gameStatus == GameStatus.RUNNING;
    }

    /**
     * Checks whether the match is currently paused.
     *
     * @return {@code true} if the match status is {@link GameStatus#PAUSED}
     */
    public boolean isGamePaused() {
        return gameStatus == GameStatus.PAUSED;
    }

    /**
     * Checks whether the match has finished.
     *
     * @return {@code true} if the match status is {@link GameStatus#FINISHED}
     */
    public boolean isGameOver() {
        return gameStatus == GameStatus.FINISHED;
    }
}
