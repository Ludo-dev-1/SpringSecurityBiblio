package fr.simplon.springsecuritybiblio;

import fr.simplon.springsecuritybiblio.controller.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityBiblioApplicationTests {

    @Autowired
    private BookController bookController;

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertThat(this.bookController).isNotNull();
    }

    @Test
    void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {

        String todoItemJsonInvalid = """
                {
                    "author": "George Orwell",
                    "category": "Science-Fiction"
                    "exemplaryNumber": 5,
                    "id": "2c5aa653-15b8-4925-8330-a10256a06e6f",
                    "title": "1984",
                    "yearPublished": 1949
                }
                """;

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoItemJsonInvalid))
                .andExpect(status().isBadRequest());
    }
}