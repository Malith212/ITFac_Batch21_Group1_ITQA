package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.DatabaseUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardHooks {
    // Scenario-scoped list to track IDs for cleanup
    private List<Integer> plantIds = new ArrayList<>();
    private List<Integer> categoryIds = new ArrayList<>();
    private List<Integer> saleIds = new ArrayList<>();

    @Before(value = "@SeedDashboardTests", order = 1)
    public void seedCategoriesSeparately() {
        // 1. Create Parent
        String parentName = "Indoor";
        Map<String, Object> parentBody = Map.of("name", parentName);
        String parentId = ApiHelper.postAndGetId("/categories", parentBody);
        categoryIds.add(Integer.parseInt(parentId));

        // 2. Create Sub-category 1 (Linking to parentId)
        Map<String, Object> sub1Body = Map.of(
                "name", "Ferns",
                "parent", Map.of(
                        "id", Integer.parseInt(parentId),
                        "name", parentName
                )
        );
        String sub1Id = ApiHelper.postAndGetId("/categories", sub1Body);
        categoryIds.add(Integer.parseInt(sub1Id));

        // 3. Create Sub-category 2
        Map<String, Object> sub2Body = Map.of(
                "name", "Palms",
                "parent", Map.of(
                        "id", Integer.parseInt(parentId),
                        "name", parentName
                )
        );
        String sub2Id = ApiHelper.postAndGetId("/categories", sub2Body);
        categoryIds.add(Integer.parseInt(sub2Id));
    }

    @Before(value = "@SeedDashboardTests", order = 2)
    public void seedPlants() {
        // Body 1: A Low Stock Plant
        Map<String, Object> plant1 = Map.of(
                "name", "LowFern",
                "quantity", 4, // Below threshold
                    "price", 250,
                "categoryId", categoryIds.get(1)
        );
        String id1 = ApiHelper.postAndGetId("/plants/category/"+categoryIds.get(1), plant1);
        plantIds.add(Integer.parseInt(id1));

        // Body 2: A Normal Stock Plant
        Map<String, Object> plant2 = Map.of(
                "name", "HighPalm",
                "quantity", 50, // Above threshold
                    "price", 300,
                "categoryId", categoryIds.get(2)
        );
        String id2 = ApiHelper.postAndGetId("/plants/category/"+categoryIds.get(2), plant2);
        plantIds.add(Integer.parseInt(id2));
    }

    @Before(value = "@SeedDashboardTests", order = 3)
    public void seedSales() {
        Map<String, Object> sale1 = Map.of(
                "quantity", 1
        );
        String saleId1 = ApiHelper.postWithParams("/sales/plant/"+plantIds.get(0), sale1);
        saleIds.add(Integer.parseInt(saleId1));

        Map<String, Object> sale2 = Map.of(
                "quantity", 1
        );
        String saleId2 = ApiHelper.postWithParams("/sales/plant/"+plantIds.get(1), sale2);
        saleIds.add(Integer.parseInt(saleId2));
    }

    @After(value = "@SeedDashboardTests", order = 2)
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
}
