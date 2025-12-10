function karateConfig() {
  var env = karate.env || 'test';
  var SpringContextHolder = Java.type('com.pharmacy.automation.test.support.SpringContextHolder');
  var ctx = SpringContextHolder.getContext();
  var port = java.lang.System.getProperty('karate.server.port');

  var config = {
    env: env,
    baseUrl: 'http://localhost:' + port,
    dbService: ctx.getBean(Java.type('com.pharmacy.automation.db.DbService')),
    cosmosDbService: ctx.getBean(Java.type('com.pharmacy.automation.cosmos.CosmosDbService'))
  };

  var userDataProvider = karate.read('classpath:helpers/users-data.js');
  config.userData = typeof userDataProvider === 'function' ? userDataProvider() : userDataProvider;

  config.dbMain = karate.read('classpath:helpers/db-main.js')(config);
  config.dbReporting = karate.read('classpath:helpers/db-reporting.js')(config);
  config.cosmos = karate.read('classpath:helpers/cosmos-core.js')(config);
  config.userSteps = karate.read('classpath:helpers/user-steps.js')(config);
  config.utils = karate.read('classpath:helpers/util.js')(config);
  config.properties = config.utils.loadProperties('automation.properties');
  config.cosmosContainers = {
    users: config.properties['cosmos.container.users'] || 'users',
    orders: config.properties['cosmos.container.orders'] || 'orders'
  };

  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}
