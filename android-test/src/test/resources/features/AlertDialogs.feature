Feature: Alert Dialogs
  This is feature file intent to test Alert Dialogs within ApiDemos

  Background:
    When I click on 'App'
    And I click on 'Alert Dialogs'
    Then "Repeat alarm" should be displayed

  @wip
  Scenario: Repeat Alarm
    When I click on 'Repeat alarm'
    Then "id#alertTitle" should be displayed

    When I click on all unselected 'id#text1'
    And I click on 'id#button1'
    And I click on 'Repeat alarm'
    Then all 'id#text1' should be selected

  @wip
  Scenario: Single Choice List
    When I click on 'Single choice list'
    Then "id#alertTitle" should be displayed

    When I click on 'name#Traffic'
    And I click on 'id#button1'

    When I click on 'Single choice list'
    Then 'name#Traffic' should be selected