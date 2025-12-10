function(config) {
  return {
    urlForUser: function(id) {
      return config.baseUrl + '/api/users/' + id;
    },
    urlForCosmos: function(id) {
      return config.baseUrl + '/api/users/' + id + '/cosmos';
    }
  }
}
