package fr.simplon.springsecuritybiblio.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}