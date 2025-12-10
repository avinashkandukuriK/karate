package com.pharmacy.automation.cosmos;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AzureCosmosDbService implements CosmosDbService {

    private final CosmosClient client;
    private final String database;

    public AzureCosmosDbService(CosmosClient client, String database) {
        this.client = client;
        this.database = database;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public List<Map<String, Object>> query(String containerName, String query, Map<String, Object> params) {
        CosmosContainer container = client.getDatabase(database).getContainer(containerName);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        CosmosPagedIterable<Map> iterable = container.queryItems(query, params, options, Map.class);
        return iterable.stream().map(item -> (Map<String, Object>) item).collect(Collectors.toList());
    }

    @Override
    public Optional<Map<String, Object>> findById(String containerName, String id) {
        CosmosContainer container = client.getDatabase(database).getContainer(containerName);
        try {
            Map<String, Object> item = container.readItem(id, new com.azure.cosmos.models.PartitionKey(id), Map.class).getItem();
            return Optional.ofNullable(item);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public void upsert(String containerName, Map<String, Object> document) {
        CosmosContainer container = client.getDatabase(database).getContainer(containerName);
        container.upsertItem(document, new com.azure.cosmos.models.PartitionKey(document.get("id")), new CosmosItemRequestOptions());
    }
}
