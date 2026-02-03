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
 * and cleans up afterwards.
 */
public class PlantsHooks {

    // Scenario-scoped lists to track IDs for cleanup
    private static List<Integer> plantIds = new ArrayList<>();
    private static List<Integer> categoryIds = new ArrayList<>();

    // Store created names for verification in tests
    private static String createdPlantName;
    private static String createdMainCategoryName;
    private static String createdSubCategoryName;

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
}
