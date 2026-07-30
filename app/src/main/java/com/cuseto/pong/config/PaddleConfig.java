package com.cuseto.pong.config;

public record PaddleConfig(
    int width,
    int height,
    int inset,
    float speed
) {}