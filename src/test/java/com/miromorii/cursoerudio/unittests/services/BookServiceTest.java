package com.miromorii.cursoerudio.unittests.services;

import com.miromorii.cursoerudio.data.dto.v1.BookDTO;
import com.miromorii.cursoerudio.exceptions.RequiredObjectIsNullException;
import com.miromorii.cursoerudio.models.Book;
import com.miromorii.cursoerudio.repositories.BookRepository;
import com.miromorii.cursoerudio.services.BookService;
import com.miromorii.cursoerudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;
    
    @InjectMocks
    private BookService service;
    
    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1); //mocka uma book
        when(repository.findById(1L)).thenReturn(Optional.of(book)); //define como o metodo deve se comportar

        BookDTO result = service.findById(1L);  // faz a chamada de fato e guarda o resultado para as consultas a seguir

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //obtem os links e verifica se existe algum com as propriedades definidas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/books/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Tittle Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDate.of(2020, 1, 1), result.getLaunchDate());
        assertEquals(10, result.getPrice());
    }

    @Test
    @Disabled("REASON: still under development")
    void findAll() {
        List<Book> book = input.mockEntityList();
        when(repository.findAll()).thenReturn(book);
        List<BookDTO> result = new ArrayList<>();//service.findAll();

        assertNotNull(result);
        assertEquals(book.size(), result.size());
    }

    @Test
    void create() {
        Book book = input.mockEntity(1); //mocka a pessoa que vai ser salva no repository
        BookDTO bookDTO = input.mockDTO(1);
        when(repository.save(book)).thenReturn(book); //simula a persistencia da book

        var result = service.create(bookDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //obtem os links e verifica se existe algum com as propriedades definidas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/books/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Tittle Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDate.of(2020, 1, 1), result.getLaunchDate());
        assertEquals(10, result.getPrice());
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        BookDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(book);

        BookDTO result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //obtem os links e verifica se existe algum com as propriedades definidas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/books/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Tittle Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDate.of(2020, 1, 1), result.getLaunchDate());
        assertEquals(10, result.getPrice());
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        when(repository.findById(book.getId())).thenReturn(Optional.of(book));

        service.delete(book.getId());
    }

    @Test
    void createShouldThrowRequiredObjectIsNullExceptionWhenDTOIsNull(){
        RequiredObjectIsNullException exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "Can't persist null object";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void updateShouldThrowRequiredObjectIsNullExceptionWhenDTOIsNull(){
        RequiredObjectIsNullException exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "Can't persist null object";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}