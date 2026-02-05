@api @admin
Feature: Category API Validation (Admin)

  @CleanUpCategory
  Scenario: TC_CAT_ADM_06 - Verify admin can create main category
    Given admin auth token is set
    When the admin sends a POST request to "/categories" with a valid name and no parent
    Then the response status code should be 201
    And the response should contain the created category

  @SeedCategorySearchTest  @CleanUpCategory
  Scenario: TC_CAT_ADM_07 - Verify admin can create sub-category
    Given admin auth token is set
    And a valid parent category ID exists
    When the admin sends a POST request to "/categories" with a valid name and parent ID
    Then the response status code should be 201
    And the response should contain the created sub-category linked to the parent

  @SeedCategorySearchTest
  Scenario: TC_CAT_ADM_08 - Verify admin can update category
    Given admin auth token is set
    And a valid category ID exists
    When the admin sends a PUT request to "/categories/{categoryId}" with category details:
      | name | UpdatedCt |
    Then the response status code should be 200

  @SeedCategorySearchTest
  Scenario: TC_CAT_ADM_09 - Verify admin can delete category
    Given admin auth token is set
    And a valid sub category ID exists
    When the admin sends a DELETE request to delete a category from "/categories/{categoryId}"
    Then the response status code should be 204

  @SeedCategorySortTest
  Scenario: TC_CAT_ADM_10 - Verify pagination and sorting of categories
    Given admin auth token is set
    When the admin sends a GET request to get paginated categories from "/categories/page?page=0&size=10&sortField=id&sortDir=desc"
    Then the response status code should be 200
    And the response should contain a paginated and sorted category list