package com.saycheese.BooksApiApplication.repositories;


import com.saycheese.BooksApiApplication.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<BookEntity,String>, PagingAndSortingRepository<BookEntity,String> {
}
