Feature: Alert Dialogs
  This feature file intent to test Alert Dialogs within ApiDemos

  Background:
    Given I am on 'Alert Dialogs' screen

  Scenario: Repeat Alarm for all days
    Given I am on Repeat Alarm screen
    When I check all days for alarm
    Then all days should be checked for alarm

  Scenario: Repeat Alarm for only week days
    Given I am on Repeat Alarm screen
    When I check all week days for alarm
    Then week days should be checked for alarm