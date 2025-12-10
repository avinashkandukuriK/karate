function(config) {
  var service = config.cosmosDbService;
  var usersContainer = (config.cosmosContainers && config.cosmosContainers.users) || 'users';
  var ordersContainer = (config.cosmosContainers && config.cosmosContainers.orders) || 'orders';

  return {
    findUser: function(id) {
      var result = service.findById(usersContainer, id);
      return result.isPresent() ? result.get() : null;
    },
    queryOrders: function(userId) {
      var query = 'SELECT * FROM c WHERE c.userId = @userId';
      return service.query(ordersContainer, query, { userId: userId });
    }
  }
}
