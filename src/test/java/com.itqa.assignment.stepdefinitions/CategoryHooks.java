package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.utilities.ApiHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hooks for Category test scenarios that require seeded test data.
 * Creates main category and sub-category via API before tests,
 * and cleans up afterwards.
 */
public class CategoryHooks {

    // Scenario-scoped lists to track IDs for cleanup
    private static List<Integer> categorySearchTestCategoryIds = new ArrayList<>();

    // Separate lists for sort by ID test
    private static List<Integer> categorySortTestCategoryIds = new ArrayList<>();
    private static String sortTestParentCategoryName;

    // Store created names for verification in tests
    private static String createdMainCategoryName;
    private static String createdSubCategoryName;

    // ==================== Hooks for Category Search Test ====================

    @Before(value = "@SeedCategorySearchTest", order = 1)
    public void seedCategoriesForSearchTest() {
        // Generate unique 3-digit suffix to avoid conflicts (zero-padded)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);

        // 1. Create Main Category (name must be 3-10 characters)
        createdMainCategoryName = "SrchM" + suffix;
        Map<String, Object> mainCategoryBody = Map.of("name", createdMainCategoryName);
        String mainCategoryId = ApiHelper.postAndGetId("/categories", mainCategoryBody);
        categorySearchTestCategoryIds.add(Integer.parseInt(mainCategoryId));

        // 2. Create Sub-category (linked to main category, name must be 3-10 characters)
        createdSubCategoryName = "SrchS" + suffix;
        Map<String, Object> subCategoryBody = Map.of(
                "name", createdSubCategoryName,
                "parent", Map.of(
                        "id", Integer.parseInt(mainCategoryId),
                        "name", createdMainCategoryName
                )
        );
        String subCategoryId = ApiHelper.postAndGetId("/categories", subCategoryBody);
        categorySearchTestCategoryIds.add(Integer.parseInt(subCategoryId));
    }

    @After(value = "@SeedCategorySearchTest", order = 1)
    public void cleanupCategorySearchTest() {
        // Delete categories in reverse order (sub-category first, then main)
        for (int i = categorySearchTestCategoryIds.size() - 1; i >= 0; i--) {
            try {
                ApiHelper.deleteResource("/categories", categorySearchTestCategoryIds.get(i));
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        categorySearchTestCategoryIds.clear();
    }

    // ==================== Hooks for Category Sort by ID Test ====================

    @Before(value = "@SeedCategorySortTest", order = 1)
    public void seedCategoriesForSortTest() {
        // Generate unique 3-digit suffix to avoid conflicts (zero-padded)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);

        // 1. Create Main/Parent Category (name must be 3-10 characters)
        sortTestParentCategoryName = "SortP" + suffix;
        Map<String, Object> parentCategoryBody = Map.of("name", sortTestParentCategoryName);
        String parentCategoryId = ApiHelper.postAndGetId("/categories", parentCategoryBody);
        categorySortTestCategoryIds.add(Integer.parseInt(parentCategoryId));

        // 2. Create 3 Sub-categories (linked to parent category)
        String[] subCatNames = {"ASort" + suffix, "BSort" + suffix, "CSort" + suffix};

        for (String subCatName : subCatNames) {
            Map<String, Object> subCategoryBody = Map.of(
                    "name", subCatName,
                    "parent", Map.of(
                            "id", Integer.parseInt(parentCategoryId),
                            "name", sortTestParentCategoryName
                    )
            );
            String subCategoryId = ApiHelper.postAndGetId("/categories", subCategoryBody);
            categorySortTestCategoryIds.add(Integer.parseInt(subCategoryId));
        }
    }

    @After(value = "@SeedCategorySortTest", order = 1)
    public void cleanupCategorySortTest() {
        // Delete categories in reverse order (sub-categories first, then parent)
        for (int i = categorySortTestCategoryIds.size() - 1; i >= 0; i--) {
            try {
                ApiHelper.deleteResource("/categories", categorySortTestCategoryIds.get(i));
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        categorySortTestCategoryIds.clear();
    }

    // Getter methods to access created data in step definitions
    public static String getCreatedMainCategoryName() {
        return createdMainCategoryName;
    }

    public static String getCreatedSubCategoryName() {
        return createdSubCategoryName;
    }

    public static String getSortTestParentCategoryName() {
        return sortTestParentCategoryName;
    }
}
