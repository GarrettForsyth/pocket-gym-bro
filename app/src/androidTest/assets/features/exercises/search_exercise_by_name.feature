@NoAsyncArch
Feature: Search an exercise

  As an user
  I want to look up an exercise
  By searching for by name

  Background: Open App
    Given I have just opened the app

  @feature
  Scenario: Successful searches
    And I search "press" in the "search edit text"
    Then I should not see "no results found"
    And I should see "shoulder press" within the "result recyclerview"
    And I should see "decline barbell press" within the "result recyclerview"
    And I should see "bench press" within the "result recyclerview"
    And I should not see "dead lift" within the "result recyclerview"

  @feature
  Scenario: no results
    And I search "no matches for this" in the "search edit text"
    Then I should see "no results found"

  @NetworkFailureFeature
  Scenario: network fails
    And I search "some exercise" in the "search edit text"
    Then a snackbar is displayed with text "Internet unavailable."

  @NetworkErrorFeature
  Scenario: network error occurs
    And I search "some exercise" in the "search edit text"
    Then a snackbar is displayed with text "A network error has occurred."
