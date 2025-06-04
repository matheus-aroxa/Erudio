package com.miromorii.cursoerudio.integrationtests.controllers.withxml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.miromorii.cursoerudio.config.TestConfig;
import com.miromorii.cursoerudio.data.dto.v1.PersonDTO;
import com.miromorii.cursoerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXmlTest extends AbstractIntegrationTest {

    private static ObjectMapper mapper;
    private static RequestSpecification specification;
    private static PersonDTO person;
    private static XmlMapper xmlMapper;


    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = mockPersonDTO();
        xmlMapper = new XmlMapper();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .setBasePath("/api/persons/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setBody(xmlMapper.writeValueAsString(person))
                .setContentType(MediaType.APPLICATION_XML_VALUE)
                .build();

        //when
        String content = given(specification)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().asString();

        PersonDTO dto = mapper.readValue(content, PersonDTO.class);
        person = dto;
        //then
        assertTrue(person.getId() > 0);
        assertEquals("Pedro", person.getFirstName());
        assertEquals("Henrique", person.getLastName());
        assertEquals("RJ", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person.isEnabled());
    }

    @Test
    @Order(2)
    void findById() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .setBasePath("/api/persons/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setContentType(MediaType.APPLICATION_XML_VALUE)
                .addPathParam("id", person.getId())
                .build();
        //when
        String found = given(specification)
                .get("{id}")
                .then()
                .extract()
                .asString();

        var foundDTO = mapper.readValue(found, PersonDTO.class);
        person = foundDTO;

        //then
        assertTrue(person.getId() > 0);
        assertEquals("Pedro", person.getFirstName());
        assertEquals("Henrique", person.getLastName());
        assertEquals("RJ", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person.isEnabled());
    }

    @Test
    @Order(3)
    void update() throws JsonProcessingException {
        //given
        person.setFirstName("Joao");
        specification = new RequestSpecBuilder()
                .setBasePath("/api/persons/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setContentType(MediaType.APPLICATION_XML_VALUE)
                .setBody(xmlMapper.writeValueAsString(person))
                .build();

        //when
        String result = given(specification)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .asString();

        PersonDTO updatedDTO = mapper.readValue(result, PersonDTO.class);
        person = updatedDTO;

        //then
        assertTrue(person.getId() > 0);
        assertEquals("Joao", person.getFirstName());
        assertEquals("Henrique", person.getLastName());
        assertEquals("RJ", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person.isEnabled());
    }

    @Test
    @Order(4)
    void disable() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .setBasePath("/api/persons/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setContentType(MediaType.APPLICATION_XML_VALUE)
                .addPathParam("id", person.getId())
                .build();

        //when
        String result = given(specification)
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .extract().asString();

        PersonDTO personDTO = mapper.readValue(result, PersonDTO.class);
        person = personDTO;

        //then
        assertFalse(person.isEnabled());
    }

    @Test
    @Order(5)
    void findAll() throws JsonProcessingException {
        //given
        specification = new RequestSpecBuilder()
                .setBasePath("/api/persons/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setContentType(MediaType.APPLICATION_XML_VALUE)
                .build();
        //when
        String result = given()
                .basePath("/api/persons/v1")
                .port(TestConfig.SERVER_PORT)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().asString();

        List<PersonDTO> people = mapper.readValue(
                result,
                mapper.getTypeFactory().constructCollectionType(List.class, PersonDTO.class)
        );
        assertNotNull(people);
        assertFalse(people.isEmpty());
    }


    @Test
    @Order(6)
    void delete() {
        //given
        specification = new RequestSpecBuilder()
                .setBasePath("/api/persons/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .setContentType(MediaType.APPLICATION_XML_VALUE)
                .addPathParam("id", person.getId())
                .build();

        //when
        String result = given(specification)
                .when()
                .delete("{id}")
                .then()
                .statusCode(204)
                .extract().asString();

        String found = given()
                .basePath("/api/persons/v1/{id}")
                .pathParam("id", 1)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_X)
                .port(TestConfig.SERVER_PORT)
                .get()
                .then().extract().asString();


        //then
        assertTrue(found.contains("not found"));
    }

    private static PersonDTO mockPersonDTO() {
        PersonDTO dto = new PersonDTO();

        dto.setFirstName("Pedro");
        dto.setLastName("Henrique");
        dto.setAddress("RJ");
        dto.setGender("Male");
        dto.setEnabled(true);

        return dto;
    }
}