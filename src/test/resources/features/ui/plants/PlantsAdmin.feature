@ui @admin
Feature: Plant Management Admin Functionality

  @TC_PLT_ADM_01
  Scenario: Verify that admin can navigate to plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    When the admin clicks on the "Add Plant" button in the Plant page
    Then the URL should contain "/plants/add"

  @TC_PLT_ADM_02
  Scenario: Verify Category must be a sub-category in add plant page
    # Step 1: Create a main category
    Given the admin navigates to the "categories" page via sidebar
    Then the admin is on the "categories" page
    When the admin clicks the Add Category button
    And the admin enters category name "MainCat"
    And the admin leaves the parent category empty
    And the admin clicks the Save Category button
    Then the category "MainCat" should be visible in the categories list
    # Step 2: Create a sub-category under the main category
    When the admin clicks the Add Category button
    And the admin enters category name "SubCat"
    And the admin selects "MainCat" as parent category
    And the admin clicks the Save Category button
    Then the category "SubCat" should be visible in the categories list
    # Step 3: Navigate to Add Plant page and verify category dropdown
    When the user navigates to the "plants" page via sidebar
    And the admin clicks on the "Add Plant" button in the Plant page
    Then the Category dropdown should be visible in Add Plant page
    And the Category dropdown should contain "SubCat" as selectable option
    And the Category dropdown should not contain "MainCat" as selectable option
    # Step 4: Cleanup - Delete sub-category first, then main category
    When the user navigates to the "categories" page via sidebar
    And the admin deletes category "SubCat"
    And the admin deletes category "MainCat"

  @TC_PLT_ADM_03
  Scenario: Verify validation for empty plant name in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin leaves the Plant Name field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Plant name is required" should be displayed below the Plant Name field in red

  @TC_PLT_ADM_04
  Scenario: Verify validation for empty price in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin leaves the Price field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Price is required" should be displayed below the Price field

  @TC_PLT_ADM_05
  Scenario: Verify validation for quantity negative number in add plant page
    Given the admin navigates to the "plants" page via sidebar
    Then the admin is on the "plants" page
    And the admin clicks on the "Add Plant" button in the Plant page
    When the admin enters a negative number in the Quantity field
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Quantity cannot be negative" should be displayed below the Quantity field

  @mvp
  Scenario: TC_PLT_ADM_11 - Verify that the system prevents form submission and displays an error when Quantity field is left empty
    Given the admin is on the "Add Plants" page
    When the admin leaves the Quantity field empty
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Quantity is required" should be displayed below the Quantity field

  @mvp
  Scenario: TC_PLT_ADM_12 - Verify that the system prevents form submission and displays an error when Category is in default/empty state
    Given the admin is on the "Add Plants" page
    When the admin leaves the Category field in default or empty state
    And the admin clicks the Save button
    Then the form submission should be blocked
    And the error message "Category is required" should be displayed below the Category field

