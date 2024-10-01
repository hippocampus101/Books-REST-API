package com.saycheese.BooksApiApplication.repositories;



import com.saycheese.BooksApiApplication.domain.entities.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {


}