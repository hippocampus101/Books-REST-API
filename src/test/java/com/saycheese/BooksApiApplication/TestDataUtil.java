package com.saycheese.BooksApiApplication;

import com.saycheese.BooksApiApplication.domain.DTO.AuthorDTO;
import com.saycheese.BooksApiApplication.domain.DTO.BookDto;
import com.saycheese.BooksApiApplication.domain.entities.AuthorEntity;
import com.saycheese.BooksApiApplication.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }
    public static AuthorDTO createTestAuthorDtoA() {
        return AuthorDTO.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("HC Andersen")
                .age(70)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Kim Larsen")
                .age(20)
                .build();
    }

    public static BookEntity createTestBookA(AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookEntityB (AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-1")
                .title("Harry Potter")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoB(AuthorDTO author) {
        return BookDto.builder()
                .isbn("978-1-2345-6 789-1")
                .title("Harry Potter")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookC(AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-2")
                .title("Biblen")
                .author(author)
                .build();
    }
}
