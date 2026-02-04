@ui
Feature: Sales User Functionality

  Background:
    Given the user is logged into the system

  @SeedSalesUserTests
  Scenario: TC_SLS_USR_01 - Verify that user can view the Sales list page
    Given the user navigates to the "sales" page via sidebar
    Then the Sales page header should be visible
    And the Sales table should be displayed with columns: Plant, Quantity, Total Price, Sold At
    And the pagination controls should be visible

  @SeedSalesUserTests
  Scenario: TC_SLS_USR_02 - Verify sorting by plant name
    Given the user navigates to the "sales" page via sidebar
    When the user clicks on the "Plant" column header
    Then the sales records should be sorted alphabetically by Plant name

  @SeedSalesUserTests
  Scenario: TC_SLS_USR_03 - Verify sorting by quantity
    Given the user navigates to the "sales" page via sidebar
    When the user clicks on the "Quantity" column header
    Then the sales records should be sorted numerically by Quantity

  @SeedSalesUserTests
  Scenario: TC_SLS_USR_04 - Verify sorting by total price
    Given the user navigates to the "sales" page via sidebar
    When the user clicks on the "Total Price" column header
    Then the sales records should be sorted numerically by Total Price

  Scenario: TC_SLS_USR_05 - Verify message when no sales exist
    Given the user navigates to the "sales" page via sidebar
    And there are no sales records in the system
    Then the message "No sales found" should be displayed in the table
