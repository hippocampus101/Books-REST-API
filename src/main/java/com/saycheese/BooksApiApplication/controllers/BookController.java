package com.saycheese.BooksApiApplication.controllers;


import com.saycheese.BooksApiApplication.domain.DTO.BookDto;
import com.saycheese.BooksApiApplication.domain.entities.BookEntity;
import com.saycheese.BooksApiApplication.mappers.Mapper;
import com.saycheese.BooksApiApplication.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    private Mapper<BookEntity,BookDto> bookMapper;
    private BookService bookService;


    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;

        this.bookService = bookService;
    }



    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        Boolean bookExists = bookService.isExists(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity );
        if(bookExists){
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK );
        } else {
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }

    }

    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable){
        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook (@PathVariable("isbn") String isbn){
        Optional<BookEntity> foundBook =   bookService.findOne(isbn);
         return  foundBook.map(bookEntity -> {
            BookDto foundBookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(foundBookDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){


        Boolean bookExists = bookService.isExists(isbn);

        if(!bookExists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            BookEntity bookEntity = bookMapper.mapFrom(bookDto);
            BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
            BookDto updatedBookDto = bookMapper.mapTo(updatedBookEntity);
            return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
        }
    }


    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook (@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

























}
