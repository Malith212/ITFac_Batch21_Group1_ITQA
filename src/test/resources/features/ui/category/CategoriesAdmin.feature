@ui @admin
Feature: Category Management - Admin

  Scenario: View category list
    And the admin is on the Categories page
    Then the categories page should be displayed
    And the Categories header should be visible
    And search sub category input field should be visible
    And category dropdown should be visible
    And search and reset buttons should be visible
    And add category button should be visible
    And category table should display ID, Name, Parent, Actions columns

  Scenario: Sort categories by ID
    And the admin is on the Categories page
    When the admin clicks on the ID column header
    Then the sorting indicator should appear on the ID column
    And categories should be sorted by ID

  Scenario: Sort categories by Name
    And the admin is on the Categories page
    When the admin clicks on the Name column header
    Then the sorting indicator should appear on the Name column
    And categories should be sorted alphabetically by name

#  Scenario: Delete Category
#    And the admin is on the Categories page
#    When the admin searches for a category record
#    And the admin clicks the delete button
#    Then the category should be deleted successfully
#    And success message should be displayed
#
#  Scenario: Search category by valid name
#    And the admin is on the Categories page
#    When the admin enters a valid category name in search field
#    And the admin clicks the Search button
#    Then matching category records should be displayed