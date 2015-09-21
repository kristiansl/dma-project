Feature: Disney account sign in
  As an existing user of DMA who is signed out
  I want to be presented with a login modal
  So that I can access my DMA account

   @qa @staging @preview
  Scenario: Guest signs in with their Disney account
    Given a guest has not signed in
    When a guest signs in with their Disney account credentials
    Then the featured movies page shall be displayed with access to the guest's settings
