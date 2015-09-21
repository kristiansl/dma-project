Feature: Movie Details
  As a user
  I want to see a movie details page for a specific movie
  So that I can view all of the details for a specific movie
#
# @staging @preview
#  Scenario: An authenticated guest adds movie to favorites
#    Given an authenticated guest has navigated to the featured page
#    And movies slider is displayed
#    When guest adds movie to favorites
#    Then selected movie shall be displayed in their collection
#
#  @staging
#  Scenario: An authenticated guest removes movie from favorites
#    Given an authenticated guest has navigated to the my collection page
#    And the guest has a list of favorite movies
#    When the guest removes a favorite movie
#    Then the selected movie shall not be displayed in their collection
#
#  @staging @preview
#  Scenario: An unauthenticated guest tries to add movie to favorites
#    Given an unauthenticated guest enters the DMA site
#    And guest wants to add movie to favorites
#    Then guest presented with login lightbox and signs in
#    Then selected movie shall be displayed in their collection

#
#  Scenario: An authenticated guest can preview or buy a movie they do not own, which is not in the vault
#    Given an authenticated guest browses a category
#    And the guest has found a movie they do not own
#    And the movie is not in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to buy the movie
#    And shall be able to preview the movie
#
#  Scenario: An unauthenticated guest can preview or buy a movie they do not own, which is not in the vault
#    Given an unauthenticated guest browses a category
#    And the guest has found a movie they do not own
#    And the movie is not in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to buy the movie
#    And shall be able to preview the movie
#
#  Scenario: An authenticated guest can preview a movie they own, which is not in the vault
#    Given an authenticated guest browses a category
#    And the guest has found a movie they own
#    And the movie is not in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to preview the movie
#
#  Scenario: An unauthenticated guest can preview a movie they own, which is not in the vault
#    Given an unauthenticated guest browses a category
#    And the guest has found a movie they own
#    And the movie is not in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to preview the movie
#
#  Scenario: An authenticated guest can preview a movie they own, which is in the vault
#    Given an authenticated guest browses a category
#    And the guest has found a movie they own
#    And the movie is in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to preview the movie
#
#  Scenario: An unauthenticated guest can preview a movie they own, which is in the vault
#    Given an unauthenticated guest browses a category
#    And the guest has found a movie they own
#    And the movie is in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to preview the movie
#
#  Scenario: An authenticated guest can preview a movie they do not own, which is in the vault
#    Given an authenticated guest browses a category
#    And the guest has found a movie they do not own
#    And the movie is in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to preview the movie
#    But shall not be able to buy the movie
#
#  Scenario: An unauthenticated guest can preview a movie they do not own, which is in the vault
#    Given an unauthenticated guest browses a category
#    And the guest has found a movie they do not own
#    And the movie is in the vault
#    When the guest views the details of the movie
#    Then the guest shall be able to preview the movie
#    But shall not be able to buy the movie
#
#  Scenario: An authenticated guest can watch a blue ribbon movie
#    Given an authenticated guest browses a category
#    When the guest views a movie with a blue ribbon
#    Then the guest shall be able to watch the movie
#
#  Scenario: An authenticated guest sees "more like this" feature films
#    Given an authenticated guest has navigated to the movie details page
#    Then the more like this section shall be displayed
#
#  Scenario: An unauthenticated guest sees "more like this" feature films
#    Given an unauthenticated guest has navigated to the movie details page
#    Then the more like this section shall be displayed with one or more movies
#
#  Scenario: Vaulted content that is not owned by the authenticated guest is not shown in "more like this"
#    Given an authenticated guest has navigated to the movie details page
#    And vaulted content is not owned by the guest
#    Then the more like this section shall not display vaulted content
#
#  Scenario: Vaulted content that is owned by the authenticated guest is shown in "more like this"
#    Given an authenticated guest has navigated to the movie details page
#    And vaulted content is owned by the guest
#    Then the more like this section shall display vaulted content
#
#  Scenario: Vaulted content is not shown in "more like this" for an unauthenticated guest
#    Given an unauthenticated guest has navigated to the movie details page
#    Then the more like this section shall not display vaulted content
#
#  Scenario: Owned and not owned movies are shown in "more like this" for an authenticated guest
#    Given an authenticated guest has navigated to the movie details page
#    Then the content may contain a mix of titles that are owned and not owned
#
#
