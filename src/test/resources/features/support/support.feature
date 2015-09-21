Feature: Support content
  As a user on the support page
  I want to be presented with the support page
  So that I can see a curated questions and various help topics.

  @qa @staging @preview
  Scenario: An unauthenticated guest browses support page
    Given an unauthenticated guest has navigated to the support page
    Then guest will be presented 5 or more curated questions

  @qa @staging @preview
  Scenario: An unauthenticated guest sees help topics
    Given an unauthenticated guest has navigated to the support page
    Then guest will see help topic links

  @qa @staging @preview
  Scenario: An unauthenticated guest navigates to KeyChest in footer of dma home page
    Given an unauthenticated guest navigates to KeyChest logo in the footer
    Then guest is displayed with support page FAQ's