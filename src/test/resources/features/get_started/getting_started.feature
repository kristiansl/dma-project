Feature: Getting started with Disney Movies Anywhere
  As a new user to DMA
  I want to be presented with an on boarding screen
  So that I can understand what DMA is and how it works

  @prod @preview
  Scenario: An unauthenticated guest accesses DMA for the first time
    Given an unauthenticated guest has navigated to the featured page
    When an unauthenticated guest enters the DMA site
    Then the featured movies page shall be displayed

  @prod @preview
  Scenario: An unauthenticated guest returns to DMA
    Given an unauthenticated guest has navigated to the featured page
    Then the featured movies page shall be displayed