package com.itqa.assignment.context;

import io.restassured.response.Response;

/**
 * Global context holder for test scenarios.
 * Provides shared access to API responses and other test data across step definitions.
 */
public class ScenarioContext {
    private static final ThreadLocal<Response> apiResponse = new ThreadLocal<>();

    /**
     * Sets the API response for the current thread/scenario
     */
    public static void setApiResponse(Response response) {
        apiResponse.set(response);
    }

    /**
     * Retrieves the API response for the current thread/scenario
     */
    public static Response getApiResponse() {
        Response response = apiResponse.get();
        if (response == null) {
            throw new IllegalStateException("API response is null. Ensure that a request has been made.");
        }
        return response;
    }

    /**
     * Clears the API response for the current thread/scenario
     */
    private static void clearApiResponse() {
        apiResponse.remove();
    }

    /**
     * Clears all context data (call this in teardown)
     */
    public static void clear() {
        clearApiResponse();
    }
}
