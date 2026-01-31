@ui
Feature: Login Functionality

  Scenario: Successful Login as Admin
    Given the user is on the login page
    When the user enters valid admin credentials
    Then the user should be redirected to the Dashboard

  Scenario: Failed Login with Invalid Credentials
    Given the user is on the login page
    When the user enters "admin_user" and "WRONG_PASSWORD"
    Then the user should see an error message "Invalid username or password."

  Scenario: Successful Login as Standard User
    Given the user is on the login page
    When the user enters "testuser" and "test123"
    Then the user should be redirected to the Dashboard