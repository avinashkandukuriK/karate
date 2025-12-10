package com.pharmacy.automation.controller;

import com.pharmacy.automation.model.CosmosUserDocument;
import com.pharmacy.automation.model.OrderSummary;
import com.pharmacy.automation.model.User;
import com.pharmacy.automation.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderSummary>> getOrders(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOrdersForUser(id));
    }

    @GetMapping("/{id}/cosmos")
    public ResponseEntity<CosmosUserDocument> getCosmosUser(@PathVariable String id) {
        return userService.findCosmosUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
