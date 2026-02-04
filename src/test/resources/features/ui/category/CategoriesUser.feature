@ui @user
Feature: Category Management - Standard User

  @SeedCategorySortTest
  Scenario: Filter category by parent
    And the user is on the Categories page
    When the user selects the created parent category from dropdown
    And the user clicks the search button on category page
    Then filtered categories by the created parent category should be displayed

  Scenario: Add Category button visibility restriction
    Given the user is logged into the system
    And the user is on the Categories page
    Then the Add Category button should not be visible

  @SeedCategorySortTest
  Scenario: Edit and Delete action restriction
    Given the user is logged into the system
    And the user is on the Categories page
    Then Edit and Delete actions should be hidden or disabled

  Scenario: Unauthorized access to Add Category page
    Given the user is logged into the system
    When the user navigates to "/ui/categories/add"
    Then the user should be redirected to Access Denied page

  @SeedCategorySortTest
  Scenario: Search category with invalid name
    Given the user is logged into the system
    And the user is on the Categories page
    When the user enters an invalid category name in search field
    And the user clicks the search button on category page
    Then "No category found" message should be displayed

#//----prameshh

  @SeedCategorySortTest
  Scenario: View category list as user
    Given the user is logged into the system
    And the user is on the Categories page
    Then the Categories page should be displayed
    And the category list should be displayed in read-only mode

  @SeedCategorySearchTest
  Scenario: Search category as user
    Given the user is logged into the system
    And the user is on the Categories page
    When the user enters the created sub-category name in the search field
    And the user clicks the search button on category page
    Then the created sub-category should be displayed in the results

 @SeedCategorySortTest
  Scenario: Sort categories by ID
    Given the user is logged into the system
    And the user is on the Categories page
    When the admin clicks on the ID column header
    Then the sorting indicator should appear on the ID column
    And categories should be sorted by ID

 @SeedCategorySortTest
  Scenario: Sort categories by name
    Given the user is logged into the system
    And the user is on the Categories page
    When the admin clicks on the Name column header
    Then the sorting indicator should appear on the Name column
    And categories should be sorted alphabetically by name

 @SeedCategorySortTest
  Scenario: Sort categories by parent
    Given the user is logged into the system
    And the user is on the Categories page
    When the user clicks on the Parent column header
    Then the sorting indicator should appear on the Parent column
    And categories should be sorted based on parent category