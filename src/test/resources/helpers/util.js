function(config) {
  var PropertiesLoader = Java.type('com.pharmacy.automation.util.PropertiesLoader');
  var SerializationUtils = Java.type('com.pharmacy.automation.util.SerializationUtils');
  var User = Java.type('com.pharmacy.automation.model.User');

  return {
    loadProperties: function(path) {
      return PropertiesLoader.loadAsMap(path);
    },
    formatCurrency: function(value) {
      return Number(value).toFixed(2);
    },
    toJson: function(object) {
      return JSON.stringify(object);
    },
    deserializeUser: function(payload) {
      var json = typeof payload === 'string' ? payload : karate.toJson(payload);
      return SerializationUtils.fromJson(json, User.class);
    }
  };
}
