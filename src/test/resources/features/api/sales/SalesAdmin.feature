@api @admin
Feature: Sales API Validation for authenticated Admin

  @SeedSalesAdminTests  @CleanUpSales
  Scenario: TC_SLS_ADM_06 - Verify admin can Create new plant sale
    Given admin auth token is set
    And valid plantId and quantity are provided
    When the admin sends a POST request to create a new plant sale at "/sales/plant"
    Then the response status code should be 201
    And the response body contains created Sales record with a valid saleId

  @SeedSalesAdminTests  @CleanUpSales
  Scenario: TC_SLS_ADM_07 - Verify admin can delete a sale record by valid sale ID
    Given admin auth token is set
    And a valid sale record exists in the database
    When the admin sends a DELETE request to "/sales/{id}" with valid sale ID
    Then the response status code should be 204

  @SeedSalesAdminTests  @CleanUpSales
  Scenario: TC_SLS_ADM_08 - Verify admin can get paginated sales records sorted by plant name
    Given admin auth token is set
    And sales records exist in the database
    When the admin send GET request to "/sales/page?page=0&size=3&sort=plantName"
    Then the response status code should be 200
    And the response contains a content array with max 3 items sorted by Plant Name

  @SeedSalesAdminTests  @CleanUpSales
  Scenario: TC_SLS_ADM_09 - Verify admin receives error when creating sale with quantity exceeding available stock
    Given admin auth token is set
    And a valid plantId with limited stock is available
    When the admin sends a POST request with large quantity exceeding stock to "/sales/plant"
    Then the response status code should be 400
    And the response contains error message about insufficient stock

  @CleanUpSales
  Scenario: TC_SLS_ADM_10 - Verify admin receives error when creating sale with invalid plant ID
    Given admin auth token is set
    And a non-existent plantId is provided
    When the admin sends a POST request to create sale with invalid plantId at "/sales/plant"
    Then the response status code should be 404
    And the response should contain an error message "Plant not found"

