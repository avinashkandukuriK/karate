package com.pharmacy.automation.cosmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryCosmosDbService implements CosmosDbService {

    private final String database;
    private final Map<String, Map<String, Map<String, Object>>> store = new ConcurrentHashMap<>();

    public InMemoryCosmosDbService(String database) {
        this.database = database;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public List<Map<String, Object>> query(String containerName, String query, Map<String, Object> params) {
        Map<String, Map<String, Object>> container = store.getOrDefault(containerName, Map.of());
        List<Map<String, Object>> values = new ArrayList<>(container.values());
        if (params == null || params.isEmpty()) {
            return values;
        }
        return values.stream()
                .filter(item -> params.entrySet().stream().allMatch(e -> e.getValue().equals(item.get(e.getKey()))))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Map<String, Object>> findById(String containerName, String id) {
        return Optional.ofNullable(store.getOrDefault(containerName, Map.of()).get(id));
    }

    @Override
    public void upsert(String containerName, Map<String, Object> document) {
        store.computeIfAbsent(containerName, key -> new ConcurrentHashMap<>())
                .put(String.valueOf(document.get("id")), document);
    }
}
