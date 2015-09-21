Feature: Contact Us
  As a user on the contact page
  I want to be presented with the contact form
  So that I can get in touch with a DMA Representative

  @qa @staging @preview
  Scenario: An unauthenticated guest sees contact form
    Given an unauthenticated guest has navigated to the contact page
    Then guest presented with contact us form

  @qa @staging @preview
  Scenario: An unauthenticated guest interacts with contact form
    Given an unauthenticated guest has navigated to the contact page
    And have not entered any text
    Then guest will not be able to submit form

  @qa @staging
  Scenario: An authenticated guest wants to send contact form
    Given an authenticated guest has navigated to the contact page
    And have entered contact text
    Then guest will be able to submit form

  @qa @staging @preview
  Scenario: An authenticated guest submits contact form without agreeing terms
    Given an authenticated guest has navigated to the contact page
    And submits contact us form without agreeing terms of use
    Then error message is displayed to agree terms

  @qa @staging
  Scenario: An unauthenticated guest navigates to contact form and starts typing
    Given an unauthenticated guest has navigated to the contact page
    When the guest start entering in the comment box
    Then the system should prompt to sign in