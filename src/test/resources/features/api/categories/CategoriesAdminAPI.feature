@api @admin
Feature: Category API Validation for Authenticated Admin

  Background:
    Given admin auth token is set

  @CleanUpCategory
  Scenario: TC_CAT_ADM_06 - Verify admin can create main category
    When the admin sends a POST request to "/categories" with a valid name and no parent
    Then the response status code should be 201
    And the response should contain the created category

  @SeedCategorySearchTest  @CleanUpCategory
  Scenario: TC_CAT_ADM_07 - Verify admin can create sub-category
    And a valid parent category ID exists
    When the admin sends a POST request to "/categories" with a valid name and parent ID
    Then the response status code should be 201
    And the response should contain the created sub-category linked to the parent

  @SeedCategorySearchTest
  Scenario: TC_CAT_ADM_08 - Verify admin can update category
    And a valid category ID exists
    When the admin sends a PUT request to "/categories/{categoryId}" with category details:
      | name | UpdatedCt |
    Then the response status code should be 200

  @SeedCategorySearchTest
  Scenario: TC_CAT_ADM_09 - Verify admin can delete category
    And a valid sub category ID exists
    When the admin sends a DELETE request to delete a category from "/categories/{categoryId}"
    Then the response status code should be 204

  @SeedCategorySortTest
  Scenario: TC_CAT_ADM_10 - Verify pagination and sorting of categories
    When the admin sends a GET request to get paginated categories from "/categories/page?page=0&size=10&sortField=id&sortDir=desc"
    Then the response status code should be 200
    And the response should contain a paginated and sorted category list

  @CleanUpCategory
  Scenario: TC_CAT_ADM_16 - Admin cannot create category with duplicate name
    And a category already exists with name "Indoor"
    When admin sends POST request to "/categories" with category name "Indoor"
    Then the response status code should be 400
    And the response should contain an error message "Main category 'Indoor' already exists"

  @CleanUpCategory
  Scenario: TC_CAT_ADM_17 - Admin cannot create category with name less than minimum length
    When admin sends POST request to "/categories" with category name "Ab"
    Then the response status code should be 400
    And the response should contain a validation error for "name" with message "Category name must be between 3 and 10 characters"

  @CleanUpCategory
  Scenario: TC_CAT_ADM_18 - Admin cannot create category with name greater than maximum length
    When admin sends POST request to "/categories" with category name "VeryLongCategoryName"
    Then the response status code should be 400
    And the response should contain a validation error for "name" with message "Category name must be between 3 and 10 characters"

  @CleanUpCategory
  Scenario: TC_CAT_ADM_19 - Admin cannot create category with invalid parent ID
    When admin sends POST request to "/categories" with category name "InvalidSub" and invalid parent id 999999
    Then the response status code should be 400

  @SeedCategorySearchTest @CleanUpCategory
  Scenario: TC_CAT_ADM_20 - Admin cannot update category with empty name
    And a valid category ID exists
    When the admin sends a PUT request to "/categories/{categoryId}" with category details:
      | name |  |
    Then the response status code should be 400
    And the response should contain a validation error for "name" with message "Category name must be between 3 and 10 characters"