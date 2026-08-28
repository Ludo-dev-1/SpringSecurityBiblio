package fr.simplon.springsecuritybiblio;

import fr.simplon.springsecuritybiblio.controller.AuthController;
import fr.simplon.springsecuritybiblio.model.Book;
import fr.simplon.springsecuritybiblio.model.Users;
import fr.simplon.springsecuritybiblio.repository.BookRepository;
import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityBiblioApplicationTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthController authController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mvc;


    @Test
    void contextLoads() {
        assertThat(this.authController).isNotNull();
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ROLE_USER"})
    public void shouldGetBooks() throws Exception {
        this.mvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ROLE_USER"})
    void shouldCantDeleteBooks() throws Exception {
        Book book = bookRepository.findAll().get(0);
        mvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(authorities = {"SCOPE_ROLE_ADMIN"})
    public void shouldPostBooks() throws Exception {
        String todoItemJson = """
                	{
                               "title": "test post livre,",
                               "author": "livre test",
                               "category": "test livre",
                               "yearPublished": 1918,
                               "exemplaryNumber": 1914
                             }
                """;
        this.mvc.perform(post("/api/books")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(todoItemJson))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ROLE_ADMIN"})
    public void shouldPutBooks() throws Exception {
        String todoItemJson = """
                	{
                               "title": "test put livre,",
                               "author": "livre test",
                               "category": "test livre",
                               "yearPublished": 1918,
                               "exemplaryNumber": 1914
                             }
                """;

        Book book = bookRepository.findAll().get(0);
        this.mvc.perform(put("/api/books/{id}", book.getId())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(todoItemJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ROLE_ADMIN"})
    void shouldDeleteBooks() throws Exception {
        Book book = bookRepository.findAll().get(2);
        mvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegister() throws Exception {
        String uuid = UUID.randomUUID().toString();

        String identifiant = """
                    {
                         "name":"user-%s",
                         "email":"%s@email.fr",
                         "password":"userMdp",
                         "authorities":[
                            {
                                "authority":"ROLE_USER"
                            }
                        ]
                    }
                """.formatted(uuid, uuid);
        this.mvc.perform(post("/api/auth/register")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(identifiant))
                .andDo(print())
                .andExpect(status().isOk());
        Users user = usersRepository.findByEmail(uuid + "@email.fr")
                .orElse(null);


        assertThat(user).isNotNull();

        assertThat(passwordEncoder.matches(
                "userMdp",
                user.getPassword()
        )).isTrue();
    }

    @Test
    public void shouldAuthenticateAndReturnToken() throws Exception {
        String userJson = """
                   {
                       "email":"admin@email.fr",
                       "password":"adminMdp"
                   }
                """;

        this.mvc.perform(post("/api/auth/login")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email").value("admin@email.fr"));
    }

    @Test
    public void shouldFailAuthenticationWithInvalidCredentials() throws Exception {
        String invalidUserJson = """
                    {
                        "email": "invalid_user@email.fr",
                        "password": "wrong_password"
                    }
                """;

        this.mvc.perform(post("/api/auth/login")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(invalidUserJson))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


}