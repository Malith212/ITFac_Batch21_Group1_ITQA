@ui @user
Feature: Sales User Functionality

  Scenario: Verify that user can view the Sales list page
    Given the user navigates to the "sales" page via sidebar
    Then the Sales page header should be visible as "Sales"
    And the Sales table should be displayed with columns: Plant, Quantity, Total Price, Sold At
    And the pagination controls should be visible

  Scenario: Verify sorting by plant name
    Given the user navigates to the "sales" page via sidebar
    When the user clicks on the "Plant" column header
    Then the sales records should be sorted alphabetically by Plant name

  Scenario: Verify sorting by quantity
    Given the user navigates to the "sales" page via sidebar
    When the user clicks on the "Quantity" column header
    Then the sales records should be sorted numerically by Quantity

  Scenario: Verify sorting by total price
    Given the user navigates to the "sales" page via sidebar
    When the user clicks on the "Total Price" column header
    Then the sales records should be sorted numerically by Total Price

  Scenario: Verify message when no sales exist
    Given the user navigates to the "sales" page via sidebar
    And there are no sales records in the system
    Then the message "No sales found" should be displayed in the table
