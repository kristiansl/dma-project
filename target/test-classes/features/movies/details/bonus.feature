Feature: Movie Details Bonus
  As a user
  I want to see the bonus listing on movie detail page
  So that I can enjoy watching the bonus content for a specific movie

  @qa @staging @preview
  Scenario: Bonus - Not logged in
    Given an unauthenticated guest has navigated to the featured page
    When an unauthenticated guest chooses to view a movie with bonus content (monsters-inc)
    Then there will be a listing of the bonus content available for that title, up to 20 items

  @qa @staging @preview
  Scenario: Bonus - Logged in on Movie not owned
    Given an authenticated guest has navigated to the featured page
    When an authenticated guest chooses to view a movie they do not own with bonus content (maleficent)
    Then there will be a listing of the bonus content available for that title, up to 20 items

  @qa @staging
  Scenario: An authenticated guest can view the thumbnails of bonus content for movie that they own
    Given an authenticated guest has navigated to the featured page
    When an authenticated guest chooses to view a movie they own with bonus content (toy-story-2)
    Then the bonus features shall be grouped in categories
    And clickable thumbnail images shall be shown for each bonus item in the bonus categories

  @qa @staging
  Scenario: An authenticated guest can view the title of the bonus content for the movie they own
    Given an authenticated guest has navigated to the featured page
    When an authenticated guest chooses to view a movie they own with bonus content (cars)
    Then guest should be able to see the title of the bonus video within in each bonus category