Feature: Account Settings
  As a parent
  I want to configure DMA limited access so that I could give the app to my child

#  @qa-on-hold
#  Scenario: An authenticated guest connects dma account with Vudu
#    Given an authenticated guest wants to connect account with vudu
#    Then the guest is connected with vudu
#
#  @qa-on-hold
#  Scenario: An authenticated guest disconnects dma account with Vudu
#    Given an authenticated guest wants to disconnect account with vudu
#    Then the guest is disconnected with vudu

  @qa @staging @preview
  Scenario: Guest wants to update profile with disney account
    Given an authenticated guest wants to update profile with disney account
    When the guest updates the profile with the name
    Then the guests disney account profile is updated with the name

  @qa @staging @preview
  Scenario: An authenticated guest navigates to account history
    Given an authenticated guest navigates to account history
    Then the movies owned by the guest are shown with its history

  @qa @staging @preview
  Scenario: Guest navigates to closed captioning in account settings
    Given an authenticated guest navigates to closed captioning display
    Then closed captioning options are displayed with edit option


#  @cleanupAccess @qa @staging @preview
#  Scenario: An authenticated guest with limited access can't purchase movie
#    Given an authenticated guest has basic limited access turned on
#    When they navigate to the movie details page for the unowned title maleficent
#    Then they cannot purchase the movie
#
#  @cleanupAccess @qa @staging @preview
#  Scenario: An authenticated guest with limited access can't share movie
#    Given an authenticated guest has basic limited access turned on
#    When they navigate to the movie details page for the unowned title maleficent
#    Then they cannot share the movie
#
#  @cleanupAccess @qa @staging
#  Scenario: An authenticated guest with limited access can't play a movie
#    Given an authenticated guest has basic limited access turned on
#    When they navigate to the movie details page for restricted title alice-in-wonderland-2010
#    Then the play button is restricted behind a password prompt
#
#  @cleanupAccess @qa @staging
#  Scenario: An authenticated guest with limited access can't purchase movie
#    Given an authenticated guest has basic limited access turned on
#    When they navigate to the movie details page for the unowned title maleficent
#    Then they cannot purchase the movie
#
#  @cleanupAccess @qa @staging
#  Scenario: An authenticated guest with limited access can't share movie
#    Given an authenticated guest has basic limited access turned on
#    When they navigate to the movie details page for the unowned title maleficent
#    Then they cannot share the movie
#
#
#  @cleanupAccess @qa @staging
#  Scenario: An authenticated guest with limited access can't play a movie
#    Given an authenticated guest has basic limited access turned on
#    When they navigate to the movie details page for restricted title alice-in-wonderland-2010
#    Then the play button is restricted behind a password prompt
