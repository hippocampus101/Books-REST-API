package com.saycheese.BooksApiApplication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saycheese.BooksApiApplication.TestDataUtil;
import com.saycheese.BooksApiApplication.domain.DTO.AuthorDTO;
import com.saycheese.BooksApiApplication.domain.entities.AuthorEntity;
import com.saycheese.BooksApiApplication.services.AuthorService;
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
public class AuthorControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorService authorService;



    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService ) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;

    }

    @Test
    public void  testThatCreateAuthorSuccessfullyReturnsHTTP201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJSON = objectMapper.writeValueAsString(testAuthorA);


        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void  testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJSON = objectMapper.writeValueAsString(testAuthorA);


        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("80")

        );

    }



    @Test
    public void testThatListAuthorsReturnsListOfAuthors () throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();

        authorService.save(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value("80")

        );
    }

    @Test
    public void testThaGetAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {

        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();

        authorService.save(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1 ")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThaGetAuthorReturnsHttpStatus404WhenAuthorDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99  ")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatgetAuthorReturnsAuthorWhenAuthorExists () throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();

        authorService.save(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value("1")

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("80")

        );
    }


    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenAuthorDoesNotExists() throws Exception {
        AuthorDTO testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorJSON = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put ("/authors/99  ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)

        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        AuthorDTO testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorJSON = objectMapper.writeValueAsString(testAuthorDto);
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put ("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFullUpdateAuthorUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);
        AuthorEntity authorDto = TestDataUtil.createTestAuthorB();

        authorDto.setId(savedAuthor.getId());

        String authorDtoUpdateJSON = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put ("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())

        );


    }


    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus200Ok () throws Exception {

        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        AuthorDTO testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("updated   ");


        String authorJSON = objectMapper.writeValueAsString(testAuthorDto);
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch ("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());

    }



    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor () throws Exception {

        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        AuthorDTO testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("updated");


        String authorJSON = objectMapper.writeValueAsString(testAuthorDto);
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch ("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("updated")

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedAuthor.getAge())

        );


    }
    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete ("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {

        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.delete ("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()   );

    }






}
