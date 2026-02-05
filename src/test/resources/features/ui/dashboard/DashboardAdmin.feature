@ui
Feature: Dashboard Functionality

  Background:
    Given the admin is logged into the system

  Scenario Outline: TC_DSH_ADM_02 - Verify the navigation menu highlighting
    When the admin navigates to the "<Page>" page via sidebar
    Then the sidebar navigation should be visible
    And the "<Menu Item>" menu item should be highlighted

    Examples:
      | Page       | Menu Item  |
      | Dashboard  | Dashboard  |
      | Categories | Categories |
      | Sales      | Sales      |
      | Plants     | Plants     |

  Scenario: TC_DSH_ADM_03 - Verify the "Manage Categories" button redirects to the Category page
    Given the admin is on the "Dashboard" page
    When the admin clicks on the "Manage Categories" button in the Dashboard page
    Then the URL should contain "/categories"
