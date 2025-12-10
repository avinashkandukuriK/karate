package com.pharmacy.automation.config;

import com.azure.cosmos.CosmosClientBuilder;
import com.pharmacy.automation.cosmos.AzureCosmosDbService;
import com.pharmacy.automation.cosmos.CosmosDbService;
import com.pharmacy.automation.cosmos.InMemoryCosmosDbService;
import com.pharmacy.automation.util.SampleDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CosmosConfig {

    @Value("${cosmos.connection-string:}")
    private String connectionString;

    @Value("${cosmos.database:karate-demo}")
    private String databaseName;

    @Bean
    public CosmosDbService cosmosDbService() {
        if (connectionString != null && !connectionString.isEmpty()) {
            log.info("Bootstrapping Azure Cosmos DB client");
            return new AzureCosmosDbService(new CosmosClientBuilder()
                    .endpointDiscoveryEnabled(true)
                    .connectionString(connectionString)
                    .consistencyLevel(com.azure.cosmos.ConsistencyLevel.EVENTUAL)
                    .buildClient(),
                    databaseName);
        }
        log.info("Using in-memory Cosmos DB service for tests");
        InMemoryCosmosDbService service = new InMemoryCosmosDbService(databaseName);
        service.upsert("users", SampleDataUtils.cosmosUser("cosmos-user-1", "cosmos@example.com", "gold"));
        service.upsert("users", SampleDataUtils.cosmosUser("cosmos-user-2", "shadow@example.com", "silver"));
        service.upsert("orders", SampleDataUtils.cosmosOrder("order-1", "cosmos-user-1", 99.99));
        return service;
    }
}
