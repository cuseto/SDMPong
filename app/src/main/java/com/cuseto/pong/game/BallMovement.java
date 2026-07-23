package com.cuseto.pong.game;

import com.cuseto.pong.model.Circle;
import com.cuseto.pong.model.Rectangle;

public final class BallMovement {
    private BallMovement() {
    }

    public static Circle move(
        Circle ball,
        double elapsedSeconds,
        int minY,
        int maxY,
        Rectangle leftPaddle,
        Rectangle rightPaddle
    ) {
        double newX = ball.startPosX() + ball.velocityX() * elapsedSeconds;
        double newY = ball.startPosY() + ball.velocityY() * elapsedSeconds;
        double velocityY = ball.velocityY();

        if (newY <= minY || newY >= maxY) {
            if (newY <= minY) newY = minY + (minY - newY);
            if (newY >= maxY) newY = maxY - (newY - maxY);
            velocityY *= -1;
        }


        double minX = leftPaddle.startPosX() + leftPaddle.width();
        double maxX = rightPaddle.startPosX();

        if (newX <= minX & ball.startPosX() > minX) {
            // take the Y range that the ball covers
            // while passing the minX line
            double m = (newY - ball.startPosY()) / (newX - ball.startPosX());
            double topRange = m * minX + ball.startPosY() - m * ball.startPosX() - ball.radius() * Math.sqrt(Math.pow(m, 2) + 1);
            double bottomRange = m * minX + ball.startPosY() - m * ball.startPosX() + ball.radius() * Math.sqrt(Math.pow(m, 2) + 1);

            if (
                (topRange > leftPaddle.startPosY() & topRange < leftPaddle.startPosY() + leftPaddle.height()) ||
                (bottomRange > leftPaddle.startPosY() & bottomRange < leftPaddle.startPosY() + leftPaddle.height())
            ) {
                newX = minX + (minX - newX);
            } 
        }

        if (newX <= minX & ball.startPosX() > minX) {
            // take the Y range that the ball covers
            // while passing the minX line
            double m = (newY - ball.startPosY()) / (newX - ball.startPosX());
            double topRange = m * minX + ball.startPosY() - m * ball.startPosX() - ball.radius() * Math.sqrt(Math.pow(m, 2) + 1);
            double bottomRange = m * minX + ball.startPosY() - m * ball.startPosX() + ball.radius() * Math.sqrt(Math.pow(m, 2) + 1);

            if (
                (topRange > leftPaddle.startPosY() & topRange < leftPaddle.startPosY() + leftPaddle.height()) ||
                (bottomRange > leftPaddle.startPosY() & bottomRange < leftPaddle.startPosY() + leftPaddle.height())
            ) {
                newX = minX + (minX - newX);
            }
        }

        if (ball.startPosX() < maxX & newX >= maxX) {
            // take the Y range that the ball covers
            // while passing the maxX line
            double m = (newY - ball.startPosY()) / (newX - ball.startPosX());
            double topRange = m * maxX + ball.startPosY() - m * ball.startPosX() - ball.radius() * Math.sqrt(Math.pow(m, 2) + 1);
            double bottomRange = m * maxX + ball.startPosY() - m * ball.startPosX() + ball.radius() * Math.sqrt(Math.pow(m, 2) + 1);

            if (
                (topRange > rightPaddle.startPosY() & topRange < rightPaddle.startPosY() + rightPaddle.height()) ||
                (bottomRange > rightPaddle.startPosY() & bottomRange < rightPaddle.startPosY() + rightPaddle.height())
            ) {
                newX = maxX - (newX - maxX);
            }
        }


        return new Circle(newX, newY, ball.radius(), ball.velocityX(), velocityY);
    }
}
