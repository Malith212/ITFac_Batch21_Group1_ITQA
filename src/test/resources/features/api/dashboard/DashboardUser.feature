@api @user @mvp
Feature: Dashboard API Validation for Authenticated User

  @SeedDashboardTests
  Scenario: TC_DSH_USR_06 - Verify authenticated User can fetch Category Summary
    Given user auth token is set
    When the user sends a GET request to retrieve the Category Summary from "/categories/summary"
    Then the response status code should be 200
    And the response should contain "mainCategories" with value 1
    And the response should contain "subCategories" with value 2

  @SeedDashboardTests
  Scenario: TC_DSH_USR_07 - Verify authenticated User can fetch Plant Summary
    Given user auth token is set
    When the user sends a GET request to retrieve the Plant Summary from "/plants/summary"
    Then the response status code should be 200
    And the response should contain "totalPlants" with value 2
    And the response should contain "lowStockPlants" with value 1

  @SeedDashboardTests
  Scenario: TC_PLT_USR_11 - Verify 404 response when requesting a non-existent plant ID
    Given user auth token is set
    When the user sends a GET request to retrieve plant with id 9999 from "/plants/9999"
    Then the response status code should be 404
    And the response should contain an error message "Plant not found: 9999"

  @SeedDashboardTests
  Scenario: TC_PLT_USR_12 - Verify User can retrieve all plants associated with a specific sub-category
    Given user auth token is set
    And valid sub-category id is provided
    When the user sends a GET request to retrieve plants by sub-category id from "/plants/category"
    Then the response status code should be 200
    And the response should contain a list of plants associated with the provided sub-category id

  @SeedDashboardTests
  Scenario: TC_PLT_USR_13 - Verify User can get plants using plant name
    Given user auth token is set
    And valid plant name is provided
    When the user sends a GET request to retrieve plants by name from "/plants/paged"
    Then the response status code should be 200
    And the response should contain a list with 1 plant object with name "LowFern"