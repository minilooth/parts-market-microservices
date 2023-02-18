package by.minilooth.vehicleservice.integration;

import by.minilooth.vehicleservice.beans.ErrorResponse;
import by.minilooth.vehicleservice.common.enums.EngineTypeStatus;
import by.minilooth.vehicleservice.dtos.EngineTypeDto;
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

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EngineTypeApiIntegrationTests {

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
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnEngineTypesList() throws JsonProcessingException, JSONException {
        String expectedJson =
                "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"updatedAt\": null,\n" +
                "    \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "    \"name\": \"Petrol\",\n" +
                "    \"status\": \"ACTIVE\"\n" +
                "  }\n" +
                "]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<EngineTypeDto>> response = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<EngineTypeDto> data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnEngineTypeById() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"updatedAt\": null,\n" +
                "  \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<EngineTypeDto> response = restTemplate.exchange(
                (createURLWithPort() + "/1"), HttpMethod.GET, entity, EngineTypeDto.class);

        EngineTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getId());
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNull(data.getUpdatedAt());
        Assertions.assertEquals(data.getName(), "Petrol");
        Assertions.assertEquals(data.getStatus(), EngineTypeStatus.ACTIVE);

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "ALTER TABLE engine_type ALTER COLUMN id RESTART WITH 1"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateEngineType() throws Exception {
        String requestJson =
                "{\n" +
                "  \"name\": \"Petrol\"\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<EngineTypeDto> response = restTemplate.exchange(createURLWithPort(),
                HttpMethod.POST, entity, EngineTypeDto.class);

        EngineTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Petrol");
        Assertions.assertEquals(data.getStatus(), EngineTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateEngineType() throws JsonProcessingException, JSONException {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Diesel\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Diesel\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<EngineTypeDto> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, EngineTypeDto.class);

        EngineTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Diesel");
        Assertions.assertEquals(data.getStatus(), EngineTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotUpdateEngineType_notFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateEngineType_impossibleAction() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to update removed engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRemoveEngineType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<EngineTypeDto> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, EngineTypeDto.class);

        EngineTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Petrol");
        Assertions.assertEquals(data.getStatus(), EngineTypeStatus.REMOVED);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotRemoveEngineType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteEngineType() throws JsonProcessingException, JSONException {
        String firstExpectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";
        String secondExpectedJson = "[]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<EngineTypeDto> firstResponse = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, EngineTypeDto.class);

        EngineTypeDto firstData = firstResponse.getBody();

        Assertions.assertEquals(firstResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(firstData);
        Assertions.assertEquals(firstData.getName(), "Petrol");
        Assertions.assertEquals(firstData.getStatus(), EngineTypeStatus.REMOVED);
        Assertions.assertEquals(firstData.getId(), 1L);
        Assertions.assertNotNull(firstData.getCreatedAt());
        Assertions.assertNull(firstData.getUpdatedAt());

        JSONAssert.assertEquals(firstExpectedJson, objectMapper.writeValueAsString(firstData), false);

        ResponseEntity<List<EngineTypeDto>> secondResponse = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<EngineTypeDto> secondData = secondResponse.getBody();

        Assertions.assertEquals(secondResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(secondData);
        Assertions.assertTrue(secondData.isEmpty());

        JSONAssert.assertEquals(secondExpectedJson, objectMapper.writeValueAsString(secondData), true);
    }

    @Test
    public void shouldNotDeleteEngineType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeleteEngineType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Deleting engine type allowed only in REMOVED status");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'INACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldActivateEngineType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<EngineTypeDto> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, EngineTypeDto.class);

        EngineTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Petrol");
        Assertions.assertEquals(data.getStatus(), EngineTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotActivateEngineType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotActivateEngineType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to activate removed engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeactivateEngineType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Petrol\",\n" +
                "  \"status\": \"INACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<EngineTypeDto> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, EngineTypeDto.class);

        EngineTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Petrol");
        Assertions.assertEquals(data.getStatus(), EngineTypeStatus.INACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotDeactivateEngineType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find engine type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO engine_type(id, name, status, created_at) " +
                    "VALUES(1, 'Petrol', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM engine_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeactivateEngineType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to deactivate removed engine type with id 1");
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/v1/engine-type";
    }

}
