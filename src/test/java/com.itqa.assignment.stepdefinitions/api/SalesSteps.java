package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class SalesSteps {
    private int plantId;
    private int quantity;
    private int saleId;
    private String plantName;


    @Given("valid plantId and quantity are provided")
    public void valid_plant_id_and_quantity_are_provided() {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get("/plants");

        List<Map<String, Object>> plants = apiResponse.jsonPath().getList("");
        if (plants != null && !plants.isEmpty()) {
            Map<String, Object> firstPlant = plants.getFirst();
            Number idNum = (Number) firstPlant.get("id");
            Number qtyNum = (Number) firstPlant.get("quantity");
            this.plantId = idNum != null ? idNum.intValue() : 0;
            int availableQuantity = qtyNum != null ? qtyNum.intValue() : 0;
            this.quantity = Math.min(1, availableQuantity);
        }
    }

    @When("the admin sends a POST request to create a new plant sale at {string}")
    public void the_admin_sends_a_post_request_to_create_a_new_plant_sale_at(String endpoint) {
        // API endpoint format: /sales/plant/{plantId}?quantity={quantity}
        String fullEndpoint = endpoint + "/" + this.plantId + "?quantity=" + this.quantity;

        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .post(fullEndpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response body contains created Sales record with a valid saleId")
    public void the_response_body_contains_created_sales_record_with_a_valid_sale_id() {
        var response = ScenarioContext.getApiResponse();
        this.saleId = response.jsonPath().getObject("id", Integer.class);
        Integer responsePlantId = response.jsonPath().getObject("plant.id", Integer.class);
        Integer responseQuantity = response.jsonPath().getObject("quantity", Integer.class);
        Assert.assertEquals("Plant ID mismatch in response", this.plantId, responsePlantId.intValue());
        Assert.assertEquals("Quantity mismatch in response", this.quantity, responseQuantity.intValue());
        Assert.assertTrue("Invalid saleId in response", saleId > 0);
    }

    // TC_SLS_ADM_07 - Delete Sale with valid ID
    @Given("a valid sale record exists in the database")
    public void a_valid_sale_record_exist_in_the_database() {
        // First, get sales records to find a valid sale ID
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get("sales/page?page=0&size=2000&sort=desc");

        List<Map<String, Object>> sales = apiResponse.jsonPath().getList("content");
        if (sales != null && !sales.isEmpty()) {
            Map<String, Object> firstSale = sales.getFirst();
            Number idNum = (Number) firstSale.get("id");
            this.saleId = idNum != null ? idNum.intValue() : 0;
        }
        Assert.assertTrue("No valid sale record found for deletion", this.saleId > 0);
    }

    @When("the admin sends a DELETE request to {string} with valid sale ID")
    public void the_admin_sends_a_delete_request_to_with_valid_sale_id(String endpoint) {
        String fullEndpoint = endpoint.replace("{id}", String.valueOf(this.saleId));

        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .delete(fullEndpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    // TC_SLS_ADM_08 - Get all sales with sorting by PlantName
    @Given("sales records exist in the database")
    public void sales_records_exist_in_the_database() {
        // Verify that sales records exist by fetching them
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get("sales/page?page=0&size=2000&sort=desc");

        List<Map<String, Object>> content = apiResponse.jsonPath().getList("content");
        if (content == null || content.isEmpty()) {
            // Create a sale record if none exist
            var plantsResponse = RestAssured.given()
                    .baseUri(ConfigReader.getProperty("api.base.uri"))
                    .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                    .contentType("application/json")
                    .when()
                    .get("/plants");

            List<Map<String, Object>> plants = plantsResponse.jsonPath().getList("");
            if (plants != null && !plants.isEmpty()) {
                Map<String, Object> firstPlant = plants.getFirst();
                Number idNum = (Number) firstPlant.get("id");
                int plantId = idNum != null ? idNum.intValue() : 0;

                RestAssured.given()
                        .baseUri(ConfigReader.getProperty("api.base.uri"))
                        .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                        .contentType("application/json")
                        .when()
                        .post("/sales/plant/" + plantId + "?quantity=1");
            }
        }
    }

    @When("the admin sends a GET request to {string}")
    public void the_admin_sends_a_get_request_to(String endpoint) {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get(endpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response contains a content array with max {int} items sorted by Plant Name")
    public void the_response_contains_a_content_array_with_max_items_sorted_by_plant_name(int maxSize) {
        var response = ScenarioContext.getApiResponse();
        List<Map<String, Object>> content = response.jsonPath().getList("content");

        Assert.assertNotNull("Content array is null", content);
        Assert.assertTrue("Content array exceeds max size", content.size() <= maxSize);

        // Verify sorting by plant name
        if (content.size() > 1) {
            for (int i = 0; i < content.size() - 1; i++) {
                Map<String, Object> plant1 = (Map<String, Object>) content.get(i).get("plant");
                Map<String, Object> plant2 = (Map<String, Object>) content.get(i + 1).get("plant");

                String plantName1 = (String) plant1.get("name");
                String plantName2 = (String) plant2.get("name");

                Assert.assertTrue("Plant names are not sorted correctly",
                        plantName1.compareToIgnoreCase(plantName2) <= 0);
            }
        }
    }

    // TC_SLS_ADM_09 - Large Quantity
    @Given("a valid plantId with limited stock is available")
    public void a_valid_plant_id_with_limited_stock_is_available() {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get("/plants");

        List<Map<String, Object>> plants = apiResponse.jsonPath().getList("");
        if (plants != null && !plants.isEmpty()) {
            Map<String, Object> firstPlant = plants.getFirst();
            Number idNum = (Number) firstPlant.get("id");
            this.plantId = idNum != null ? idNum.intValue() : 0;
            this.plantName = (String) firstPlant.get("name");
            this.quantity = (int) firstPlant.get("quantity");
        }
    }

    @When("the admin sends a POST request with large quantity exceeding stock to {string}")
    public void the_admin_sends_a_post_request_with_large_quantity_exceeding_stock_to(String endpoint) {
        int qty = 1000000; // Large quantity that exceeds stock
        String fullEndpoint = endpoint + "/" + this.plantId + "?quantity=" + qty;

        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .post(fullEndpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response contains error message about insufficient stock")
    public void the_response_contains_error_message_about_insufficient_stock() {
        var response = ScenarioContext.getApiResponse();
        String actualMessage = response.jsonPath().getString("message");

        String expectMessage = this.plantName + " has only " + this.quantity + " items available in stock";
        Assert.assertEquals("Insufficient stock error message mismatch", expectMessage, actualMessage);
    }

    // TC_SLS_ADM_10 - Create Sell plant with Invalid PlantId
    @Given("a non-existent plantId is provided")
    public void a_non_existent_plant_id_is_provided() {
        this.plantId = 999999; // Non-existent plant ID
        this.quantity = 1;
    }

    @When("the admin sends a POST request to create sale with invalid plantId at {string}")
    public void the_admin_sends_a_post_request_to_create_sale_with_invalid_plant_id_at(String endpoint) {
        String fullEndpoint = endpoint + "/" + this.plantId + "?quantity=" + this.quantity;

        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .post(fullEndpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response contains error message {string}")
    public void the_response_contains_error_message(String expectedMessage) {
        var response = ScenarioContext.getApiResponse();
        String errorMessage = response.getBody().asString();

        Assert.assertTrue("Expected error message not found in response",
                errorMessage.contains(expectedMessage));
    }
}