@ui
Feature: Login Functionality

  Scenario: TC_DSH_ADM_01 - Verify that an Admin with valid credentials navigates to the Dashboard page after successful login
    Given the user is on the "Login" page
    When the user enters valid admin username
    And the user enters valid admin password
    And the user clicks the "Login" button
    Then the URL should contain "dashboard"

  Scenario: Verify that an Admin with invalid credentials sees an error message
    Given the user is on the "Login" page
    When the user enters invalid admin username
    And the user enters invalid admin password
    And the user clicks the "Login" button
    Then the user should see an error message "Invalid username or password."