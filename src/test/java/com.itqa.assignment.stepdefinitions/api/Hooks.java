package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.utilities.ApiCleanupUtil;
import io.cucumber.java.After;

public class Hooks {
    ApiCleanupUtil apiCleanupUtil = new ApiCleanupUtil();

    @After(value="@CleanUpSales", order = 10)
    public void cleanUpSalesData() {
        apiCleanupUtil.cleanUpInventory();
        apiCleanupUtil.cleanUpSales();
        apiCleanupUtil.cleanUpPlants();
        apiCleanupUtil.cleanUpCategory();
    }

    @After(value="@CleanUpPlants", order = 10)
    public void cleanUpPlantsData() {
        apiCleanupUtil.cleanUpPlants();
        apiCleanupUtil.cleanUpCategory();
    }

    @After(value="@CleanUpCategory", order = 10)
    public void cleanUpCategoryData() {
        apiCleanupUtil.cleanUpCategory();
    }

    @After(value="@api", order = 20)
    public void generalContextCleanup() {
        ScenarioContext.clear();
    }
}
