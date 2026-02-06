@api @user @mvp
Feature: Dashboard API Validation for Authenticated User

  Background:
    Given user auth token is set

  @SeedDashboardTests
  Scenario: TC_DSH_USR_06 - Verify authenticated User can fetch Category Summary
    When the user sends a GET request to retrieve the Category Summary from "/categories/summary"
    Then the response status code should be 200
    And the response should contain "mainCategories" with value 1
    And the response should contain "subCategories" with value 2

  @SeedDashboardTests
  Scenario: TC_DSH_USR_07 - Verify authenticated User can fetch Plant Summary
    When the user sends a GET request to retrieve the Plant Summary from "/plants/summary"
    Then the response status code should be 200
    And the response should contain "totalPlants" with value 2
    And the response should contain "lowStockPlants" with value 1
