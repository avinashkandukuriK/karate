function(config) {
  var core = karate.read('classpath:helpers/cosmos-core.js')(config);

  return {
    getTier: function(id) {
      var user = core.findUser(id);
      if (!user) return null;
      return user.tier;
    }
  }
}
