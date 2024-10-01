package com.saycheese.BooksApiApplication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saycheese.BooksApiApplication.TestDataUtil;
import com.saycheese.BooksApiApplication.domain.DTO.BookDto;
import com.saycheese.BooksApiApplication.domain.entities.BookEntity;
import com.saycheese.BooksApiApplication.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;




    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoB(null);
        String createBookJSON = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoB(null);
        String createBookJSON = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );

    }


    @Test
    public void testThatListBookReturnsHttpStatus200Ok() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoB(null);
        String createBookJSON = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books" )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatListBookReturnsBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityB(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value(bookEntity.getTitle())
        );

    }


    @Test
    public void testThatGetBookReturnsHttpStatus200OkWhenBookExists () throws Exception {

        BookEntity bookEntity = TestDataUtil.createTestBookEntityB(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenBookDoesNotExists () throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityB(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }



    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok () throws Exception {

        BookEntity testBookEntityB = TestDataUtil.createTestBookEntityB(null);
        BookEntity savedBookEntityB  = bookService.createUpdateBook(
                testBookEntityB.getIsbn(), testBookEntityB
        );

        BookDto bookDto = TestDataUtil.createTestBookDtoB(null);

        bookDto.setIsbn(savedBookEntityB.getIsbn());



        String bookJSON = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + savedBookEntityB.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }



    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {

        BookEntity testBookEntityB = TestDataUtil.createTestBookEntityB(null);
        BookEntity savedBookEntityB  = bookService.createUpdateBook(
                testBookEntityB.getIsbn(), testBookEntityB
        );

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);


        testBookB.setIsbn(savedBookEntityB.getIsbn());
        testBookB.setTitle("Updated");

        String bookJSON = objectMapper.writeValueAsString(testBookB);



        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntityB.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Updated")
        );

    }


    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200Ok   () throws Exception {

        BookEntity testBookEntityB = TestDataUtil.createTestBookEntityB(null);
        BookEntity savedBookEntityB  = bookService.createUpdateBook(
                testBookEntityB.getIsbn(), testBookEntityB
        );

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);
        testBookB.setTitle("updated");
        String bookJSON = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBookEntityB.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }



    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook   () throws Exception {

        BookEntity testBookEntityB = TestDataUtil.createTestBookEntityB(null);
        BookEntity savedBookEntityB  = bookService.createUpdateBook(
                testBookEntityB.getIsbn(), testBookEntityB
        );

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);
        testBookB.setTitle("updated");
        String bookJSON = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBookEntityB.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBookEntityB.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("updated")
        );

    }

    @Test
    public void testThatDeleteBookNonExistingBookReturnHttp204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/jbhib" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    public void testThatDeleteBookExistingBookReturnHttp204NoContent() throws Exception {
        BookEntity testBookEntityB = TestDataUtil.createTestBookEntityB(null);
        BookEntity savedBookEntityB  = bookService.createUpdateBook(
                testBookEntityB.getIsbn(), testBookEntityB
        );
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/books/" + savedBookEntityB.getIsbn()     )
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }















}
