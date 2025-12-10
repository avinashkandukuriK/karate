function(config) {
  var service = config.cosmosDbService;

  return {
    findUser: function(id) {
      var result = service.findById('users', id);
      return result.isPresent() ? result.get() : null;
    },
    queryOrders: function(userId) {
      var query = 'SELECT * FROM c WHERE c.userId = @userId';
      return service.query('orders', query, { userId: userId });
    }
  }
}
