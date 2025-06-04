package com.miromorii.cursoerudio.integrationtests.controllers.cors.withjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miromorii.cursoerudio.config.TestConfig;
import com.miromorii.cursoerudio.integrationtests.dto.PersonDTO;
import com.miromorii.cursoerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerCorsWithJsonTest extends AbstractIntegrationTest {
    
    private static ObjectMapper mapper;
    private static RequestSpecification specification;
    private static PersonDTO person;

    @BeforeAll
    static void beforeAll() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = mockPersonDTO();
    }
    
    @Test
    @Order(1)
    void createPersonWithValidOriginShouldReturnCreatedPerson() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath("/api/persons/v1")
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .setBody(person)
                .build();

        //when
        String person = given(specification)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO created = mapper.readValue(person, PersonDTO.class);
        PersonControllerCorsWithJsonTest.person = created;

        //then
        assertTrue(PersonControllerCorsWithJsonTest.person.getId() > 0);
        assertEquals("John", PersonControllerCorsWithJsonTest.person.getFirstName());
        assertEquals("Doe", PersonControllerCorsWithJsonTest.person.getLastName());
        assertEquals("123 Main St", PersonControllerCorsWithJsonTest.person.getAddress());
        assertEquals("Male", PersonControllerCorsWithJsonTest.person.getGender());
        assertTrue(PersonControllerCorsWithJsonTest.person.isEnabled());
    }

    @Test
    @Order(2)
    void createPersonWithInvalidOriginShouldFail() {
        //given
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_INVALID)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath("/api/persons/v1")
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .setBody(person)
                .build();

        //when
        String response = given(specification)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        //then
        assertEquals("Invalid CORS request", response);
    }

    @Test
    @Order(3)
    void findByIdWithValidOriginShouldReturnFoundPerson() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath("/api/persons/v1")
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .addPathParam("id", person.getId())
                .build();

        //when
        String found = given(specification)
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        PersonDTO foundDTO = mapper.readValue(found, PersonDTO.class);

        //then
        assertEquals(foundDTO.getId(), PersonControllerCorsWithJsonTest.person.getId());
        assertEquals(foundDTO.getFirstName(), PersonControllerCorsWithJsonTest.person.getFirstName());
        assertEquals(foundDTO.getLastName(), PersonControllerCorsWithJsonTest.person.getLastName());
        assertEquals(foundDTO.getAddress(), PersonControllerCorsWithJsonTest.person.getAddress());
        assertEquals(foundDTO.getGender(), PersonControllerCorsWithJsonTest.person.getGender());
        assertEquals(foundDTO.isEnabled(), PersonControllerCorsWithJsonTest.person.isEnabled());
    }

    @Test
    @Order(4)
    void findByIdWithInvalidOriginShouldFail() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_INVALID)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath("/api/persons/v1")
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .addPathParam("id", person.getId())
                .build();

        String found = given(specification)
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", found);
    }

    private static PersonDTO mockPersonDTO() {
        var dto = new PersonDTO();

        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAddress("123 Main St");
        dto.setGender("Male");
        dto.setEnabled(true);

        return dto;
    }
}