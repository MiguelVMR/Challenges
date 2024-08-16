package com.agenda.commitment.integrationstest.boundary;

import com.agenda.commitment.configuration.AbstractIntegrationTest;
import com.agenda.commitment.configuration.TestConfig;
import com.agenda.commitment.entities.Commitment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The Class CommitmentControllerTestIntegration
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommitmentControllerTestIntegration extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Commitment  commitment;

    @BeforeAll
    public static void setUp() {

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        mapper.registerModule(javaTimeModule);

        specification = new RequestSpecBuilder()
                .setBasePath("/v1/commitment")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .build();

        commitment = new Commitment();
        commitment.setDescription("EXP mentoring online class");
        commitment.setName("EXP");
        commitment.setDate(LocalDate.now());
        commitment.setHour("20:00");
    }

    @Test
    @Order(1)
    void integrationTestGivenCommitment_when_CreateOneCommitment_ShouldReturnOneCommitment() throws JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(commitment)
                .when().post()
                .then().statusCode(200)
                .extract().body().asString();

        Commitment createdCommitment = mapper.readValue(content, Commitment.class);

        commitment = createdCommitment;

        assertNotNull(createdCommitment.getName());
        assertNotNull(createdCommitment.getHour());
        assertNotNull(createdCommitment.getDescription());
        assertNotNull(createdCommitment.getDate());

    }

}
