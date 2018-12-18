Feature: SearchFragment Integration Test

  Background:
    Given a search fragment

  Scenario: search
    Given the "progress bar" is not displayed
    And "no results found" is not displayed
    When I search "dead lift" in "search edit text"
    Then it should query the viewmodel for "dead lift"
    And the "progress bar" is displayed

  Scenario: load results
    When a result "dead lift" is found
    Then I should see "dead lift" within the "result recyclerview"

  Scenario: network failure
    When a network failure occurs
    Then a snackbar is displayed with text "Internet unavailable."
    Then a snackbar action is displayed with text "Retry"

  Scenario: network failure retry to connect
    When I search "dead lift" in "search edit text"
    And a network failure occurs
    And the snackbar action is clicked
    Then it should query the viewmodel for "dead lift"


