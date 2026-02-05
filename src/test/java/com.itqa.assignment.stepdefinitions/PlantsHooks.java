package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.utilities.ApiHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hooks for Plant User test scenarios that require seeded test data.
 * Creates main category, sub-category and plant via API before tests,
 * and cleans up Afterward.
 */
public class PlantsHooks {

    // Scenario-scoped lists to track IDs for cleanup
    private static final List<Integer> plantIds = new ArrayList<>();
    private static final List<Integer> categoryIds = new ArrayList<>();

    // Separate lists for sort test
    private static final List<Integer> sortTestPlantIds = new ArrayList<>();
    private static final List<Integer> sortTestCategoryIds = new ArrayList<>();

    // Separate lists for category dropdown test (TC_PLT_ADM_02)
    private static final List<Integer> categoryDropdownTestCategoryIds = new ArrayList<>();
    private static String categoryDropdownMainCatName;
    private static String categoryDropdownSubCatName;

    // Separate lists for plant list test (TC_PLT_USR_01)
    private static final List<Integer> plantListTestPlantIds = new ArrayList<>();
    private static final List<Integer> plantListTestCategoryIds = new ArrayList<>();

    // Store created names for verification in tests
    private static String createdPlantName;
    private static String createdMainCategoryName;
    private static String createdSubCategoryName;

    // Store created plant names for sort test
    private static final List<String> createdPlantNamesForSort = new ArrayList<>();

    @Before(value = "@SeedPlantSearchTest", order = 1)
    public void seedCategoryAndPlant() {
        // Generate unique 4-digit suffix to avoid conflicts (zero-padded)
        String suffix = String.format("%04d", System.currentTimeMillis() % 10000);

        // 1. Create Main Category (name must be 3-10 characters)
        createdMainCategoryName = "Main" + suffix;
        Map<String, Object> mainCategoryBody = Map.of("name", createdMainCategoryName);
        String mainCategoryId = ApiHelper.postAndGetId("/categories", mainCategoryBody);
        categoryIds.add(Integer.parseInt(mainCategoryId));

        // 2. Create Sub-category (linked to main category, name must be 3-10 characters)
        createdSubCategoryName = "Sub" + suffix;
        Map<String, Object> subCategoryBody = Map.of(
                "name", createdSubCategoryName,
                "parent", Map.of(
                        "id", Integer.parseInt(mainCategoryId),
                        "name", createdMainCategoryName
                )
        );
        String subCategoryId = ApiHelper.postAndGetId("/categories", subCategoryBody);
        categoryIds.add(Integer.parseInt(subCategoryId));

        // 3. Create Plant under the sub-category (name must be 3-25 characters)
        createdPlantName = "SrchPlant" + suffix;
        Map<String, Object> plantBody = Map.of(
                "name", createdPlantName,
                "quantity", 10,
                "price", 100,
                "categoryId", Integer.parseInt(subCategoryId)
        );
        String plantId = ApiHelper.postAndGetId("/plants/category/" + subCategoryId, plantBody);
        plantIds.add(Integer.parseInt(plantId));
    }

    @After(value = "@SeedPlantSearchTest", order = 1)
    public void cleanup() {
        // Delete plants first
        for (int id : plantIds) {
            try {
                ApiHelper.deleteResource("/plants", id);
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        // Delete categories in reverse order (sub-category first, then main)
        for (int i = categoryIds.size() - 1; i >= 0; i--) {
            try {
                ApiHelper.deleteResource("/categories", categoryIds.get(i));
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        plantIds.clear();
        categoryIds.clear();
    }

    // ==================== Hooks for Plant Sorting Test (TC_PLT_USR_05) ====================

    @Before(value = "@SeedPlantSortTest", order = 1)
    public void seedPlantsForSortTest() {
        // Clear previous data
        createdPlantNamesForSort.clear();

        // Generate unique 4-digit suffix to avoid conflicts (zero-padded)
        String suffix = String.format("%04d", System.currentTimeMillis() % 10000);

        // 1. Create Main Category (name must be 3-10 characters)
        String mainCatName = "SortM" + suffix.substring(0, 3);
        Map<String, Object> mainCategoryBody = Map.of("name", mainCatName);
        String mainCategoryId = ApiHelper.postAndGetId("/categories", mainCategoryBody);
        sortTestCategoryIds.add(Integer.parseInt(mainCategoryId));

        // 2. Create Sub-category (linked to main category, name must be 3-10 characters)
        String subCatName = "SortS" + suffix.substring(0, 3);
        Map<String, Object> subCategoryBody = Map.of(
                "name", subCatName,
                "parent", Map.of(
                        "id", Integer.parseInt(mainCategoryId),
                        "name", mainCatName
                )
        );
        String subCategoryId = ApiHelper.postAndGetId("/categories", subCategoryBody);
        sortTestCategoryIds.add(Integer.parseInt(subCategoryId));

        // 3. Create 5 Plants with different names (not in alphabetical order)
        // Names chosen to test alphabetical sorting: Zebra, Apple, Mango, Banana, Orange
        String[] plantNames = {"Zebra" + suffix, "Apple" + suffix, "Mango" + suffix, "Banana" + suffix, "Orange" + suffix};

        for (String plantName : plantNames) {
            Map<String, Object> plantBody = Map.of(
                    "name", plantName,
                    "quantity", 10,
                    "price", 100,
                    "categoryId", Integer.parseInt(subCategoryId)
            );
            String plantId = ApiHelper.postAndGetId("/plants/category/" + subCategoryId, plantBody);
            sortTestPlantIds.add(Integer.parseInt(plantId));
            createdPlantNamesForSort.add(plantName);
        }
    }

    @After(value = "@SeedPlantSortTest", order = 1)
    public void cleanupSortTest() {
        // Delete plants first
        for (int id : sortTestPlantIds) {
            try {
                ApiHelper.deleteResource("/plants", id);
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        // Delete categories in reverse order (sub-category first, then main)
        for (int i = sortTestCategoryIds.size() - 1; i >= 0; i--) {
            try {
                ApiHelper.deleteResource("/categories", sortTestCategoryIds.get(i));
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        sortTestPlantIds.clear();
        sortTestCategoryIds.clear();
        createdPlantNamesForSort.clear();
    }

    // ==================== Hooks for Category Dropdown Test (TC_PLT_ADM_02) ====================

    @Before(value = "@SeedCategoryDropdownTest", order = 1)
    public void seedCategoriesForDropdownTest() {
        // Generate unique 3-digit suffix to avoid conflicts (zero-padded)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);

        // 1. Create Main Category (name must be 3-10 characters)
        categoryDropdownMainCatName = "DropM" + suffix;
        Map<String, Object> mainCategoryBody = Map.of("name", categoryDropdownMainCatName);
        String mainCategoryId = ApiHelper.postAndGetId("/categories", mainCategoryBody);
        categoryDropdownTestCategoryIds.add(Integer.parseInt(mainCategoryId));

        // 2. Create Sub-category (linked to main category, name must be 3-10 characters)
        categoryDropdownSubCatName = "DropS" + suffix;
        Map<String, Object> subCategoryBody = Map.of(
                "name", categoryDropdownSubCatName,
                "parent", Map.of(
                        "id", Integer.parseInt(mainCategoryId),
                        "name", categoryDropdownMainCatName
                )
        );
        String subCategoryId = ApiHelper.postAndGetId("/categories", subCategoryBody);
        categoryDropdownTestCategoryIds.add(Integer.parseInt(subCategoryId));
    }

    @After(value = "@SeedCategoryDropdownTest", order = 1)
    public void cleanupCategoryDropdownTest() {
        // Delete categories in reverse order (sub-category first, then main)
        for (int i = categoryDropdownTestCategoryIds.size() - 1; i >= 0; i--) {
            try {
                ApiHelper.deleteResource("/categories", categoryDropdownTestCategoryIds.get(i));
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        categoryDropdownTestCategoryIds.clear();
    }

    // ==================== Hooks for Plant List Test (TC_PLT_USR_01) ====================

    @Before(value = "@SeedPlantListTest", order = 1)
    public void seedPlantsForListTest() {
        // Generate unique 3-digit suffix to avoid conflicts (zero-padded)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);

        // 1. Create Main Category (name must be 3-10 characters)
        String mainCatName = "ListM" + suffix;
        Map<String, Object> mainCategoryBody = Map.of("name", mainCatName);
        String mainCategoryId = ApiHelper.postAndGetId("/categories", mainCategoryBody);
        plantListTestCategoryIds.add(Integer.parseInt(mainCategoryId));

        // 2. Create Sub-category (linked to main category, name must be 3-10 characters)
        String subCatName = "ListS" + suffix;
        Map<String, Object> subCategoryBody = Map.of(
                "name", subCatName,
                "parent", Map.of(
                        "id", Integer.parseInt(mainCategoryId),
                        "name", mainCatName
                )
        );
        String subCategoryId = ApiHelper.postAndGetId("/categories", subCategoryBody);
        plantListTestCategoryIds.add(Integer.parseInt(subCategoryId));

        // 3. Create 15 Plants with different names
        String[] plantNames = {
                "Aloe" + suffix, "Basil" + suffix, "Cactus" + suffix, "Daisy" + suffix, "Elm" + suffix,
                "Fern" + suffix, "Grass" + suffix, "Holly" + suffix, "Iris" + suffix, "Jade" + suffix,
                "Kale" + suffix, "Lily" + suffix, "Maple" + suffix, "Nettle" + suffix, "Oak" + suffix
        };

        for (String plantName : plantNames) {
            Map<String, Object> plantBody = Map.of(
                    "name", plantName,
                    "quantity", 15,
                    "price", 50,
                    "categoryId", Integer.parseInt(subCategoryId)
            );
            String plantId = ApiHelper.postAndGetId("/plants/category/" + subCategoryId, plantBody);
            plantListTestPlantIds.add(Integer.parseInt(plantId));
        }
    }

    @After(value = "@SeedPlantListTest", order = 1)
    public void cleanupPlantListTest() {
        // Delete plants first
        for (int id : plantListTestPlantIds) {
            try {
                ApiHelper.deleteResource("/plants", id);
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        // Delete categories in reverse order (sub-category first, then main)
        for (int i = plantListTestCategoryIds.size() - 1; i >= 0; i--) {
            try {
                ApiHelper.deleteResource("/categories", plantListTestCategoryIds.get(i));
            } catch (Exception e) {
                // Ignore if already deleted
            }
        }

        plantListTestPlantIds.clear();
        plantListTestCategoryIds.clear();
    }

    // Getter methods to access created data in step definitions
    public static String getCreatedPlantName() {
        return createdPlantName;
    }

    public static String getCreatedMainCategoryName() {
        return createdMainCategoryName;
    }

    public static String getCreatedSubCategoryName() {
        return createdSubCategoryName;
    }

    public static List<String> getCreatedPlantNamesForSort() {
        return createdPlantNamesForSort;
    }

    // Getter methods for category dropdown test (TC_PLT_ADM_02)
    public static String getCategoryDropdownMainCatName() {
        return categoryDropdownMainCatName;
    }

    public static String getCategoryDropdownSubCatName() {
        return categoryDropdownSubCatName;
    }

    // Getter methods for plant IDs and category IDs (for API tests)
    public static List<Integer> getPlantIds() {
        return plantIds;
    }

    public static List<Integer> getCategoryIds() {
        return categoryIds;
    }
}
