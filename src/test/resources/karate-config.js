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

  config.dbMain = karate.read('classpath:helpers/db-main.js')(config);
  config.dbReporting = karate.read('classpath:helpers/db-reporting.js')(config);
  config.cosmos = karate.read('classpath:helpers/cosmos-core.js')(config);
  config.userSteps = karate.read('classpath:helpers/user-steps.js')(config);

  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}
