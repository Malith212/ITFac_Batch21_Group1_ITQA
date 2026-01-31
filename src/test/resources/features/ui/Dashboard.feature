@ui @admin
Feature: Dashboard Functionality

  Scenario: Verify that admin can navigate to "Categories" page from dashboard
    When the user navigates to the "categories" page via sidebar
    Then the user should be on the "categories" page
