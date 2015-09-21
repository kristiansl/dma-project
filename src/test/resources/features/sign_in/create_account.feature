Feature: Create Disney Account
  As a new user
  I want to create a new Disney Account on DMA
  So that I can access content available on DMA and have an option to connect my third party accounts, such as iTunes

  Background:
    Given a new user wishes to create a Disney Account

  @qa @staging @preview
  Scenario Outline: Validating date of birth of a guest to create a new disney account
    When the user selects <birth date details> with <age> years
    Then the account is created based on eligible <age>

  Examples:
    |birth date details |age|
    |eligible age       | 14|
    |exact eligible age | 13|
    |not eligible age   | 12|

  @qa @staging @preview
  Scenario Outline: Validating error messages for date of birth in account creation
    When the <birth date> is not supplied
    And the user continues to create a Disney account
    Then "The birth date selected is not a valid date" message shall be displayed

  Examples:
    | birth date       |
    | month            |
    | day              |
    | year             |
    | month, day, year |

   @qa @staging
   Scenario: Create a new Disney account
    When the required information is supplied
    And the user creates an account
    Then message: Your account has been created! will be displayed



