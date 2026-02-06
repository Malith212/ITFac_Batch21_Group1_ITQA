@api @user
Feature: Category API Validation (User)

  @SeedCategorySortTest
  Scenario: TC_CAT_USR_06 - Verify user can retrieve categories
    Given user auth token is set
    When the user sends a GET request to get categories from "/categories"
    Then the response status code should be 200
    And the response should contain a category list


  @SeedCategorySearchTest
  Scenario: TC_CAT_USR_07 - Verify user can view category by ID
    Given user auth token is set
    And a valid category ID exists
    When the user sends a GET request to get categories from "/categories/{categoryId}"
    Then the response status code should be 200
    And the response should contain the category details

  @CleanUpCategory
  Scenario: TC_SLS_USR_11 - Verify user cannot create category
    Given user auth token is set
    When the user sends a POST request to "/categories" with category details:
      | name | TstCty |
    Then the response status code should be 403

  @SeedCategorySearchTest
  Scenario: TC_SLS_USR_12 - Verify user cannot update category
    Given user auth token is set
    And a valid category ID exists
    When the user sends a PUT request to "/categories/{categoryId}" with category details:
      | name | UpdatedCt |
    Then the response status code should be 403

  @SeedCategorySearchTest
  Scenario: TC_SLS_USR_13 - Verify user cannot delete category
    Given user auth token is set
    And a valid category ID exists
    When the user sends a DELETE request to delete a category from "/categories/{categoryId}"
    Then the response status code should be 403

#    PRAMESH

  @CleanUpCategory
  Scenario: TC_CAT_USR_16 - Verify user cannot get category with non-numeric ID
    Given user auth token is set
    When the user sends a GET request to get categories from "/categories/abc"
    Then the response status code should be 400

  @CleanUpCategory
  Scenario: TC_CAT_USR_17 - Verify user cannot access admin-only category summary
    Given user auth token is set
    When the user sends a GET request to get categories from "/categories/summary"
    Then the response status code should be 403