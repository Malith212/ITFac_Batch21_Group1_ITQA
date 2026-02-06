@api @user
Feature: Sales API Validation for authenticated User

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_06 - Verify User can get all sales records
    Given user auth token is set
    And sales records exist in the database for user tests
    When the user send GET request to "/sales"
    Then the response status code should be 200
    And the response contains a sales record array

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_07 - Verify User can get paginated sales records sorted by quantity
    Given user auth token is set
    And sales records exist in the database for user tests
    When the user sends a GET request to "/sales/page?page=0&size=3&sort=quantity"
    Then the response status code should be 200
    And the response contains a content array with max 3 items sorted by Quantity

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_08 - Verify User can get paginated sales records sorted by total price
    Given user auth token is set
    And sales records exist in the database for user tests
    When the user sends a GET request to "/sales/page?page=0&size=3&sort=totalPrice"
    Then the response status code should be 200
    And the response contains a content array with max 3 items sorted by Total Price

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_09 - Verify User cannot create a plant sale
    Given user auth token is set
    And valid plantId and quantity are provided for user
    When the user sends a POST request to create a new plant sale at "/sales/plant"
    Then the response status code should be 403

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_10 - Verify User cannot delete a sale record
    Given user auth token is set
    And a valid sale record exists in the database for user tests
    When the user sends a DELETE request to "/sales/{id}" with valid sale ID
    Then the response status code should be 403

#PRAMESH

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_18 - Verify User can get paginated sales records sorted by quantity
    Given user auth token is set
    And sales records exist in the database for user tests
    When the user sends a GET request to "/sales/page?page=0&size=3&sort=quantity"
    Then the response status code should be 200
    And the response contains a content array with max 3 items sorted by Quantity

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_08 - Verify User can get paginated sales records sorted by total price
    Given user auth token is set
    And sales records exist in the database for user tests
    When the user sends a GET request to "/sales/page?page=0&size=3&sort=totalPrice"
    Then the response status code should be 200
    And the response contains a content array with max 3 items sorted by Total Price

  @SeedSalesUserTests @CleanUpSales
  Scenario: TC_SLS_USR_20 - Verify User can get paginated sales records sorted by Sold Date
    Given user auth token is set
    And sales records exist in the database for user tests
    When the user sends a GET request to "/sales/page?page=0&size=3&sort=soldAt"
    Then the response status code should be 200
    And the response contains a content array with max 3 items sorted by Sold Date