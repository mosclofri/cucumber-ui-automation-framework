Feature: Alert Dialogs
  This is feature file intent to test Alert Dialogs within ApiDemos

  Background:
    Given I am on 'Alert Dialogs' screen

  Scenario: Repeat Alarm for all days
    Given I am on Repeat Alarm screen
    When I check all days for alarm
    Then all days should be checked for alarm