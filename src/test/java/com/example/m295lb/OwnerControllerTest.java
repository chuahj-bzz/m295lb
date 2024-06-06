package com.example.m295lb;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnerControllerTest {
    private static final String BASE_URL = "http://localhost:8080/artifacts_m295lb/resources/api";

    // positive get all owners
    @Test
    public void getAllOwners_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/owners");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // positive create / post
    @Test
    public void creatingNewOwner_whenPostRequest_thenResponseCodeCreated() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/owners");
        String newOwnerJson = "{ \"name\": \"John Doe\", \"address\": \"123 Main St\" }";
        StringEntity entity = new StringEntity(newOwnerJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
    }

    // negative get
    @Test
    public void givenWrongId_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/owners/50");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }



    // negative create / post
    @Test
    public void createInvalidOwner_whenPostRequest_thenResponseCodeBadRequest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/owners");
        String invalidOwnerJson = "{ \"name\": \"\", \"address\": \"123 Main St\" }";
        StringEntity entity = new StringEntity(invalidOwnerJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }
}