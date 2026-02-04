@ui
Feature: Category Management - Admin

  Background:
    Given the admin is logged into the system

  Scenario: TC_CAT_ADM_01 - View category list
    And the admin is on the Categories page
    Then the categories page should be displayed
    And the Categories header should be visible
    And search sub category input field should be visible
    And category dropdown should be visible
    And search and reset buttons should be visible
    And add category button should be visible
    And category table should display ID, Name, Parent, Actions columns

  @SeedCategorySortTest
  Scenario: TC_CAT_ADM_02 - Sort categories by ID
    And the admin is on the Categories page
    When the admin clicks on the ID column header
    Then the sorting indicator should appear on the ID column
    And categories should be sorted by ID

  @SeedCategorySortTest
  Scenario: TC_CAT_ADM_03 - Sort categories by Name
    And the admin is on the Categories page
    When the admin clicks on the Name column header
    Then the sorting indicator should appear on the Name column
    And categories should be sorted alphabetically by name

  Scenario: TC_CAT_ADM_04 - Search category by valid name
    And the admin is on the Categories page
    When the admin enters a valid category name in search field
    And the admin clicks the Search button
    Then matching category records should be displayed

  Scenario: TC_CAT_ADM_05 - Delete Category
    And the admin is on the Categories page
    When the admin searches for a category record
    And the admin clicks the delete button
    Then the category should be deleted successfully
    And success message should be displayed

#  //----pramesh

  Scenario: TC_CAT_ADM_11 - Open Add Category page
    And the admin is on the Categories page
    When the admin clicks the Add Category button
    Then the Add Category page should be displayed

  Scenario: TC_CAT_ADM_12 - Add main category
    And the admin is on the Categories page
    When the admin clicks the Add Category button
    And the admin enters a valid category name
    And the admin leaves the parent category empty
    And the admin clicks the Save button on category page
    Then the category should be created successfully
    And the new category should appear in the category list
    When the admin deletes the created category from UI
    Then the created category should be removed from the list


  Scenario: TC_CAT_ADM_13 - Add sub-category
    # First create a main category
    And the admin is on the Categories page
    When the admin clicks the Add Category button
    And the admin enters a valid main category name
    And the admin leaves the parent category empty
    And the admin clicks the Save button on category page
    Then the category should be created successfully
    And the main category should appear in the category list
    # Now create a sub-category under the main category
    When the admin clicks the Add Category button
    And the admin enters a valid sub-category name
    And the admin selects the created main category as parent
    And the admin clicks the Save button on category page
    Then the sub-category should be created successfully
    And the sub-category should appear in the category list with correct parent
    # Cleanup - delete sub-category first, then main category
    When the admin deletes the created sub-category from UI
    Then the sub-category should be removed from the list
    When the admin deletes the created main category from UI
    Then the main category should be removed from the list

  Scenario: TC_CAT_ADM_14 - Category name required validation
    And the admin is on the Categories page
    When the admin clicks the Add Category button
    And the admin clicks the Save button on category page
    Then an error message "Category name is required" should be displayed
    And an error message "Category name must be between 3 and 10 characters" should be displayed


  Scenario: TC_CAT_ADM_15 - Cancel add category
    And the admin is on the Categories page
    When the admin clicks the Add Category button
    And the admin clicks the Cancel button
    Then the admin should be redirected to the Categories page