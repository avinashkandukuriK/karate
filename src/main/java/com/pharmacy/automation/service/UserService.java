package com.pharmacy.automation.service;

import com.pharmacy.automation.cosmos.CosmosDbService;
import com.pharmacy.automation.db.DbService;
import com.pharmacy.automation.db.DbTarget;
import com.pharmacy.automation.model.CosmosUserDocument;
import com.pharmacy.automation.model.OrderSummary;
import com.pharmacy.automation.model.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final DbService dbService;
    private final CosmosDbService cosmosDbService;

    public UserService(DbService dbService, CosmosDbService cosmosDbService) {
        this.dbService = dbService;
        this.cosmosDbService = cosmosDbService;
    }

    public Optional<User> findUser(Long id) {
        String sql = "select id, email, status, role from users where id = :id";
        return dbService.queryForObject(DbTarget.MAIN, sql, Map.of("id", id), User.class);
    }

    public List<OrderSummary> findOrdersForUser(Long userId) {
        String sql = "select id, user_id as userId, total, channel from orders where user_id = :userId";
        return dbService.query(DbTarget.MAIN, sql, Map.of("userId", userId)).stream()
                .map(row -> OrderSummary.builder()
                        .id((String) row.get("ID"))
                        .userId(((Number) row.get("USER_ID")).longValue())
                        .total(((Number) row.get("TOTAL")).doubleValue())
                        .channel((String) row.get("CHANNEL"))
                        .build())
                .toList();
    }

    public int countReportingUsersByStatus(String status) {
        String sql = "select count(1) as cnt from users_reporting where status = :status";
        return ((Number) dbService.query(DbTarget.REPORTING, sql, Map.of("status", status)).get(0).get("CNT"))
                .intValue();
    }

    public Optional<CosmosUserDocument> findCosmosUser(String id) {
        return cosmosDbService.findById("users", id).map(doc -> CosmosUserDocument.builder()
                .id(String.valueOf(doc.get("id")))
                .email(String.valueOf(doc.get("email")))
                .tier(String.valueOf(doc.get("tier")))
                .build());
    }
}
