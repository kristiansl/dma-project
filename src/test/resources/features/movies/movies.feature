Feature: Movies
  As a guest user
  I want to see a grid of tiles representing movie categories available on DMA
  So that I can select a tile and view the movies within that category.

   @prod
  Scenario: An unauthenticated guest browses movie categories
    Given an unauthenticated guest has navigated to the movies page
    Then the movies page shall be displayed

  @before  @prod
  Scenario: An unauthenticated guest browses for all movies
    Given an unauthenticated guest has navigated to the movies page
    When an unauthenticated guest views all movies
    Then a list of movies shall be displayed

  @qa @staging @preview
  Scenario: An unauthenticated guest browses a category
    Given an unauthenticated guest has navigated to the movies page
    When a guest browses each movies categories
    Then the selected category page shall be displayed

  @qa @staging @preview
  Scenario: An unauthenticated guest browse the categories on the movies page
    Given an unauthenticated guest has navigated to the movies page
    Then the movies page shall display all the usual Disney categories

  @qa @staging @preview
  Scenario: An unauthenticated guest browse the category pages with category specific titles
    Given an unauthenticated guest has navigated to the movies page
    When a guest browses each movies categories
    Then the category page shall display page title relevant to the category

  @qa @staging @preview
  Scenario: An unauthenticated guest previews movie categories
    Given an unauthenticated guest has navigated to the movies page
    When a guest hovers over movies in the main navigation
    Then after a slight delay a preview list of all movie categories will be displayed

  @qa @staging
  Scenario: An authenticated guest navigates to Movie Detail Page of any owned movie
    Given an authenticated guest has navigated to the my movies page
    When guest navigates to movie detail page of his owned movie
    Then HD icon should be displayed to notify guest that its HD version

#  TODO .........
#  Scenario: Add scenarios based on SORTING