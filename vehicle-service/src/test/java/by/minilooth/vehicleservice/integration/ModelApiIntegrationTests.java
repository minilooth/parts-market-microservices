package by.minilooth.vehicleservice.integration;

import by.minilooth.vehicleservice.beans.ErrorResponse;
import by.minilooth.vehicleservice.common.enums.ModelStatus;
import by.minilooth.vehicleservice.dtos.ModelDto;
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
public class ModelApiIntegrationTests {

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
            "INSERT INTO make(id, name, status, created_at) " +
                    "VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnModelsList() throws JsonProcessingException, JSONException {
        String expectedJson =
                "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"updatedAt\": null,\n" +
                "    \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "    \"name\": \"3 series\",\n" +
                "    \"status\": \"ACTIVE\",\n" +
                "    \"makeId\": 1\n" +
                "  }\n" +
                "]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<ModelDto>> response = restTemplate.exchange(
                (createURLWithPort() + "/all/1"), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<ModelDto> data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) " +
                    "VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnModelById() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"updatedAt\": null,\n" +
                "  \"createdAt\": \"2000-01-01T00:00:00\",\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ModelDto> response = restTemplate.exchange(
                (createURLWithPort() + "/1"), HttpMethod.GET, entity, ModelDto.class);

        ModelDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getId());
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNull(data.getUpdatedAt());
        Assertions.assertEquals(data.getName(), "3 series");
        Assertions.assertEquals(data.getStatus(), ModelStatus.ACTIVE);
        Assertions.assertEquals(data.getMakeId(), 1L);

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), true);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "ALTER TABLE model ALTER COLUMN id RESTART WITH 1"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateModel() throws Exception {
        String requestJson =
                "{\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"makeId\": 1\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ModelDto> response = restTemplate.exchange(createURLWithPort(),
                HttpMethod.POST, entity, ModelDto.class);

        ModelDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "3 series");
        Assertions.assertEquals(data.getStatus(), ModelStatus.ACTIVE);
        Assertions.assertEquals(data.getMakeId(), 1L);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotCreateModel_notFound() {
        String requestJson =
                "{\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(), HttpMethod.POST,
                entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find make with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO make(id, name, status, created_at) VALUES(2, 'Audi', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1",
            "DELETE FROM make WHERE id = 2"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateModel() throws JsonProcessingException, JSONException {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"X5\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"makeId\": 2\n" +
                "}";
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"X5\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"makeId\": 2\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ModelDto> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ModelDto.class);

        ModelDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "X5");
        Assertions.assertEquals(data.getStatus(), ModelStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getMakeId(), 2L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotUpdateModel_modelNotFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"X5\",\n" +
                "  \"status\": \"ACTIVE\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find model with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateModel_makeNotFound() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"X5\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"makeId\": 2\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find make with id 2");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'REMOVED', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotUpdateModel_impossibleAction() {
        String requestJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"X5\",\n" +
                "  \"status\": \"REMOVED\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.PATCH, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to update removed model with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRemoveModel() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"status\": \"REMOVED\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ModelDto> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
                HttpMethod.POST, entity, ModelDto.class);

        ModelDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "3 series");
        Assertions.assertEquals(data.getStatus(), ModelStatus.REMOVED);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getMakeId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotRemoveModel_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/remove/1"),
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
                    "VALUES(1, '3 series', 'REMOVED', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteModel() throws JsonProcessingException, JSONException {
        String firstExpectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"status\": \"REMOVED\",\n" +
                "  \"makeId\": 1\n" +
                "}";
        String secondExpectedJson = "[]";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ModelDto> firstResponse = restTemplate.exchange((createURLWithPort() + "/1"), HttpMethod.DELETE, entity, ModelDto.class);

        ModelDto firstData = firstResponse.getBody();

        Assertions.assertEquals(firstResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(firstData);
        Assertions.assertEquals(firstData.getName(), "3 series");
        Assertions.assertEquals(firstData.getStatus(), ModelStatus.REMOVED);
        Assertions.assertEquals(firstData.getId(), 1L);
        Assertions.assertNotNull(firstData.getCreatedAt());
        Assertions.assertNull(firstData.getUpdatedAt());

        JSONAssert.assertEquals(firstExpectedJson, objectMapper.writeValueAsString(firstData), false);

        ResponseEntity<List<ModelDto>> secondResponse = restTemplate.exchange(
                (createURLWithPort() + "/all/1"), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        List<ModelDto> secondData = secondResponse.getBody();

        Assertions.assertEquals(secondResponse.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(secondData);
        Assertions.assertTrue(secondData.isEmpty());

        JSONAssert.assertEquals(secondExpectedJson, objectMapper.writeValueAsString(secondData), true);
    }

    @Test
    public void shouldNotDeleteModel_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Unable to find model with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeleteModel_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/1"),
                HttpMethod.DELETE, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getMessage(), "Deleting model allowed only in REMOVED status");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'INACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldActivateModel() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ModelDto> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ModelDto.class);

        ModelDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "3 series");
        Assertions.assertEquals(data.getStatus(), ModelStatus.ACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getMakeId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotActivateModel_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
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
                    "VALUES(1, '3 series', 'REMOVED', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotActivateModel_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/activate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(data.getMessage(), "Unable to activate removed model with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'ACTIVE', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeactivateModel() throws JsonProcessingException, JSONException {
        String expectedJson =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"3 series\",\n" +
                "  \"status\": \"INACTIVE\",\n" +
                "  \"makeId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ModelDto> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ModelDto.class);

        ModelDto data = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getName(), "3 series");
        Assertions.assertEquals(data.getStatus(), ModelStatus.INACTIVE);
        Assertions.assertEquals(data.getId(), 1L);
        Assertions.assertEquals(data.getMakeId(), 1L);
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getUpdatedAt());

        JSONAssert.assertEquals(expectedJson, objectMapper.writeValueAsString(data), false);
    }

    @Test
    public void shouldNotDeactivateModel_notFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(data.getMessage(), "Unable to find model with id 1");
    }

    @Test
    @Sql(statements = {
            "INSERT INTO make(id, name, status, created_at) VALUES(1, 'BMW', 'ACTIVE', '2000-01-01 00:00:00.000000')",
            "INSERT INTO model(id, name, status, created_at, make_id) " +
                    "VALUES(1, '3 series', 'REMOVED', '2000-01-01 00:00:00.000000', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM model WHERE id = 1",
            "DELETE FROM make WHERE id = 1"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeactivateModel_impossibleAction() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange((createURLWithPort() + "/deactivate/1"),
                HttpMethod.POST, entity, ErrorResponse.class);

        ErrorResponse data = response.getBody();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(data.getMessage(), "Unable to deactivate removed model with id 1");
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/v1/model";
    }

}
