@api @user @mvp
Feature: Plants API Validation (User)

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_06 - Verify User cannot create a new plant
    Given user auth token is set
    And a valid sub-category exists for plant creation
    When the user sends a POST request to "/plants/category/{categoryId}" with plant details:
      | name     | UserPlant |
      | price    | 100       |
      | quantity | 10        |
    Then the response status code should be 403
    And the response body should contain error message for insufficient permissions

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_07 - Verify User cannot delete a plant
    Given user auth token is set
    And a plant exists for user deletion attempt
    When the user sends a DELETE request to "/plants/{plantId}"
    Then the response status code should be 403
    And the response body should contain error message for insufficient permissions

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_08 - Verify User cannot update a plant
    Given user auth token is set
    And a plant exists for user update attempt
    When the user sends a PUT request to "/plants/{plantId}" with updated details:
      | name     | UpdatedByUser |
      | price    | 200           |
      | quantity | 50            |
    Then the response status code should be 403
    And the response body should contain error message for insufficient permissions

  @SeedPlantListTest
  Scenario: TC_PLT_USR_09 - Verify User can successfully view the plant list
    Given user auth token is set
    When the user sends a GET request to "/plants"
    Then the response status code should be 200
    And the response body should contain a list of plants

  @SeedPlantListTest
  Scenario: TC_PLT_USR_10 - Verify searching plants with pagination parameters
    Given user auth token is set
    When the user sends a GET request to "/plants/paged?page=0&size=5"
    Then the response status code should be 200
    And the response body should contain content array with max 5 items

