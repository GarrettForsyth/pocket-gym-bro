Feature: Interactions with ExerciseSearchSuggestion

  @NoAsyncArch
  Scenario: Insert an ExerciseSearchSuggestion into the database
    Given an ExerciseSearchSuggestion
    When I insert the ExerciseSearchSuggestion into the database
    Then no errors occur

  @NoAsyncArch
  Scenario: Insert a list of  ExerciseSearchSuggestions
    Given a list of ExerciseSearchSuggestions
    When I insert the list of ExerciseSearchSuggestions into the database
    Then no errors occur

  @NoAsyncArch
  Scenario Outline: Querying ExerciseSearchSuggestions by name
    Given a list of ExerciseSearchSuggestions
    When I insert the list of ExerciseSearchSuggestions into the database
    And I query "<exercise name>" from ExerciseSearchSuggestions
    Then I get a result with <number> of entries

    Examples:
      | exercise name     | number |
      | %test exercise 1% | 1      |
      | %test exercise%   | 5      |
      | %aklfja%          | 0      |

  @NoAsyncArch
  Scenario: Inserting same id replaced old value
    Given an ExerciseSearchSuggestion
    When I insert the ExerciseSearchSuggestion into the database
    And insert another ExerciseSearchSuggestion with the same id and different values
    Then the new old ExerciseSearchSuggestion is replaced with the new one
