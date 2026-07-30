package com.cuseto.pong.config;

public record BallConfig(
    int radius,
    float initialVelocityX,
    float initialVelocityY
) {}