@ui
Feature: Sales Management Functionality

  Scenario: Verify that admin can navigate to sell plant page
    Given the admin is logged into the system
    And the admin is on the Sales page
    When the admin clicks on the "Sell Plant" button in the Sales page
    Then the admin should be redirected to the page with url "/sales/new"