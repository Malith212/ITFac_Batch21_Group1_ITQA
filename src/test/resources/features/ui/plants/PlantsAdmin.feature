@ui @admin
Feature: Plant Management Admin Functionality

  @TC_PLT_ADM_01
  Scenario: Verify that admin can navigate to plant page
    Given the user navigates to the "plants" page via sidebar
    When the admin clicks on the "Add Plant" button in the Plant page
    Then the URL should contain "/plants/add"

  @TC_PLT_ADM_03
  Scenario: Verify validation for empty plant name in add plant page
    Given the user navigates to the "plants" page via sidebar
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin leaves the Plant Name field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Plant name is required" should be displayed below the Plant Name field in red

  @TC_PLT_ADM_04
  Scenario: Verify validation for empty price in add plant page
    Given the user navigates to the "plants" page via sidebar
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin leaves the Price field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Price is required" should be displayed below the Price field

  @TC_PLT_ADM_05
  Scenario: Verify validation for quantity negative number in add plant page
    Given the user navigates to the "plants" page via sidebar
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin enters a negative number in the Quantity field
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Quantity cannot be negative" should be displayed below the Quantity field

