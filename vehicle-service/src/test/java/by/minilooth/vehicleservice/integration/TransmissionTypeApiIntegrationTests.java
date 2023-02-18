package by.minilooth.vehicleservice.integration;

import by.minilooth.vehicleservice.beans.ErrorResponse;
import by.minilooth.vehicleservice.common.enums.TransmissionTypeStatus;
import by.minilooth.vehicleservice.dtos.TransmissionTypeDto;
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
public class TransmissionTypeApiIntegrationTests {

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
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnTransmissionTypesList() throws JsonProcessingException, JSONException {
        String expectedJson =
                "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"updatedAt\": null,\n" +
                "    \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "    \"name\": \"Manual\",\n" +
                "    \"status\": \"ACTIVE\"\n" +
                "  }\n" +
                "]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<TransmissionTypeDto>> response = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<TransmissionTypeDto> data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnTransmissionTypeById() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"updatedAt\": null,\n" +
                "  \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "  \"name\": \"Manual\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<TransmissionTypeDto> response = restTemplate.exchange(
                (createURLWithPort() + "/1"), HttpMethod.GET, entity, TransmissionTypeDto.class);

        TransmissionTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getId());
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNull(data.getUpdatedAt());
        Assertions.assertEquals(data.getName(), "Manual");
        Assertions.assertEquals(data.getStatus(), TransmissionTypeStatus.ACTIVE);

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "ALTER TABLE transmission_type ALTER COLUMN id RESTART WITH 1"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM transmission_type WHERE id = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateTransmissionType() throws Exception {
        String requestJson =
                "{\n" +
                "  \"name\": \"Manual\"\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Manual\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<TransmissionTypeDto> response = restTemplate.exchange(createURLWithPort(),
                HttpMethod.POST, entity, TransmissionTypeDto.class);

        TransmissionTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Manual");
        Assertions.assertEquals(data.getStatus(), TransmissionTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateTransmissionType() throws JsonProcessingException, JSONException {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Automatic\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Automatic\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<TransmissionTypeDto> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, TransmissionTypeDto.class);

        TransmissionTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Automatic");
        Assertions.assertEquals(data.getStatus(), TransmissionTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotUpdateTransmissionType_notFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Automatic\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateTransmissionType_impossibleAction() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Automatic\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to update removed transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRemoveTransmissionType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Manual\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<TransmissionTypeDto> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, TransmissionTypeDto.class);

        TransmissionTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Manual");
        Assertions.assertEquals(data.getStatus(), TransmissionTypeStatus.REMOVED);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotRemoveTransmissionType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteTransmissionType() throws JsonProcessingException, JSONException {
        String firstExpectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Manual\",\n" +
                "  \"status\": \"REMOVED\"\n" +
                "}";
        String secondExpectedJson = "[]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<TransmissionTypeDto> firstResponse = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, TransmissionTypeDto.class);

        TransmissionTypeDto firstData = firstResponse.getBody();

        Assertions.assertEquals(firstResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(firstData);
        Assertions.assertEquals(firstData.getName(), "Manual");
        Assertions.assertEquals(firstData.getStatus(), TransmissionTypeStatus.REMOVED);
        Assertions.assertEquals(firstData.getId(), 1L);
        Assertions.assertNotNull(firstData.getCreatedAt());
        Assertions.assertNull(firstData.getUpdatedAt());

        JSONAssert.assertEquals(firstExpectedJson, objectMapper.writeValueAsString(firstData), false);

        ResponseEntity<List<TransmissionTypeDto>> secondResponse = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<TransmissionTypeDto> secondData = secondResponse.getBody();

        Assertions.assertEquals(secondResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(secondData);
        Assertions.assertTrue(secondData.isEmpty());

        JSONAssert.assertEquals(secondExpectedJson, objectMapper.writeValueAsString(secondData), true);
    }

    @Test
    public void shouldNotDeleteTransmissionType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeleteTransmissionType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Deleting transmission type allowed only in REMOVED status");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'INACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldActivateTransmissionType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Manual\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<TransmissionTypeDto> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, TransmissionTypeDto.class);

        TransmissionTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Manual");
        Assertions.assertEquals(data.getStatus(), TransmissionTypeStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotActivateTransmissionType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotActivateTransmissionType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to activate removed transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'ACTIVE', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeactivateTransmissionType() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Manual\",\n" +
                "  \"status\": \"INACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<TransmissionTypeDto> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, TransmissionTypeDto.class);

        TransmissionTypeDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "Manual");
        Assertions.assertEquals(data.getStatus(), TransmissionTypeStatus.INACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotDeactivateTransmissionType_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find transmission type with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO transmission_type(id, name, status, created_at) " +
                    "VALUES(1, 'Manual', 'REMOVED', '2000-01-01 00:00:00.000000')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM transmission_type WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeactivateTransmissionType_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to deactivate removed transmission type with id 1");
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/v1/transmission-type";
    }

}
