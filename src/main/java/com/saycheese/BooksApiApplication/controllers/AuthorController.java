package com.saycheese.BooksApiApplication.controllers;


import com.saycheese.BooksApiApplication.domain.DTO.AuthorDTO;
import com.saycheese.BooksApiApplication.domain.entities.AuthorEntity;
import com.saycheese.BooksApiApplication.mappers.Mapper;
import com.saycheese.BooksApiApplication.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {


    public AuthorService authorsService;
    private final Mapper<AuthorEntity,AuthorDTO> authorMapper;

    public AuthorController(AuthorService authorsService,Mapper<AuthorEntity,AuthorDTO> authorMapper){
        this.authorsService = authorsService;
        this.authorMapper = authorMapper;
    }


    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorsService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDTO> listAuthors(){
        List<AuthorEntity> authors = authorsService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }


    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> findAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity>   foundAuthor = authorsService.findOne(id);

        return foundAuthor.map(authorEntity -> {
            AuthorDTO authorDTO = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);


        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }


    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDTO authorDto){
         if(!(authorsService.isExists(id))){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);

        AuthorEntity savedAuthorEntity = authorsService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity),HttpStatus.OK);
    }



    @PatchMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDTO> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody AuthorDTO authorDto)
    {
        if(!(authorsService.isExists(id))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor  =   authorsService.partialUpdate(id,  authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor),HttpStatus.OK);


    }


    @DeleteMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDTO> partialUpdate(@PathVariable("id") Long id){
        authorsService.delete(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "authors")
    public ResponseEntity<AuthorDTO> partialUpdate(){
        authorsService.deleteAll();
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
