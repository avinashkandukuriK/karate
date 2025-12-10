function(config) {
  var DbTarget = Java.type('com.pharmacy.automation.db.DbTarget');
  var service = config.dbService;

  return {
    findUser: function(id) {
      var sql = 'select * from users where id = :id';
      return service.query(DbTarget.MAIN, sql, { id: id })[0];
    },
    listOrders: function(userId) {
      return service.query(DbTarget.MAIN, 'select * from orders where user_id = :id', { id: userId });
    }
  }
}
