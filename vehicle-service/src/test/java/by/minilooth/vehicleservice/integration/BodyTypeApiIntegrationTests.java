package by.minilooth.vehicleservice.integration;

import by.minilooth.vehicleservice.beans.ErrorResponse;
import by.minilooth.vehicleservice.common.enums.BodyTypeStatus;
import by.minilooth.vehicleservice.dtos.BodyTypeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BodyTypeApiIntegrationTests {

    @LocalServerPort private int port;
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private ObjectMapper objectMapper;

    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                "VALUES(1, 'Coupe', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnBodyTypesList() throws JsonProcessingException, JSONException {
        String expectedJson =
                "[\n" +
                "   {\n" +
                "      \"id\":1,\n" +
                "      \"updatedAt\":null,\n" +
                "      \"createdAt\":\"2000-01-01T00:00:00\",\n" +
                "      \"name\":\"Coupe\",\n" +
                "      \"status\":\"ACTIVE\"\n" +
                "   }\n" +
                "]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<BodyTypeDto>> response = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<BodyTypeDto> data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                "VALUES(1, 'Coupe', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnBodyTypeById() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "   \"id\":1,\n" +
                "   \"updatedAt\":null,\n" +
                "   \"createdAt\":\"2000-01-01T00:00:00\",\n" +
                "   \"name\":\"Coupe\",\n" +
                "   \"status\":\"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<BodyTypeDto> response = restTemplate.exchange(
                (createURLWithPort() + "/1"), HttpMethod.GET, entity, BodyTypeDto.class);

        BodyTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getCreatedAt(), LocalDateTime.parse("2000-01-01T00:00:00"));
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getName(), "Coupe");
        Assertions.assertEquals(data.getStatus(), BodyTypeStatus.ACTIVE);
        Assertions.assertNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "ALTER TABLE body_type ALTER COLUMN id RESTART WITH 1"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateBodyType() throws Exception {
        String requestJson =
                "{\n" +
                "   \"name\":\"Coupe\"\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Coupe\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<BodyTypeDto> response = restTemplate.exchange(createURLWithPort(),
                HttpMethod.POST, entity, BodyTypeDto.class);

        BodyTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Coupe");
        Assertions.assertEquals(data.getStatus(), BodyTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateBodyType() throws JsonProcessingException, JSONException {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Cabriolet\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Cabriolet\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<BodyTypeDto> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, BodyTypeDto.class);

        BodyTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Cabriolet");
        Assertions.assertEquals(data.getStatus(), BodyTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotUpdateBodyType_notFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Cabriolet\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateBodyType_impossibleAction() {
        String request =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Cabriolet\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to update removed body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRemoveBodyType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Coupe\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<BodyTypeDto> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, BodyTypeDto.class);

        BodyTypeDto body = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(body);
        Assertions.assertEquals(body.getName(), "Coupe");
        Assertions.assertEquals(body.getStatus(), BodyTypeStatus.REMOVED);
        Assertions.assertEquals(body.getId(), 1L);
        Assertions.assertNotNull(body.getCreatedAt());
        Assertions.assertNotNull(body.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(body), false);
    }

    @Test
    public void shouldNotRemoveBodyType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteBodyType() throws JsonProcessingException, JSONException {
        String firstExpectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Coupe\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";
        String secondExpectedJson = "[]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<BodyTypeDto> firstResponse = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, BodyTypeDto.class);

        BodyTypeDto firstData = firstResponse.getBody();

        Assertions.assertEquals(firstResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(firstData);
        Assertions.assertEquals(firstData.getName(), "Coupe");
        Assertions.assertEquals(firstData.getStatus(), BodyTypeStatus.REMOVED);
        Assertions.assertEquals(firstData.getId(), 1L);
        Assertions.assertNotNull(firstData.getCreatedAt());
        Assertions.assertNull(firstData.getUpdatedAt());

        JSONAssert.assertEquals(firstExpectedJson, objectMapper.writeValueAsString(firstData), false);

        ResponseEntity<List<BodyTypeDto>> secondResponse = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<BodyTypeDto> secondData = secondResponse.getBody();

        Assertions.assertEquals(secondResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(secondData);
        Assertions.assertEquals(0, secondData.size());

        JSONAssert.assertEquals(secondExpectedJson, objectMapper.writeValueAsString(secondData), true);
    }

    @Test
    public void shouldNotDeleteBodyType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeleteBodyType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Deleting body type allowed only in REMOVED status");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'INACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldActivateBodyType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Coupe\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<BodyTypeDto> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, BodyTypeDto.class);

        BodyTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Coupe");
        Assertions.assertEquals(data.getStatus(), BodyTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotActivateBodyType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotActivateBodyType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to activate removed body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeactivateBodyType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Coupe\",\n" +
                "  \"status\": \"INACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<BodyTypeDto> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, BodyTypeDto.class);

        BodyTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Coupe");
        Assertions.assertEquals(data.getStatus(), BodyTypeStatus.INACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotDeactivateBodyType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find body type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO body_type(id, name, status, created_at) " +
                    "VALUES(1, 'Coupe', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM body_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeactivateBodyType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to deactivate removed body type with id 1");
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/v1/body-type";
    }

}
