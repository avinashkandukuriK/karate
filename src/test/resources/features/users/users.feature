Feature: User API and data validation

  Background:
    * url baseUrl

  Scenario: Validate user API response matches database and reporting counts
    Given url userSteps.urlForUser(1)
    When method get
    Then status 200
    And match response.email == 'api.user@example.com'
    * def dbUser = dbMain.findUser(1)
    * match dbUser.EMAIL == response.email
    * def orderRows = dbMain.listOrders(1)
    * match orderRows.length == 2
    * def activeCount = dbReporting.activeUserCount()
    * match activeCount == 2

  Scenario: Validate Cosmos document aligns with API data
    Given url userSteps.urlForCosmos('cosmos-user-1')
    When method get
    Then status 200
    * def cosmosDoc = cosmos.findUser('cosmos-user-1')
    * match cosmosDoc.email == 'cosmos@example.com'
    * def cosmosOrders = cosmos.queryOrders('cosmos-user-1')
    * match cosmosOrders[0].total == 99.99
