Feature: Login functionality

  Background:
    Given User launches the application login page
    
    @TC001
  Scenario: Login with valid staff credentials
    When  User enters valid staff "hyundaipro01" and "hyundaipro01"
    And User clicks Authorized Login
    Then User should navigate to staff dashboard
