@ui @admin
Feature: Plant Management Admin Functionality

  Scenario: TC_PLT_ADM_01 - Verify that admin can navigate to plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    When the admin clicks on the "Add Plant" button in the Plant page
    Then the URL should contain "/plants/add"

  @SeedCategoryDropdownTest
  Scenario: TC_PLT_ADM_02 - Verify Category must be a sub-category in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    When the admin clicks on the "Add Plant" button in the Plant page
    Then the Category dropdown should be visible in Add Plant page
    And the Category dropdown should contain the created sub-category as selectable option
    And the Category dropdown should not contain the created main category as selectable option

  Scenario: TC_PLT_ADM_03 - Verify validation for empty plant name in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin leaves the Plant Name field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Plant name is required" should be displayed below the Plant Name field in red

  Scenario: TC_PLT_ADM_04 - Verify validation for empty price in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin leaves the Price field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Price is required" should be displayed below the Price field

  Scenario: TC_PLT_ADM_05 - Verify validation for quantity negative number in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin enters a negative number in the Quantity field
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Quantity cannot be negative" should be displayed below the Quantity field
