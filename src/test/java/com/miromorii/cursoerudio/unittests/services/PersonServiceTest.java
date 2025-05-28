package com.miromorii.cursoerudio.unittests.services;

import com.miromorii.cursoerudio.data.dto.v1.PersonDTO;
import com.miromorii.cursoerudio.exceptions.RequiredObjectIsNullException;
import com.miromorii.cursoerudio.models.Person;
import com.miromorii.cursoerudio.repositories.PersonRepository;
import com.miromorii.cursoerudio.services.PersonService;
import com.miromorii.cursoerudio.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1); //mocka uma person
        when(repository.findById(1L)).thenReturn(Optional.of(person)); //define como o metodo deve se comportar

        PersonDTO result = service.findById(1L);  // faz a chamada de fato e guarda o resultado para as consultas a seguir

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //obtem os links e verifica se existe algum com as propriedades definidas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/persons/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/persons/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void findAll() {
        List<Person> person = input.mockEntityList();
        when(repository.findAll()).thenReturn(person);
        List<PersonDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(person.size(), result.size());
    }

    @Test
    void create() {
        Person person = input.mockEntity(1); //mocka a pessoa que vai ser salva no repository
        PersonDTO personDTO = input.mockDTO(1);
        when(repository.save(person)).thenReturn(person); //simula a persistencia da person

        var result = service.create(personDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //obtem os links e verifica se existe algum com as propriedades definidas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/persons/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/persons/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }


    @Test
    void update() {
        Person person = input.mockEntity(1);
        PersonDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(person);

        PersonDTO result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //obtem os links e verifica se existe algum com as propriedades definidas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/persons/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/persons/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/persons/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        when(repository.findById(person.getId())).thenReturn(Optional.of(person));

        service.delete(person.getId());
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