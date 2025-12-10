function() {
  return {
    relationalExamples: [
      { userId: 1, email: 'api.user@example.com', expectedOrders: 2, activeCount: 2 }
    ],
    cosmosExamples: [
      { cosmosId: 'cosmos-user-1', email: 'cosmos@example.com', firstOrderTotal: 99.99 }
    ]
  };
}
