@Integration
Feature: autoClearedValue test

  Scenario: clears when fragment is replaced
    Given a fragment with an autoCleared value
    And a value set to "test value"
    When the fragment is replaced with a new instance
    And the value is accessed
    Then an exception is thrown

  Scenario: doesn't clear child fragments
    Given a fragment with an autoCleared value
    And a value set to "test value"
    When it is given a child fragment
    Then the autocleared value is not cleared

  Scenario: doesn't clear for dialog
    Given a fragment with an autoCleared value
    And a value set to "test value"
    Given a dialog fragment is shown
    When the dialog fragment is dismissed
    Then the autocleared value is not cleared

