@ui @admin
Feature: Sales Management Functionality

  Scenario: Verify that admin can navigate to sell plant page
    Given the user navigates to the "sales" page via sidebar
    When the admin clicks on the "Sell Plant" button in the Sales page
    Then the URL should contain "/sales/new"
