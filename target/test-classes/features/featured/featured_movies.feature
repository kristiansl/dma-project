Feature: Featured movies
  As a user
  I want to be presented with a page showing curated features and offers available on DMA
  So that I can see the latest features and offers available to me on DMA

 @qa @staging @preview
  Scenario: Display billboards for unauthenticated guest
    Given an unauthenticated guest enters the DMA site
    Then billboards shall display not less than 2 banners

#  @US2129-1
#  Scenario: An authenticated guest previews a billboard
#    Given an authenticated guest has navigated to the featured page
#    And a movie details billboard exists
#    When an authenticated guest chooses to view movie details
#    Then the movie details page shall be displayed
#
#  @US2129-2
#  Scenario: An unauthenticated guest previews a billboard
#    Given an unauthenticated guest has navigated to the featured page
#    And a movie details billboard exists
#    When an authenticated guest chooses to view movie details
#    Then the movie details page shall be displayed
#
#  @US2132
#  Scenario: An unauthenticated guest lands on Sign In billboard
#    Given an unauthenticated guest has navigated to the featured page
#    Then billboards shall display not less than 2 banners
#    Then Sign In billboard should be present

  @staging-on-hold @preview-on-hold
  Scenario: A guest signing in from billboard
    Given an unauthenticated guest has navigated to the featured page
    And the guest selects Sign In option on billboard
    Then the guest is displayed with Sign In page

#  @staging-on-hold @preview-on-hold
#  Scenario: A guest getting started from billboard
#    Given an unauthenticated guest has navigated to the featured page
#    And the guest selects Get Started option on billboard
#    Then the guest is displayed with Get Started page

  @staging
  Scenario: As a DMA guest I should be able to select Buy option on billboard
    Given an unauthenticated guest has navigated to the featured page
    And the guest selects Buy option on billboard
    Then the movie details page, with Buy option should be displayed

  @staging-on-hold @preview
  Scenario: As a DMA guest I should be able to select Movie Details option on billboard
    Given an unauthenticated guest has navigated to the featured page
    And the guest selects Movie Details option on billboard
    Then the movie details page, with Movie Details option should be displayed

  @staging
  Scenario: As a DMA guest I should be able to select Pre-Order option on billboard
    Given an unauthenticated guest has navigated to the featured page
    And the guest selects Pre-Order option on billboard
    Then the movie details page, with Pre-Order option should be displayed

#  @on-hold
#  Scenario: As a DMA guest I should be able to select Take Tour option on billboard
#    Given an unauthenticated guest has navigated to the featured page
#    And the guest selects Take Tour option on billboard
#    Then the guest is displayed with Take Tour pages




