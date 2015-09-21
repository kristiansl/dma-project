Feature: Discover content
  As a user on the Discover page
  I want to see a collection of Disney Content tiles
  So that I can discover more content about my favorite movies or movies that are coming to theatres

  @prod
  Scenario: An unauthenticated guest views the Discover page
    Given an unauthenticated guest has navigated to the discover page
    Then the discover page shall be displayed

  @prod @qa @staging
  Scenario: An unauthenticated guest navigates to the Discover page
    Given an unauthenticated guest has navigated to the discover page
    Then the Discover page shall be displayed with video thumbnails, titles, play and info buttons

  @qa @staging @preview
  Scenario: An authenticated guest navigates to the Discover page
    Given an authenticated guest has navigated to the discover page
    Then the Discover page shall be displayed with video thumbnails, titles, play and info buttons

  @qa @staging @preview
   Scenario: An authenticated guest navigates to the Discover page and selects video
    Given an authenticated guest has navigated to the discover page
    Then the discover page shall be displayed
    Then guest selects random video
    Then popup window will be displayed video thumbnail with expected options

  @qa @staging @preview
  Scenario: An unauthenticated guest trying to add video to favorites
    Given an unauthenticated guest has navigated to the discover page
    #Then guest selects random video
    And guest trying to add video to favorites
    #Then guest presented with login and closes it
    Then video not found in favorites


    # on-hold - to be fixed
#  @qa @staging @preview
#  Scenario: An unauthenticated guest adds video to favorites
#    Given an unauthenticated guest has navigated to the discover page
#    Then guest selects random video
#    And guest trying to add video to favorites
#    Then guest presented with login lightbox and signs in
#   # Then guest selects the video which he wants to add to favorites
#    And guest adds video to favorites
#    Then popup window will be displayed video thumbnail with expected options
#    Then video appears under Favorites Videos section on MyCollection page


  @qa @staging @preview
  Scenario: An authenticated guest adds video to favorites
    Given an authenticated guest has navigated to the discover page
    When guest adds a random video to favorites
    Then video appears under Favorites Videos section on MyCollection page

  @qa @staging @preview
  Scenario: An authenticated guest wants to the view discover video
    Given an unauthenticated guest has navigated to the discover page
    Then message: Please sign in to is displayed

  @qa @staging @preview
  Scenario: A guest guest views the Discover page
    Given a guest has navigated to the discover page
    Then background and video thumbnails are displayed

#  @staging-on-hold @preview
#  Scenario: An authenticated guest removes video from favorites
#    Given an authenticated guest has navigated to the my collection page
#    Then guest removes a favorite video

#  @on-hold
#  Scenario: An authenticated guest wants to share a video
#    Given an authenticated guest with no movies has navigated to the discover page
#    When guest selects random video to share
#    Then the share options are displayed to guest

#  @qa @staging
#  Scenario: As a DMA user I should be able to view latest Discover videos
#    Given an authenticated guest has navigated to the discover page
#    When the guest selects latest discover video
#    Then the  video details page should be displayed as expected

