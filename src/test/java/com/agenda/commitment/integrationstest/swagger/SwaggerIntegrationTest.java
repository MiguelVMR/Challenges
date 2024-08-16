package com.agenda.commitment.integrationstest.swagger;

import static io.restassured.RestAssured.given;

import com.agenda.commitment.configuration.AbstractIntegrationTest;
import com.agenda.commitment.configuration.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The Class SwaggerIntegrationTest
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void testShouldDisplaySwaggerUiPage() {
        var content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfig.SERVER_PORT)
                .when().get()
                .then().statusCode(200)
                .extract().body().asString();

        assertTrue(content.contains("<title>Swagger UI</title>"));
    }
}
