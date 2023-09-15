Feature: End to End API test for All events
  Description: The purpose of these test to cover End to End flow for Events

  Scenario: TC_1 Verify user can able to retrieve the existing events
    Given Get the event details based on id,page & size
    Then Response status code should  be 200
    And Print the response

  Scenario: TC_2 Verify user can able to create a new event
    Given Admin should provides details to create an event
    Then Response status code should  be 200
    And Response should contain id field




