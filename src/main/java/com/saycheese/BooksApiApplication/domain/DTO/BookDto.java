package com.saycheese.BooksApiApplication.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {


    private String isbn;

    private String title;

    private AuthorDTO author;
}
