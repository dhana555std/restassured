package org.example.models;

public record PostRequest(
    String title,
    String body,
    Integer userId
) {}

