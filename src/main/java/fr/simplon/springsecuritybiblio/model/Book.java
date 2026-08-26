package fr.simplon.springsecuritybiblio.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book")
public class Book {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false)
    private String author;

    @NonNull
    @Column(nullable = false)
    private String category;

    @NonNull
    @Column(nullable = false)
    private Integer yearPublished;

    @NonNull
    @Column(nullable = false)
    private Integer exemplaryNumber;

}
