@ui
Feature: Sales Management Admin Functionality

  Background:
    Given the admin is logged into the system

  @SeedSalesAdminTests
  Scenario: TC_SLS_ADM_01 - Verify that admin can navigate to sell plant page
    Given the admin navigates to the "sales" page via sidebar
    When the admin clicks on the "Sell Plant" button in the Sales page
    Then the "Sell Plant" form should be display successfully

  @SeedSalesAdminTests
  Scenario: TC_SLS_ADM_02 - Verify plant dropdown loads only available plants with stock
    Given the admin navigates to the "sales" page via sidebar
    And the admin navigates to the Sell Plant page
    When the admin clicks on the Plant selection dropdown
    Then the plant dropdown should display only plants with stock greater than 0

  @SeedSalesAdminTests
  Scenario: TC_SLS_ADM_03 - Verify sell plant quantity validation for negative values
    Given the admin navigates to the "sales" page via sidebar
    And the admin navigates to the Sell Plant page
    When the admin enters quantity "-1"
    And the admin clicks on the "Sell" button
    Then the sell plant form submission should be blocked
    And the browser validation message should be displayed as "Quantity must be greater than 0"

  @SeedSalesAdminTests
  Scenario: TC_SLS_ADM_04 - Verify validation for empty plant selection
    Given the admin navigates to the "sales" page via sidebar
    And the admin navigates to the Sell Plant page
    When the admin enters quantity "5"
    And the admin clicks on the "Sell" button without selecting a plant
    Then the sell plant form submission should be blocked
    And the browser validation message should be displayed as "Plant is required"

  @SeedSalesAdminTests
  Scenario: TC_SLS_ADM_05 - Verify successfully plant sale
    Given the admin navigates to the "sales" page via sidebar
    And the admin navigates to the Sell Plant page
    When the admin selects a plant from the dropdown
    And the admin enters quantity between 0 and selected plant stock
    And the admin clicks on the "Sell" button
    Then the sell plant form should be submitted successfully
    And stock of the selected plant should be reduced accordingly
    And the user should be on the "sales" page