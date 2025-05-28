package com.miromorii.cursoerudio.integrationtests.testcontainers.swagger;

import com.miromorii.cursoerudio.config.TestConfig;
import com.miromorii.cursoerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTests extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerPage() {
        String content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfig.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().body().asString();

        assertTrue(content.contains("Swagger UI"));
    }

}