Feature: My Collection
  As a user of DMA
  I want to see a grid of tiles representing the entitlements in my collection
  So that I can select a tile and view an entitlement

  @qa @prod @preview
  Scenario: An unauthenticated guest accesses movies section in my collection on DMA
    Given an unauthenticated guest has navigated to the my collection page
    Then the my collection page shall be displayed

  @qa @staging @preview
  Scenario: An authenticated guest with no movies accesses My Collection
    Given an authenticated guest with no movies has navigated to the my collection page
    Then the "Connect Account" option shall be displayed

  @qa @staging @testing
  Scenario: An authenticated guest views my-collection page and "Favorites" ribbon appears
    Given an authenticated guest has navigated to the my favorite movies page
    And a list of movies shall appear with "Favorites" ribbon
    Then each favorite movie thumbnail image appears with proper flag color

  @qa @staging
  Scenario: An authenticated guest sees sort options for Favorites movies & videos
    Given an authenticated guest has navigated to the my favorite movies page
    Then favorites sort options shall be displayed

  @qa @staging
  Scenario: An authenticated guest views their collection
    Given an authenticated guest has navigated to the my movies page
    Then the my collection page shall be displayed with a list of owned movies

  @qa @staging
  Scenario: An authenticated guest's owned  movies are flagged
    Given an authenticated guest has navigated to the my movies page
    Then each owned movie image appears with proper flag color

  @qa @staging
  Scenario: An authenticated guest views my movies page and presented with sorting option
    Given an authenticated guest has navigated to the my movies page
    And guest own more than one movie
    Then movies sorting option shall be displayed

  @qa @staging
  Scenario: An authenticated guest selects a movie and see the movie details
    Given an authenticated guest has navigated to the my movies page
    Then the my collection page shall be displayed with a list of owned movies
    Then guest selects movie title and views movie details

  @qa @staging
  Scenario: An authenticated guest presented with movies collection sort options
    Given an authenticated guest has navigated to the my movies page
    And sorting is available on the my movies page
    Then sort options shall be displayed

  @qa @staging
  Scenario: An authenticated guest views my-collection page and "My Collection" ribbon appears
    Given an authenticated guest has navigated to the my movies page
    Then movies owned by the guest should appear with "My Collection" ribbon

  @qa @staging
  Scenario: An authenticated guest views my-collection page and movies sorted with "Date Added" option
    Given an authenticated guest has navigated to the my movies page
    Then default sort option should be Date Added

 #  @on-hold @staging @preview
 # Scenario: An authenticated guest can favorite owned movies
 #   Given an authenticated guest has navigated to the my collection page
  #  Then the my collection page shall be displayed with a list of owned movies
 #   And guest adds owned movie to favorites
 #   Then selected movie shall be displayed in their collection


  @qa @staging
  Scenario: An authenticated guest should be able to play vaulted movie, which he owned
    Given a guest owned a currently vaulted movie bambi-2
    Then guest should be able to play the movie


#  TODO The following scenarios were supplied by DMA's product owner.  BEFORE implementing these scenarios, QA must review to determine if the steps fit within the automation framework.
#
#  Scenario: Display My Collection Page - Movies
#    Given an authenticated user accesses movie section in my collection on DMA
#    When an authenticated user guest has navigated to the my collections page - Movies
#    Then the my collection page will display movie tiles the user owns
#    And a sync my collection selection will display
#    And sort selection will display
#
#  Scenario: Display My Collection Page - Favorites
#    Given an authenticated user accesses the Favorites section in my collection on DMA
#    When an authenticated user guest has navigated to the my collections page - Favorites
#    Then the my collection page will display movie tiles the user has favorited
#    And a "Movies / Favorites" navigation selection will display
#
#  Scenario: Select Movie Tile
#    Given an authenticated user accesses the movies section in my collection on DMA
#    When the user selects a movie tile
#    Then the movie details page for a given movie will display
#
#  Scenario: Select Favorite Movie Tile
#    Given an authenticated user accesses the Favorites section in my collection on DMA
#    When the user selects a favorited movie tile
#    Then the movie details page for a given movie will display

