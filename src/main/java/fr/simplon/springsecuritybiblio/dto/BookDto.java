package fr.simplon.springsecuritybiblio.dto;

public record BookDto(
        String title,
        String author,
        String category,
        Integer yearPublished,
        Integer exemplaryNumber
) {
}