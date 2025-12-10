function(config) {
  var PropertiesLoader = Java.type('com.pharmacy.automation.util.PropertiesLoader');

  return {
    loadProperties: function(path) {
      return PropertiesLoader.loadAsMap(path);
    },
    formatCurrency: function(value) {
      return Number(value).toFixed(2);
    },
    toJson: function(object) {
      return JSON.stringify(object);
    }
  };
}
