@ui @user
Feature: Plant Management User Functionality

  @TC_PLT_USR_01
  Scenario: Verify plant list visibility for user in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And the plant list should be displayed
    And the plant list should have pagination

  @TC_PLT_USR_03
  Scenario: Verify access control for Add plant in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And the page header area should be visible
    And the "Add Plant" button should not be visible

  @TC_PLT_USR_04
  Scenario: Verify edit and delete access restriction in plants page
    Given the user navigates to the "plants" page via sidebar
    Then the user should be on the "plants" page
    And at least one plant row should be visible
    And the Actions column should be visible
    And the Edit and Delete actions should not be visible

