package com.pharmacy.automation.util;

import java.util.Map;

/**
 * Convenience factory methods for reusable demo data objects.
 */
public final class SampleDataUtils {

    private SampleDataUtils() {
    }

    public static Map<String, Object> cosmosUser(String id, String email, String tier) {
        return Map.of("id", id, "email", email, "tier", tier);
    }

    public static Map<String, Object> cosmosOrder(String id, String userId, double total) {
        return Map.of("id", id, "userId", userId, "total", total);
    }
}
