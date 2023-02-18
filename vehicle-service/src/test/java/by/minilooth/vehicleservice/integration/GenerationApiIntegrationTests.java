package by.minilooth.vehicleservice.integration;

import by.minilooth.vehicleservice.beans.ErrorResponse;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import by.minilooth.vehicleservice.dtos.GenerationDto;
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
public class GenerationApiIntegrationTests {

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
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnGenerationsList() throws JsonProcessingException, JSONException {
        String expectedJson =
                "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"updatedAt\": null,\n" +
                "    \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "    \"name\": \"E36\",\n" +
                "    \"status\": \"ACTIVE\",\n" +
                "    \"modelId\": 1,\n" +
                "    \"issuedFrom\": 1990,\n" +
                "    \"issuedTo\": 2000\n" +
                "  }\n" +
                "]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<GenerationDto>> response = restTemplate.exchange(
                (createURLWithPort() + "/all/1"), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<GenerationDto> data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnGenerationById() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"updatedAt\": null,\n" +
                "  \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "  \"name\": \"E36\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GenerationDto> response = restTemplate.exchange(
                (createURLWithPort() + "/1"), HttpMethod.GET, entity, GenerationDto.class);

        GenerationDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getId());
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNull(data.getUpdatedAt());
        Assertions.assertEquals(data.getName(), "E36");
        Assertions.assertEquals(data.getStatus(), GenerationStatus.ACTIVE);
        Assertions.assertEquals(data.getModelId(), 1L);
        Assertions.assertEquals(data.getIssuedFrom(), 1990);
        Assertions.assertEquals(data.getIssuedTo(), 2000);

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "ALTER TABLE generation ALTER COLUMN id RESTART WITH 1"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateGeneration() throws Exception {
        String requestJson =
                "{\n" +
                "  \"name\": \"E36\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"updatedAt\": null,\n" +
                "  \"name\": \"E36\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<GenerationDto> response = restTemplate.exchange(createURLWithPort(),
                HttpMethod.POST, entity, GenerationDto.class);

        GenerationDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getId());
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNull(data.getUpdatedAt());
        Assertions.assertEquals(data.getName(), "E36");
        Assertions.assertEquals(data.getStatus(), GenerationStatus.ACTIVE);
        Assertions.assertEquals(data.getModelId(), 1L);
        Assertions.assertEquals(data.getIssuedFrom(), 1990);
        Assertions.assertEquals(data.getIssuedTo(), 2000);

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    @Sql(statements = {
            "ALTER TABLE generation ALTER COLUMN id RESTART WITH 1"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotCreateGeneration_notFound() {
        String requestJson =
                "{\n" +
                "  \"name\": \"E36\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find model with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(2, '5 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM model WHERE id = 2",
            "DELETE FROM make WHERE id = 1",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateGeneration() throws JsonProcessingException, JSONException {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E34\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 2,\n" +
                "  \"issuedFrom\": 1988,\n" +
                "  \"issuedTo\": 1995\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E34\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 2,\n" +
                "  \"issuedFrom\": 1988,\n" +
                "  \"issuedTo\": 1995\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<GenerationDto> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, GenerationDto.class);

        GenerationDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());
        Assertions.assertEquals(data.getName(), "E34");
        Assertions.assertEquals(data.getStatus(), GenerationStatus.ACTIVE);
        Assertions.assertEquals(data.getModelId(), 2L);
        Assertions.assertEquals(data.getIssuedFrom(), 1988);
        Assertions.assertEquals(data.getIssuedTo(), 1995);

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void generationNotFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E34\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 2,\n" +
                "  \"issuedFrom\": 1988,\n" +
                "  \"issuedTo\": 1995\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateGeneration_modelNotFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E34\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 2,\n" +
                "  \"issuedFrom\": 1988,\n" +
                "  \"issuedTo\": 1995\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find model with id 2");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'REMOVED', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateGeneration_impossibleAction() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E46\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1998,\n" +
                "  \"issuedTo\": 2006\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to update removed generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRemoveGeneration() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E36\",\n" +
                "  \"status\": \"REMOVED\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GenerationDto> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, GenerationDto.class);

        GenerationDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "E36");
        Assertions.assertEquals(data.getStatus(), GenerationStatus.REMOVED);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getModelId(), 1L);
        Assertions.assertEquals(data.getIssuedFrom(), 1990);
        Assertions.assertEquals(data.getIssuedTo(), 2000);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotRemoveGeneration_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'REMOVED', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteGeneration() throws JsonProcessingException, JSONException {
        String firstExpectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E36\",\n" +
                "  \"status\": \"REMOVED\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";
        String secondExpectedJson = "[]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GenerationDto> firstResponse = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, GenerationDto.class);

        GenerationDto firstData = firstResponse.getBody();

        Assertions.assertEquals(firstResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(firstData);
        Assertions.assertEquals(firstData.getName(), "E36");
        Assertions.assertEquals(firstData.getStatus(), GenerationStatus.REMOVED);
        Assertions.assertEquals(firstData.getId(), 1L);
        Assertions.assertEquals(firstData.getModelId(), 1L);
        Assertions.assertEquals(firstData.getIssuedFrom(), 1990);
        Assertions.assertEquals(firstData.getIssuedTo(), 2000);
        Assertions.assertNotNull(firstData.getCreatedAt());
        Assertions.assertNull(firstData.getUpdatedAt());

        JSONAssert.assertEquals(firstExpectedJson, objectMapper.writeValueAsString(firstData), false);

        ResponseEntity<List<GenerationDto>> secondResponse = restTemplate.exchange(
                (createURLWithPort() + "/all/1"), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<GenerationDto> secondData = secondResponse.getBody();

        Assertions.assertEquals(secondResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(secondData);
        Assertions.assertTrue(secondData.isEmpty());

        JSONAssert.assertEquals(secondExpectedJson, objectMapper.writeValueAsString(secondData), true);
    }

    @Test
    public void shouldNotDeleteGeneration_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeleteGeneration_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(data.getMessage(), "Deleting generation allowed only in REMOVED status");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'INACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldActivateGeneration() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E36\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GenerationDto> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, GenerationDto.class);

        GenerationDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "E36");
        Assertions.assertEquals(data.getStatus(), GenerationStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getModelId(), 1L);
        Assertions.assertEquals(data.getIssuedFrom(), 1990);
        Assertions.assertEquals(data.getIssuedTo(), 2000);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotActivateGeneration_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'REMOVED', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotActivateGeneration_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to activate removed generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'ACTIVE', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeactivateGeneration() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"E36\",\n" +
                "  \"status\": \"INACTIVE\",\n" +
                "  \"modelId\": 1,\n" +
                "  \"issuedFrom\": 1990,\n" +
                "  \"issuedTo\": 2000\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GenerationDto> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, GenerationDto.class);

        GenerationDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "E36");
        Assertions.assertEquals(data.getStatus(), GenerationStatus.INACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getModelId(), 1L);
        Assertions.assertEquals(data.getIssuedFrom(), 1990);
        Assertions.assertEquals(data.getIssuedTo(), 2000);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotDeactivateGeneration_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find generation with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)",
            "INSERT INTO generation(id, name, status, created_at, model_id, issued_from, issued_to) " +
                    "VALUES(1, 'E36', 'REMOVED', '2000-01-01 00:00:00.000000', 1, 1990, 2000)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM generation WHERE id = 1",
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeactivateGeneration_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(data.getMessage(), "Unable to deactivate removed generation with id 1");
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/v1/generation";
    }

}
