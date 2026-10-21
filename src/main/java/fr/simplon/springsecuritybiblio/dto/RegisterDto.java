package fr.simplon.springsecuritybiblio.dto;

public record RegisterDto(
        String name,
        String email,
        String password
) {
}