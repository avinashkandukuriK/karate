Feature: User API and data validation

  Background:
    * url baseUrl
    * def props = utils.loadProperties('automation.properties')
    * def userData = karate.get('userData')

  @api @db
  Scenario Outline: User profile matches relational data sources for <email>
    Given path 'api', 'users', <userId>
    When method get
    Then status 200
    And match response.email == <email>
    And def userPojo = utils.deserializeUser(response)
    And match userPojo.email == <email>
    And def dbUser = dbMain.findUser(<userId>)
    And match dbUser.EMAIL == response.email
    And def orderRows = dbMain.listOrders(<userId>)
    And match orderRows == '#[<expectedOrders>]'
    And def activeCount = dbReporting.activeUserCount()
    And match activeCount == <activeCount>
    And match utils.formatCurrency(orderRows[0].TOTAL) != null

    Examples: userData.relationalExamples

  @api @cosmos
  Scenario Outline: Cosmos document stays consistent with API data for <cosmosId>
    * def usersContainer = props['cosmos.container.users']
    * def ordersContainer = props['cosmos.container.orders']
    Given path 'api', 'users', <cosmosId>, 'cosmos'
    When method get
    Then status 200
    And def cosmosDoc = cosmos.findUser(<cosmosId>)
    And match cosmosDoc.email == <email>
    And def cosmosOrders = cosmos.queryOrders(<cosmosId>)
    And match cosmosOrders[0].total == <firstOrderTotal>
    And match utils.formatCurrency(cosmosOrders[0].total) == utils.formatCurrency(<firstOrderTotal>)
    And match cosmos.findUser(<cosmosId>).tier != null

    Examples: userData.cosmosExamples
