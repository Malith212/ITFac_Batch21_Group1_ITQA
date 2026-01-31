package com.itqa.assignment.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized navigation helper for managing page navigation and preconditions.
 * Provides consistent navigation methods and URL management across the framework.
 */
public class NavigationHelper {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    // Centralized route definitions
    private static final Map<String, String> ROUTES = new HashMap<>();
    static {
        ROUTES.put("login", "/login");
        ROUTES.put("dashboard", "/dashboard");
        ROUTES.put("categories", "/categories");
        ROUTES.put("plants", "/plants");
        ROUTES.put("sales", "/sales");
        ROUTES.put("sell-plant", "/sales/new");
    }

    // Sidebar navigation locators
    private static final Map<String, By> SIDEBAR_LINKS = new HashMap<>();
    static {
        SIDEBAR_LINKS.put("dashboard", By.cssSelector("a[href='/ui/dashboard']"));
        SIDEBAR_LINKS.put("categories", By.cssSelector("a[href='/ui/categories']"));
        SIDEBAR_LINKS.put("plants", By.cssSelector("a[href='/ui/plants']"));
        SIDEBAR_LINKS.put("sales", By.cssSelector("a[href='/ui/sales']"));
    }

    /**
     * Navigate directly to a page by URL.
     * @param pageName the page key (e.g., "dashboard", "categories")
     */
    public static void navigateTo(String pageName) {
        String route = ROUTES.get(pageName.toLowerCase());
        if (route == null) {
            throw new IllegalArgumentException("Unknown page: " + pageName);
        }
        String baseUrl = ConfigReader.getProperty("ui.url");
        Driver.getDriver().get(baseUrl + route);
    }

    /**
     * Navigate to a page via sidebar click (requires being logged in).
     * @param pageName the page key (e.g., "dashboard", "categories")
     */
    public static void navigateViaSidebar(String pageName) {
        By sidebarLink = SIDEBAR_LINKS.get(pageName.toLowerCase());
        if (sidebarLink == null) {
            throw new IllegalArgumentException("Unknown sidebar link: " + pageName);
        }
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.elementToBeClickable(sidebarLink)).click();
    }

    /**
     * Wait until URL contains the expected segment.
     * @param urlSegment the URL segment to wait for
     */
    public static void waitForUrlContains(String urlSegment) {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.urlContains(urlSegment.toLowerCase()));
    }

    /**
     * Check if currently on a specific page.
     * @param pageName the page key to check
     * @return true if current URL contains the page route
     */
    public static boolean isOnPage(String pageName) {
        String route = ROUTES.get(pageName.toLowerCase());
        if (route == null) {
            return false;
        }
        return Driver.getDriver().getCurrentUrl().contains(route);
    }

    /**
     * Get a reusable WebDriverWait instance.
     * @return WebDriverWait with default timeout
     */
    public static WebDriverWait getWait() {
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
    }
}
