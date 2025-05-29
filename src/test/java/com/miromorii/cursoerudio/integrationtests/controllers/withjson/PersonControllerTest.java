package com.miromorii.cursoerudio.integrationtests.controllers.withjson;

import com.miromorii.cursoerudio.config.TestConfig;
import com.miromorii.cursoerudio.integrationtests.dto.PersonDTO;
import com.miromorii.cursoerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.miromorii.cursoerudio.models.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerTest extends AbstractIntegrationTest {
    
    @Test
    void createPersonWithValidOriginShouldReturnCreatedPerson() {

        PersonDTO personDTO = given()
                .basePath("/api/persons/v1")
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .port(TestConfig.SERVER_PORT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mockPersonDTO())
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonDTO.class);

        assertNotNull(personDTO);
        assertEquals("John", personDTO.getFirstName());
        assertEquals("Doe", personDTO.getLastName());
        assertEquals("123 Main St", personDTO.getAddress());
        assertEquals("Male", personDTO.getGender());
    }

    @Test
    void createPersonWithInvalidOriginShouldFail() {

        String response = given()
                .basePath("/api/persons/v1")
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_INVALID)
                .port(TestConfig.SERVER_PORT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mockPersonDTO())
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", response);
    }

    @Test
    void findByIdWithValidOriginShouldReturnFoundPerson() {
        PersonDTO createdPerson = given()
                .basePath("/api/persons/v1")
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .port(TestConfig.SERVER_PORT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mockPersonDTO())
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonDTO.class);

        PersonDTO found = given()
                .basePath("/api/persons/v1/{id}")
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .port(TestConfig.SERVER_PORT)
                .pathParam("id", createdPerson.getId())
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonDTO.class);

        assertNotNull(found);
        assertEquals(createdPerson.getId(), found.getId());
        assertEquals(createdPerson.getFirstName(), found.getFirstName());
        assertEquals(createdPerson.getLastName(), found.getLastName());
        assertEquals(createdPerson.getAddress(), found.getAddress());
        assertEquals(createdPerson.getGender(), found.getGender());
    }

    @Test
    void findByIdWithInvalidOriginShouldFail() {
        PersonDTO createdPerson = given()
                .basePath("/api/persons/v1")
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .port(TestConfig.SERVER_PORT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mockPersonDTO())
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonDTO.class);

        String found = given()
                .basePath("/api/persons/v1/{id}")
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_INVALID)
                .port(TestConfig.SERVER_PORT)
                .pathParam("id", createdPerson.getId())
                .when()
                .get()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", found);
    }

    private PersonDTO mockPersonDTO() {
        var dto = new PersonDTO();

        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAddress("123 Main St");
        dto.setGender("Male");

        return dto;
    }
}