@ui @user
Feature: Plant Management User Functionality

  @SeedPlantListTest
  Scenario: TC_PLT_USR_01 - Verify plant list visibility for user in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And the plant list should be displayed
    And the plant list should have pagination

  @SeedPlantSearchTest
  Scenario: TC_PLT_USR_02 - Verify search functionality for user in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    When the user enters the created plant name in the search field
    And the user clicks the Search button
    Then the search keyword should be entered
    And the page should refresh or grid should update
    And only plants matching the name should be displayed
    And the created plant should be displayed in the results

  Scenario: TC_PLT_USR_03 - Verify access control for Add plant in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And the page header area should be visible
    And the "Add Plant" button should not be visible

  Scenario: TC_PLT_USR_04 - Verify edit and delete access restriction in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And at least one plant row should be visible
    And the Actions column should be visible
    And the Edit and Delete actions should not be visible

  @SeedPlantSortTest
  Scenario: TC_PLT_USR_05 - Verify sorting by plants name in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And the Name column header should be clickable
    When the user clicks on the Name column header
    Then the sort indicator should appear or toggle
    And the plant list should be sorted alphabetically
