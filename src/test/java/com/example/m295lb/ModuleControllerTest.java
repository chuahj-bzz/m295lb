package com.example.m295lb;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleControllerTest {
    private static final String BASE_URL
            = "http://localhost:8080/artifacts_m295/resources/api";

    // positive get all modules
    @Test
    public void getAllModules_whenCorrectRequest_thenResponseCodeSuccess()
            throws ClientProtocolException, IOException {

        HttpUriRequest request = new HttpGet(BASE_URL + "/getModules");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode() );
    }
    // positive get by id
    @Test
    public void givenCorrectId_whenCorrectRequest_thenResponseCodeSuccess()
            throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(BASE_URL + "/2");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode() );
    }
    // negative get
    @Test
    public void givenWrongId_whenCorrectRequest_thenResponseCodeNotFound()
            throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(BASE_URL + "/50");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode() );
    }
    // positive create / put
    @Test
    public void creatingNewModule_whenPostRequest_thenResponseCodeCreated() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postModules");
        String invalidModuleJson = "{ \"name\": \"jia en\", \"start\": \"2025-05-30T10:00:00\", \"end\": \"2025-06-30T10:00:00\", \"cost\": 100.3, \"active\": true}";
        StringEntity entity = new StringEntity(invalidModuleJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
    }
    // negative create / put
    @Test
    public void createInvalidModule_whenPostRequest_thenResponseCodeBadRequest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postModules");
        String invalidModuleJson = "{ \"name\": \"jia en\", \"start\": \"2024-05-30T10:00:00\", \"end\": \"2024-06-30T10:00:00\", \"cost\": 100.3, \"active\": true}";
        StringEntity entity = new StringEntity(invalidModuleJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());
    }
    // positive update / put
    @Test
    public void givenValidModule_whenPutRequest_thenResponseCodeSuccess() throws IOException {
        // Update the module with ID 3
        HttpPut request = new HttpPut(BASE_URL + "/putModules/4");
        String updatedModuleJson = "{\"name\": \"newname2\", \"start\": \"2025-05-30T10:00:00\", \"end\": \"2025-06-30T10:00:00\", \"cost\": 500.90, \"active\":true}";
        StringEntity putEntity = new StringEntity(updatedModuleJson);
        request.setEntity(putEntity);
        request.setHeader("Content-type", "application/json");

        HttpResponse putResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, putResponse.getStatusLine().getStatusCode());
    }


    // negative update / put
    @Test
    public void ValidModule_whenPutRequest_thenResponseCodeSuccess() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postModules");
        String validModuleJson = "{ \"name\": \"Updated Module\", \"start\": \"2024-05-30\", \"end\": \"2024-06-30\", \"cost\": 500, \"active\": true }";
        StringEntity entity = new StringEntity(validModuleJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }



}
