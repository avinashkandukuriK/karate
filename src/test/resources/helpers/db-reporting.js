function(config) {
  var DbTarget = Java.type('com.pharmacy.automation.db.DbTarget');
  var service = config.dbService;

  return {
    activeUserCount: function() {
      var result = service.query(DbTarget.REPORTING, 'select count(1) as cnt from users_reporting where status = :status', { status: 'ACTIVE' });
      return result[0].CNT;
    }
  }
}
