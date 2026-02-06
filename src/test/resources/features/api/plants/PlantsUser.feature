@api @user
Feature: Plants API Validation for Authenticated User

  Background:
    Given user auth token is set

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_06 - Verify User cannot create a new plant
    And a valid sub-category exists for plant creation
    When the user sends a POST request to "/plants/category/{categoryId}" with plant details:
      | name     | UserPlant |
      | price    | 100       |
      | quantity | 10        |
    Then the response status code should be 403
    And the response body should contain error message for insufficient permissions

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_07 - Verify User cannot delete a plant
    And a plant exists for user deletion attempt
    When the user sends a DELETE request to "/plants/{plantId}"
    Then the response status code should be 403
    And the response body should contain error message for insufficient permissions

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_08 - Verify User cannot update a plant
    And a plant exists for user update attempt
    When the user sends a PUT request to "/plants/{plantId}" with updated details:
      | name     | UpdatedByUser |
      | price    | 200           |
      | quantity | 50            |
    Then the response status code should be 403
    And the response body should contain error message for insufficient permissions

  @SeedPlantListTest
  Scenario: TC_PLT_USR_09 - Verify User can successfully view the plant list
    When the user sends a GET request to "/plants"
    Then the response status code should be 200
    And the response body should contain a list of plants

  @SeedPlantListTest
  Scenario: TC_PLT_USR_10 - Verify searching plants with pagination parameters
    When the user sends a GET request to "/plants/paged?page=0&size=5"
    Then the response status code should be 200
    And the response body should contain content array with max 5 items

  @SeedDashboardTests
  Scenario: TC_PLT_USR_11 - Verify 404 response when requesting a non-existent plant ID
    When the user sends a GET request to retrieve plant with id 9999 from "/plants/9999"
    Then the response status code should be 404
    And the response should contain an error message "Plant not found: 9999"

  @SeedDashboardTests
  Scenario: TC_PLT_USR_12 - Verify User can retrieve all plants associated with a specific sub-category
    And valid sub-category id exists
    When the user sends a GET request to retrieve plants by sub-category id from "/plants/category/{subCategoryId}"
    Then the response status code should be 200
    And the response should contain a list of plants associated with the provided sub-category id

  @SeedDashboardTests
  Scenario: TC_PLT_USR_13 - Verify User can get plants using plant name
    And valid plant name exists
    When the user sends a GET request to retrieve plants by name from "/plants/paged?page=0&size=5&name={plantName}"
    Then the response status code should be 200
    And the response should contain a list with 1 plant object with name "LowFern"


