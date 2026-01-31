@ui
Feature: Dashboard Functionality

  Scenario: Verify that admin can navigate to "Categories" page from dashboard
    Given the admin is logged into the system
    When the admin clicks on the "Categories" button
    Then the admin should be redirected to the page with url "/categories"