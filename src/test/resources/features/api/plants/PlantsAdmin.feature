@api @admin @mvp
Feature: Plants API Validation (Admin)

  @SeedPlantSearchTest @CleanUpPlants
  Scenario: TC_PLT_ADM_06 - Verify Admin can create a new plant under a specific sub-category
    Given admin auth token is set
    And a valid sub-category exists for plant creation
    When the admin sends a POST request to "/plants/category/{categoryId}" with plant details:
      | name     | API_TestPlant123 |
      | price    | 150          |
      | quantity | 25           |
    Then the response status code should be 201
    And the response body should contain a valid "id"
    And the response body should contain "name" with value "API_TestPlant123"

  @SeedPlantSearchTest
  Scenario: TC_PLT_ADM_07 - Verify Admin can update price and quantity of an existing plant
    Given admin auth token is set
    And a plant exists for update
    When the admin sends a PUT request to "/plants/{plantId}" with updated details:
      | name     | UpdatedPlant |
      | price    | 60           |
      | quantity | 90           |
    Then the response status code should be 200
    And the response body should contain "price" with value "60.0"
    And the response body should contain "quantity" with value "90"

  @SeedPlantSearchTest
  Scenario: TC_PLT_ADM_08 - Verify Admin can delete an existing plant by ID
    Given admin auth token is set
    And a plant exists for deletion
    When the admin sends a DELETE request to "/plants/{plantId}"
    Then the response status code should be 204

  @SeedPlantSearchTest
  Scenario: TC_PLT_ADM_09 - Verify retrieving a specific plant's details using its ID
    Given admin auth token is set
    And a plant exists for retrieval
    When the admin sends a GET request to "/plants/{plantId}"
    Then the response status code should be 200
    And the response body should contain a valid "id"
    And the response body should contain the created plant name

  @SeedPlantSearchTest
  Scenario: TC_PLT_ADM_10 - Verify validation fails if price is 0 or negative
    Given admin auth token is set
    And a valid sub-category exists for plant creation
    When the admin sends a POST request to "/plants/category/{categoryId}" with plant details:
      | name     | Rose |
      | price    | -50  |
      | quantity | 5    |
    Then the response status code should be 400
    And the response body should contain validation error for price

  @SeedDashboardTests
  Scenario: TC_PLT_ADM_13 - Verify duplicate plant names in the same category are not allowed
    Given admin auth token is set
    And valid sub-category id exists
    And a plant named "LowFern" exists in that sub-category
    When the admin sends a POST request to "/plants/category/{categoryId}" with plant details:
     | name     | LowFern     |
     | price    | 100         |
     | quantity | 10          |
    Then the response status code should be 400
    And the response should contain an error message "Plant 'LowFern' already exists in this category"

  @SeedDashboardTests
  Scenario: TC_PLT_ADM_14 - Verify validation fails if name, character length less than specified minLength 3
    Given admin auth token is set
    And valid sub-category id exists
    When the admin sends a POST request to "/plants/category/{categoryId}" with plant details:
     | name     | AB          |
     | price    | 100         |
     | quantity | 10          |
    Then the response status code should be 400
    And the response body should contain validation error for name length

  @SeedDashboardTests
  Scenario: TC_PLT_ADM_15 - Verify 404 response when attempting to update a non-existent plant ID
    Given admin auth token is set
    When the admin sends a PUT request to "/plants/9999" with updated details:
      | name     | NonExistentPlant |
      | price    | 100              |
      | quantity | 10               |
    Then the response status code should be 404
    And the response should contain an error message "Plant not found"

  @SeedDashboardTests
  Scenario: TC_PLT_ADM_16 - Verify validation fails when a Admin user deletes a plant that does not exist
    Given admin auth token is set
    When the admin sends a DELETE request to "/plants/9999"
    Then the response status code should be 404
    And the response should contain an error message "Plant not found"

  @SeedDashboardTests
  Scenario: TC_PLT_ADM_17 - Verify validation fails when an Amin user creates a plant with a non-existent Category ID
    Given admin auth token is set
    When the admin sends a POST request to "/plants/category/9999" with plant details:
      | name     | InvalidCategoryPlant |
      | price    | 100                  |
      | quantity | 10                   |
    Then the response status code should be 404
    And the response should contain an error message "Category not found"
