package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.DatabaseUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesHooks {
    // Scenario-scoped list to track IDs for cleanup
    private final List<Integer> plantIds = new ArrayList<>();
    private final List<Integer> categoryIds = new ArrayList<>();
    private final List<Integer> saleIds = new ArrayList<>();

    // ========== ADMIN SALES TESTS ==========
    @Before(value = "@SeedSalesAdminTests", order = 1)
    public void seedCategoriesForAdminTests() {
        // 1. Create Parent Category
        String parentName = "Outdoor";
        Map<String, Object> parentBody = Map.of("name", parentName);
        String parentId = ApiHelper.postAndGetId("/categories", parentBody);
        categoryIds.add(Integer.parseInt(parentId));

        // 2. Create Sub-category
        Map<String, Object> subBody = Map.of(
                "name", "Flowering",
                "parent", Map.of(
                        "id", Integer.parseInt(parentId),
                        "name", parentName
                )
        );
        String subId = ApiHelper.postAndGetId("/categories", subBody);
        categoryIds.add(Integer.parseInt(subId));
    }

    @Before(value = "@SeedSalesAdminTests", order = 2)
    public void seedPlantsForAdminTests() {
        // Plant 1: Available stock for testing
        Map<String, Object> plant1 = Map.of(
                "name", "Rose Plant",
                "quantity", 15,
                "price", 350,
                "categoryId", categoryIds.get(1)
        );
        String id1 = ApiHelper.postAndGetId("/plants/category/" + categoryIds.get(1), plant1);
        plantIds.add(Integer.parseInt(id1));

        // Plant 2: Available stock for dropdown test
        Map<String, Object> plant2 = Map.of(
                "name", "Jasmine Plant",
                "quantity", 10,
                "price", 300,
                "categoryId", categoryIds.get(1)
        );
        String id2 = ApiHelper.postAndGetId("/plants/category/" + categoryIds.get(1), plant2);
        plantIds.add(Integer.parseInt(id2));
    }

    @After(value = "@SeedSalesAdminTests", order = 2)
    public void cleanup() {
        DatabaseUtil.executeUpdate("delete from inventory");

        for (int id : saleIds) {
            ApiHelper.deleteResource("/sales",id);
        }

        // Loop through and delete everything created in this scenario
        for (int id : plantIds) {
            ApiHelper.deleteResource("/plants",id);
        }

        // Reverse the list: Delete Sub 2, then Sub 1, then Parent
        for (int i = categoryIds.size() - 1; i >= 0; i--) {
            ApiHelper.deleteResource("/categories", categoryIds.get(i));
        }

        saleIds.clear();
        plantIds.clear();
        categoryIds.clear();
    }

    // ========== USER SALES TESTS ==========
    @Before(value = "@SeedSalesUserTests", order = 1)
    public void seedCategoriesForUserTests() {
        // 1. Create Parent Category
        String parentName = "Garden";
        Map<String, Object> parentBody = Map.of("name", parentName);
        String parentId = ApiHelper.postAndGetId("/categories", parentBody);
        categoryIds.add(Integer.parseInt(parentId));

        // 2. Create Sub-category
        Map<String, Object> subBody = Map.of(
                "name", "Vegetables",
                "parent", Map.of(
                        "id", Integer.parseInt(parentId),
                        "name", parentName
                )
        );
        String subId = ApiHelper.postAndGetId("/categories", subBody);
        categoryIds.add(Integer.parseInt(subId));
    }

    @Before(value = "@SeedSalesUserTests", order = 2)
    public void seedPlantsForUserTests() {
        // Plant 1: For sales record
        Map<String, Object> plant1 = Map.of(
                "name", "Tomato Plant",
                "quantity", 20,
                "price", 150,
                "categoryId", categoryIds.get(1)
        );
        String plantId1 = ApiHelper.postAndGetId("/plants/category/" + categoryIds.get(1), plant1);
        plantIds.add(Integer.parseInt(plantId1));

        // Plant 2: For sales record
        Map<String, Object> plant2 = Map.of(
                "name", "Cucumber Plant",
                "quantity", 8,
                "price", 120,
                "categoryId", categoryIds.get(1)
        );
        String plantId2 = ApiHelper.postAndGetId("/plants/category/" + categoryIds.get(1), plant2);
        plantIds.add(Integer.parseInt(plantId2));
    }

    @Before(value = "@SeedSalesUserTests", order = 3)
    public void seedSalesForUserTests() {
        // Plant 1 stock = 10
        for (int i = 0; i < 8; i++) {
            Map<String, Object> sale = Map.of("quantity", 1);
            String saleId = ApiHelper.postWithParams(
                    "/sales/plant/" + plantIds.get(0),
                    sale
            );
            saleIds.add(Integer.parseInt(saleId));
        }
        // Plant 2 stock = 8
        for (int i = 0; i < 5; i++) {
            Map<String, Object> sale = Map.of("quantity", 1);
            String saleId = ApiHelper.postWithParams(
                    "/sales/plant/" + plantIds.get(1),
                    sale
            );
            saleIds.add(Integer.parseInt(saleId));
        }
    }

    @After(value = "@SeedSalesUserTests", order = 2)
    public void cleanupAfterUserTests() {
        DatabaseUtil.executeUpdate("delete from inventory");

        for (int id : saleIds) {
            ApiHelper.deleteResource("/sales",id);
        }

        // Loop through and delete everything created in this scenario
        for (int id : plantIds) {
            ApiHelper.deleteResource("/plants",id);
        }

        // Reverse the list: Delete Sub 2, then Sub 1, then Parent
        for (int i = categoryIds.size() - 1; i >= 0; i--) {
            ApiHelper.deleteResource("/categories", categoryIds.get(i));
        }

        saleIds.clear();
        plantIds.clear();
        categoryIds.clear();
    }
}
