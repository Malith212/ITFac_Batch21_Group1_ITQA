package com.itqa.assignment.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiHelper {

    private static String jwtToken = null;

    /**
     * Authenticates with the API and retrieves a JWT token.
     * The token is cached for subsequent requests.
     */
    private static String getJwtToken() {
        if (jwtToken == null) {
            String baseUri = ConfigReader.getProperty("api.base.uri");
            String username = ConfigReader.getProperty("api.username");
            String password = ConfigReader.getProperty("api.password");

            Map<String, String> credentials = Map.of(
                    "username", username,
                    "password", password
            );

            Response response = RestAssured.given()
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .post("/auth/login");

            if (response.getStatusCode() != 200) {
                throw new RuntimeException("Authentication failed: " + response.getStatusLine());
            }

            // Try to extract token from different possible response formats
            jwtToken = response.jsonPath().getString("token");
            if (jwtToken == null) {
                jwtToken = response.jsonPath().getString("accessToken");
            }
            if (jwtToken == null) {
                jwtToken = response.jsonPath().getString("jwt");
            }
            if (jwtToken == null) {
                throw new RuntimeException("Could not extract JWT token from response: " + response.getBody().asString());
            }
        }
        return jwtToken;
    }

    /**
     * Clears the cached JWT token. Call this if you need to re-authenticate.
     */
    public static void clearToken() {
        jwtToken = null;
    }

    private static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                // JWT Bearer token authentication
                .header("Authorization", "Bearer " + getJwtToken())
                .contentType(ContentType.JSON)
                .log().ifValidationFails();
    }

    /**
     * Reusable POST method that returns the ID of the created resource
     */
    public static String postAndGetId(String endpoint, Object body) {
        Response response = getRequestSpec()
                .body(body)
                .post(endpoint);

        if (response.getStatusCode() != 201 && response.getStatusCode() != 200) {
            throw new RuntimeException("API Failure: " + response.getStatusLine());
        }

        return response.jsonPath().getString("id");
    }

    /**
     * Reusable POST method with query parameters that returns the ID of the created resource
     */
    public static String postWithParams(String endpoint, Map<String, Object> queryParams) {
        RequestSpecification spec = getRequestSpec();
        if (queryParams != null) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                spec.queryParam(entry.getKey(), entry.getValue());
            }
        }

        Response response = spec.post(endpoint);

        if (response.getStatusCode() != 200 && response.getStatusCode() != 201) {
            throw new RuntimeException("POST failed: " + response.getStatusLine());
        }

        return response.jsonPath().getString("id");
    }

    /**
     * Reusable DELETE method
     */
    public static void deleteResource(String endpoint, int id) {
        getRequestSpec()
                .delete(endpoint + "/" + id);
    }
}