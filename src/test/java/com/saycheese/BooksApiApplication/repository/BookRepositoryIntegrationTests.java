package com.saycheese.BooksApiApplication.repository;

import com.saycheese.BooksApiApplication.TestDataUtil;
import com.saycheese.BooksApiApplication.domain.entities.AuthorEntity;
import com.saycheese.BooksApiApplication.domain.entities.BookEntity;
import com.saycheese.BooksApiApplication.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private final BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        BookEntity book = TestDataUtil.createTestBookA(author);

        underTest.save(book);
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        BookEntity bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);
        BookEntity bookB = TestDataUtil.createTestBookEntityB(author);
        underTest.save(bookB);
        BookEntity bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookC);

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        BookEntity bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);

        bookA.setTitle("UPDATED");
        underTest.save(bookA);
        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        BookEntity bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);
        underTest.deleteById(bookA.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}
