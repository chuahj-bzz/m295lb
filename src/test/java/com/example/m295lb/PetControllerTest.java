package com.example.m295lb;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetControllerTest {
    private static final String BASE_URL = "http://localhost:8080/artifacts_m295lb/resources/api";

    @Test
    public void creatingNewPet_whenPostRequest_thenResponseCodeCreated() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPet");
        String newPetJson = "{ \"alive\": true, \"birthday\": \"2020-01-17\", \"breed\": \"Dogo\", \"legs\": 4, \"name\": \"jia en\", \"weight\": 30.3}";
        StringEntity entity = new StringEntity(newPetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        // Print out the response body when the status code is 400
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response body: " + responseBody);
        }
        assertEquals(HttpStatus.SC_CREATED, statusCode, "Expected status code is 201 but got " + statusCode);
    }
    // positive create / post
    @Test
    public void creatingNewPet_whenPostRequest_thenResponseCodeUnauthorized() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPet");
        String newPetJson = "{ \"alive\": true, \"birthday\": \"2020-01-17\", \"breed\": \"Dogo\", \"legs\": 4, \"name\": \"jia en\", \"weight\": 30.3}";
        StringEntity entity = new StringEntity(newPetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        // Print out the response body when the status code is 400
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response body: " + responseBody);
        }
        assertEquals(HttpStatus.SC_UNAUTHORIZED, statusCode, "Expected status code is 201 but got " + statusCode);
    }
    // negative create / post
    @Test
    public void createInvalidPet_whenPostRequest_thenResponseCodeServerError() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPets");
        String invalidPetJson = "{ \"name\": \"\", \"type\": \"Dog\", \"ownerId\": 1 }";
        StringEntity entity = new StringEntity(invalidPetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());
    }
    // positive create in bulk
    @Test
    public void creatingNewPetsBulk_whenPostRequest_thenResponseCodeCreated() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPetsBulk");
        String newPetsJson = "[{ \"name\": \"New\", \"breed\": \"Bulldog\", \"weight\": 30.0, \"birthday\": \"2015-05-20\", \"alive\": true, \"legs\": 4 }, { \"name\": \"Another\", \"breed\": \"Labrador\", \"weight\": 35.0, \"birthday\": \"2016-06-21\", \"alive\": true, \"legs\": 4 }]";
        StringEntity entity = new StringEntity(newPetsJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        // Print out the response body when the status code is 400
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response body: " + responseBody);
        }
        assertEquals(HttpStatus.SC_CREATED, statusCode, "Expected status code is 201 but got " + statusCode);
    }
    // positive create in bulk
    @Test
    public void creatingNewPetsBulk_whenPostRequest_thenResponseCodeUnauthorized() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPetsBulk");
        String newPetsJson = "[{ \"name\": \"New\", \"breed\": \"Bulldog\", \"weight\": 30.0, \"birthday\": \"2015-05-20\", \"alive\": true, \"legs\": 4 }, { \"name\": \"Another\", \"breed\": \"Labrador\", \"weight\": 35.0, \"birthday\": \"2016-06-21\", \"alive\": true, \"legs\": 4 }]";
        StringEntity entity = new StringEntity(newPetsJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        // Print out the response body when the status code is 400
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response body: " + responseBody);
        }
        assertEquals(HttpStatus.SC_UNAUTHORIZED, statusCode, "Expected status code is 201 but got " + statusCode);
    }
    // negative create in bulk
    @Test
    public void creatingNewPetsBulk_whenPostRequest_thenResponseCodeBadRequest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/postPetsBulk");
        // Invalid JSON data (missing required fields or wrong data type)
        String newPetsJson = "[{ \"namee\": \"New\", \"weight\": 30.0, \"birthday\": \"2015-05-20\", \"alive\": true, \"legs\": 4 }, { \"name\": \"Another\", \"weight\": 35.0, \"birthday\": \"2016-06-21\", \"alive\": true, \"legs\": 4 }]";
        StringEntity entity = new StringEntity(newPetsJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);
        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        // Print out the response body when the status code is 400
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response body: " + responseBody);
        }
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode, "Expected status code is 400 but got " + statusCode);
    }

    // negative get all pets when pets exist - has to fail if there are data entry
    @Test
    public void getAllPets_whenNoPetsExist_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/getPets");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    // negative get pet by id
    @Test
    public void givenWrongId_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/100");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }
    // positive checks if pet exists
    @Test
    public void checkPetExist_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/exists/1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    // negative checks if pet exists
    @Test
    public void checkPetNotExist_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/exists/100");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }
    // positive filter get all pets that are alive - boolean
    @Test
    public void getPetsAlive_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/isAlive/true");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // positive get all pets
    @Test
    public void getAllPets_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/getPets");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    // negative filter get all pets that are alive - boolean
    @Test
    public void getPetsAliveNotExist_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/isAlive/false");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    // positive get pet by id
    @Test
    public void getPetById_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void getPetsNotAlive_whenNoPetsNotAlive_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/isAlive/false");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    // positive get pets by name
    @Test
    public void getPetsByName_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/name/Max");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    // negative get pets by name
    @Test
    public void getPetByNameNotExisting_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/name/dug");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    // admin updating pets
    @Test
    public void updatePet_whenAdminUser_thenResponseCodeSuccess() throws IOException {
        HttpPut request = new HttpPut(BASE_URL + "/putPets/1"); // Assuming pet with id 10 exists
        String updatePetJson = "{ \"alive\": true, \"birthday\": \"2020-01-17\", \"breed\": \"Dogo\", \"legs\": 4, \"name\": \"NichtMaxi\", \"weight\": 30.3}";
        StringEntity entity = new StringEntity(updatePetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Print out the response body when the status code is 400
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response body: " + responseBody);
        }

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    public void updatePetsById_whenCorrectRequest_thenResponseCodeUnauthorized() throws IOException {
        HttpPut request = new HttpPut(BASE_URL + "/putPets/1"); // Assuming pet with id 10 exists
        String updatePetJson = "{ \"name\": \"UpdatedName\", \"weight\": \"30.3\",\"birthday\": \"2015-05-20T10:00:00\",\"alive\": \"true\",\"legs\": \"4\" }";
        StringEntity entity = new StringEntity(updatePetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }
    // negative update pets by id
    @Test
    public void updatePetsNotExisting_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpPut request = new HttpPut(BASE_URL + "/putPets/100");
        String updatePetJson = "{ \"alive\": true, \"birthday\": \"2020-01-17\", \"breed\": \"Dogo\", \"legs\": 4, \"name\": \"testWorks\", \"weight\": 30.3}";
        StringEntity entity = new StringEntity(updatePetJson, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    // positive delete Pet by Id
    @Test
    public void deletePetByIExisting_whenCorrectRequest_thenResponseCodeSucess() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePet/3");

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    // positive delete Pet by Id
    @Test
    public void deletePetByIExisting_whenCorrectRequest_thenResponseCodeUnauthorized() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePet/3");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }

    // delete Pet by Id
    @Test
    public void deletePetByIdNotExisting_whenCorrectRequest_thenResponseCodeNotFound() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePet/100");

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    // Positive test case
    @Test
    public void deletePetsByDate_whenPetsExist_thenResponseCodeSuccess() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePetsByDate/2022-12-31");

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    // Negative test case
    @Test
    public void deletePetsByDate_whenPetsDoNotExist_thenResponseCodeNotFound() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePetsByDate/2000-12-31");

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }
    @Test
    public void deletePetsByDate_whenPetsDoNotExist_thenResponseCodeUnauthorized() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePetsByDate/2022-01-17");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }

    // delete all Pets
    @Test
    public void deleteallPets_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePets");

        // Add basic authentication header
        String auth = "Basic " + Base64.getEncoder().encodeToString(("admin:1234").getBytes());
        request.setHeader("Authorization", auth);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }
    // delete all Pets
    @Test
    public void deleteallPets_whenCorrectRequest_thenResponseCodeUnauthorized() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/deletePets");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }
}



