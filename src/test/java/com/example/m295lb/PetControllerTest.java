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

public class PetControllerTest {
    private static final String BASE_URL = "http://localhost:8080/artifacts_m295lb/resources/api";

    // positive get all pets
    @Test
    public void getAllPets_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/api/getPets");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // positive get pet by id
    @Test
    public void getPetById_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/api/1");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // positive checks if pet exists
    @Test
    public void checkPetExist_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/api/exists/1");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // positive filter get all pets that are alive - boolean
    @Test
    public void getPetsAlive_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/api/isAlive/true");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // positive get pets by name
    @Test
    public void getPetsByName_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/api/name/Fido");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // negative get
    @Test
    public void givenWrongId_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/50");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    // positive create / post
    @Test
    public void creatingNewPet_whenPostRequest_thenResponseCodeCreated() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPets");
        String newPetJson = "{ \"name\": \"Fido\", \"type\": \"Dog\", \"ownerId\": 1 }";
        StringEntity entity = new StringEntity(newPetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
    }

    // negative create / post
    @Test
    public void createInvalidPet_whenPostRequest_thenResponseCodeBadRequest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPets");
        String invalidPetJson = "{ \"name\": \"\", \"type\": \"Dog\", \"ownerId\": 1 }";
        StringEntity entity = new StringEntity(invalidPetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_BAD_REQUEST, httpResponse.getStatusLine().getStatusCode());
    }
}