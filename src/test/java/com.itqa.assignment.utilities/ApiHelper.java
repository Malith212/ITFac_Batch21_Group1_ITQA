package com.itqa.assignment.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class ApiHelper {

    public enum UserRole {
        ADMIN,
        USER
    }

    private static final Map<UserRole, String> tokenCache = new HashMap<>();

    /**
     * Authenticates with the API and retrieves a JWT token for the specified role.
     * Tokens are cached per role for subsequent requests.
     */
    public static String getJwtToken(UserRole role) {
        if (!tokenCache.containsKey(role)) {
            String baseUri = ConfigReader.getProperty("api.base.uri");
            String username;
            String password;

            if (role == UserRole.ADMIN) {
                username = ConfigReader.getProperty("api.admin.username");
                password = ConfigReader.getProperty("api.admin.password");
            } else {
                username = ConfigReader.getProperty("api.user.username");
                password = ConfigReader.getProperty("api.user.password");
            }

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
                throw new RuntimeException("Authentication failed for " + role + ": " + response.getStatusLine());
            }

            // Try to extract token from different possible response formats
            String token = response.jsonPath().getString("token");
            if (token == null) {
                token = response.jsonPath().getString("accessToken");
            }
            if (token == null) {
                token = response.jsonPath().getString("jwt");
            }
            if (token == null) {
                throw new RuntimeException("Could not extract JWT token from response: " + response.getBody().asString());
            }
            tokenCache.put(role, token);
        }
        return tokenCache.get(role);
    }

    /**
     * Clears all cached JWT tokens. Call this if you need to re-authenticate.
     */
    @SuppressWarnings("unused")
    public static void clearToken() {
        tokenCache.clear();
    }

    /**
     * Clears the cached JWT token for a specific role.
     */
    @SuppressWarnings("unused")
    public static void clearToken(UserRole role) {
        tokenCache.remove(role);
    }

    public static RequestSpecification getRequestSpec(UserRole role) {
        return RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                // JWT Bearer token authentication
                .header("Authorization", "Bearer " + getJwtToken(role))
                .contentType(ContentType.JSON)
                .log().ifValidationFails();
    }

    /**
     * Reusable POST method that returns the ID of the created resource (uses ADMIN role by default)
     */
    public static String postAndGetId(String endpoint, Object body) {
        return postAndGetId(endpoint, body, UserRole.ADMIN);
    }

    /**
     * Reusable POST method that returns the ID of the created resource for a specific role
     */
    public static String postAndGetId(String endpoint, Object body, UserRole role) {
        Response response = getRequestSpec(role)
                .body(body)
                .post(endpoint);

        if (response.getStatusCode() != 201 && response.getStatusCode() != 200) {
            throw new RuntimeException("API Failure: " + response.getStatusLine());
        }

        return response.jsonPath().getString("id");
    }

    /**
     * Reusable POST method with query parameters that returns the ID of the created resource (uses ADMIN role by default)
     */
    public static String postWithParams(String endpoint, Map<String, Object> queryParams) {
        return postWithParams(endpoint, queryParams, UserRole.ADMIN);
    }

    /**
     * Reusable POST method with query parameters that returns the ID of the created resource for a specific role
     */
    public static String postWithParams(String endpoint, Map<String, Object> queryParams, UserRole role) {
        RequestSpecification spec = getRequestSpec(role);
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
     * Reusable DELETE method (uses ADMIN role by default)
     */
    public static void deleteResource(String endpoint, int id) {
        deleteResource(endpoint, id, UserRole.ADMIN);
    }

    /**
     * Reusable DELETE method for a specific role
     */
    public static void deleteResource(String endpoint, int id, UserRole role) {
        getRequestSpec(role)
                .delete(endpoint + "/" + id);
    }
}