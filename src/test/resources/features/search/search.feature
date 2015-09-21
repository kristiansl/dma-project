Feature: Search
  As a user of DMA
  I want to search for movie titles
  So that I can find a movie and purchase.

  @qa @staging @preview
  Scenario: An unauthenticated guest searches for movie title
    Given an unauthenticated guest has navigated to the search page
    And searches for the movie title cars
    Then the search results shall display the cars titles

  @qa @staging @preview
  Scenario: An unauthenticated guest views search page
    Given an unauthenticated guest has navigated to the search page
    Then the search page shall be displayed

  @qa @staging @preview
  Scenario: An unauthenticated guest utilizes search
    Given an unauthenticated guest has navigated to the search page
    And have typed minimum of TO characters for search
    Then the search results shall be displayed

  @qa @staging @preview
  Scenario: An authenticated guest utilizes search
    Given an authenticated guest has navigated to the search page
    And have typed minimum of TO characters for search
    Then the search results shall be displayed

#  @qa @staging @preview
#  Scenario: An authenticated guest cancels search
#    Given an authenticated guest has navigated to the search page
#    When the search is cancelled
#    Then the search page shall not be displayed

  @qa @staging @preview
  Scenario: An unauthenticated guest cancels search
    Given an unauthenticated guest has navigated to the search page
    When the search is cancelled
    Then the search page shall not be displayed