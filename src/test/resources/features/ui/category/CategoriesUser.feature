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
