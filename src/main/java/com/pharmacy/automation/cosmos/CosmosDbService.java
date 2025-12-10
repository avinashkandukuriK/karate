package com.pharmacy.automation.cosmos;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CosmosDbService {
    String getDatabase();

    List<Map<String, Object>> query(String containerName, String query, Map<String, Object> params);

    Optional<Map<String, Object>> findById(String containerName, String id);

    void upsert(String containerName, Map<String, Object> document);
}
