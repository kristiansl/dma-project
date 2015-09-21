Feature: Rewards
  As a guest user
  I want to see the rewards page with  default points on it

   @prod
  Scenario: An unauthenticated guest views Disney Movie Rewards
    Given an unauthenticated guest has navigated to the rewards page
    Then the rewards page shall be displayed
    And the unauthenticated guest shall have 00000 movie reward points

  @qa @staging @preview
  Scenario: An authenticated guest views Disney Movie Rewards
    Given an authenticated guest has navigated to the rewards page
    Then the rewards page shall be displayed
    Then the authenticated guest shall have movie reward points

  @qa @staging @preview
  Scenario: An unauthenticated guest views Disney Movie Rewards page and applies a code
    Given an unauthenticated guest has navigated to the rewards page
    Then the rewards page shall be displayed
    And guest entered a code: 'abcd' to redeem
    Then guest presented with login lightbox and signs in

  @preview @preview
  Scenario: An authenticated guest views Disney Movie Rewards and signs in to DMR page
    Given an authenticated guest has navigated to the rewards page
    And guest chooses to Get Rewards
    Then guest redirected and auto signs in to DMR page

  @qa @staging @preview
  Scenario: An authenticated guest views Disney Movie Rewards page
    Given an authenticated guest has navigated to the rewards page
    Then the rewards page shall be properly displayed

  @qa @staging
  Scenario: Guest creates new account and enters rewards code
    Given a guest creates a new account and enters rewards code
    Then the guest gets the movie and reward points through Disney Movie Rewards

  @qa @staging
  Scenario: An existing guest enters rewards code
    Given an existing guest enters rewards code
    Then duplicate code message is displayed

  @qa @staging
  Scenario: Guest enters invalid rewards code
    Given an existing guest enters invalid rewards code
    Then error message is displayed for invalid rewards code