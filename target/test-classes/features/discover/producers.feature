Feature: Discover content
  As a producer on the Discover page
  I want to assure that new discover videos are showing correctly on staging env
  So that I can publish them to production environment

  @producer
  Scenario: As a DMA producer I should be able to verify latest Discover videos
    Given an unauthenticated guest has navigated to the discover page
    Then new discover videos are displayed correctly on the discover page

  @producer
  Scenario: As a DMA user I should be able to view latest Discover videos details page
    Given an unauthenticated guest has navigated to the discover page
    Then each of new discover video details page displayed as expected
