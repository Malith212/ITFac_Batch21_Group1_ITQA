@ui @user
Feature: Category Management - Standard User

  Scenario: Filter category by parent
    And the user is on the Categories page
    When the user selects a parent category from dropdown
    And the user clicks the Search button
    Then filtered categories should be displayed

  Scenario: Add Category button visibility restriction
    Given the user is logged into the system
    And the user is on the Categories page
    Then the Add Category button should not be visible

  Scenario: Edit and Delete action restriction
    Given the user is logged into the system
    And the user is on the Categories page
    Then Edit and Delete actions should be hidden or disabled

  Scenario: Unauthorized access to Add Category page
    Given the user is logged into the system
    When the user navigates to "/ui/categories/add"
    Then the user should be redirected to Access Denied page

  Scenario: Search category with invalid name
    Given the user is logged into the system
    And the user is on the Categories page
    When the user enters an invalid category name in search field
    And the user clicks the Search button
    Then "No category found" message should be displayed

#//pramesh

  Scenario: View category list as user
    Given the user is logged into the system
    And the user is on the Categories page
    Then the Categories page should be displayed
    And the category list should be displayed in read-only mode

  Scenario: Search category as user
    Given the user is logged into the system
    And the user is on the Categories page
    When the user enters a category name in the search field
    And the user clicks the Search button
    Then matching category records be displayed

  Scenario: Sort categories by ID
    Given the user is logged into the system
    And the user is on the Categories page
    When the admin clicks on the ID column header
    Then the sorting indicator should appear on the ID column
    And categories should be sorted by ID

  Scenario: Sort categories by name
    Given the user is logged into the system
    And the user is on the Categories page
    When the admin clicks on the Name column header
    Then the sorting indicator should appear on the Name column
    And categories should be sorted alphabetically by name

  Scenario: Sort categories by parent
    Given the user is logged into the system
    And the user is on the Categories page
    When the user clicks on the Parent column header
    Then the sorting indicator should appear on the Parent column
    And categories should be sorted based on parent category
