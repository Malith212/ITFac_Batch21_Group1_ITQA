@ui
Feature: Dashboard Functionality

  Background:
    Given the user is logged into the system

  Scenario: TC_DSH_USR_01 - Verify the "Manage Plants" button redirects to the Plants page
    Given the user is on the "Dashboard" page
    When the user clicks on the "Manage Plants" button in the Dashboard page
    Then the URL should contain "/plants"

  Scenario: TC_DSH_USR_02 - Verify the "View Sales" button redirects to the Sales page
    Given the user is on the "Dashboard" page
    When the user clicks on the "View Sales" button in the Dashboard page
    Then the URL should contain "/sales"

  @SeedDashboardTests
  Scenario: TC_DSH_USR_03 - Verify "Main" and "Sub" category counts are reflected on the "Categories" dashboard card
    When the "Categories" card is visible on the Dashboard page
    Then the "Categories" card should show "1" for "Main" count
    And the "Categories" card should show "2" for "Sub" count

  @SeedDashboardTests
  Scenario: TC_DSH_USR_04 - Verify "Total" and "Low stock" plant counts are reflected on the "Plants" dashboard card
    When the "Plants" card is visible on the Dashboard page
    Then the "Plants" card should show "2" for "Total" count
    And the "Plants" card should show "1" for "Low Stock" count

  @SeedDashboardTests
  Scenario: TC_DSH_USR_05 - Verify "Revenue" and "Sales" counts are reflected on the "Sales" dashboard card
    When the "Sales" card is visible on the Dashboard page
    Then the "Sales" card should show "Rs 550.0" for "Revenue" count
    And the "Sales" card should show "2" for "Sales" count