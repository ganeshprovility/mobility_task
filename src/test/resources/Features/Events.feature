Feature: End to End API test for All events
  Description: The purpose of these test to cover End to End flow for Events

  Scenario: TC_1 Verify user can able to retrieve the existing events
    Given We get the event details based on id,page & size
    Then We should get the status Code 200
    And I print the response

  Scenario: TC_2 Verify user can able to create a new event
    Given Admin provides details to create an event
    Then We should get the status Code 200
    And Validate "providerId" value present in "id" field





