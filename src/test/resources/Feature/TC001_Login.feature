Feature: Login

  Background:
    Given User launches the application login page

  @TC01
  Scenario: Login page loads
    Then the login page elements are displayed

  @TC02
  Scenario: Valid staff login
    When User enters valid staff credentials "hospvalue01" and "testlogin001"
    And User clicks Authorized Login
    Then User should navigate to staff dashboard "HOSPVALUE01"

  @TC03
  Scenario: Valid patient login
    When User selects Patient Portal
    And User enters valid patient credentials "patientUser" and "patientPass"
    And User clicks Patient Login
    Then User should navigate to patient dashboard

  @TC04
  Scenario: Invalid password
    When User enters a valid username "hospvalue01" and an incorrect password "WrongPassword@123"
    And User clicks the login button
    Then User should see a generic login error
    And User should remain on the login page

  @TC05
  Scenario: Invalid username
    When User enters an unknown username "unknownuser999" and an incorrect password "WrongPassword@123"
    And User clicks the login button
    Then User should see the same generic login error
    And User should remain on the login page

  @TC06
  Scenario: Submit with both fields empty
    When User clicks the login button without entering credentials
    Then required-field validation should appear for both fields "Please enter your username" and "Please enter your password"

  @TC07
  Scenario: Submit with one field empty
    When User enters only a username "hospvalue01" and clicks the login button
    Then required-field validation should appear for the password field "Please enter your password"

  @TC08
  Scenario: Show and hide password
    When User enters a password "SecretPass@123"
    And User clicks the password visibility icon
    Then the password should be visible
    When User clicks the password visibility icon again
    Then the password should be hidden

  @TC09
  Scenario: Switch between login tabs
    When User switches between Staff / Provider and Patient Portal
    Then the correct login form should be displayed for each tab "Patient ID / Username" and "Staff Username"

  @TC10
  Scenario: Submit with only password entered
    When User enters only a password "testlogin001" and clicks the login button
    Then required-field validation should appear for the username field "Please enter your username"

  @TC11
  Scenario: Submit with whitespace-only credentials
    When User enters valid staff credentials "   " and "   "
    And User clicks the login button
    Then required-field validation should appear for both fields "Please enter your username" and "Please enter your password"

  @TC12
  Scenario: Toggle Remember device checkbox
    Then the Remember device checkbox should be unchecked by default
    When User clicks the Remember device checkbox
    Then the Remember device checkbox should be checked
    When User clicks the Remember device checkbox again
    Then the Remember device checkbox should be unchecked

  @TC13
  Scenario: Navigate to Forgot Password page
    When User clicks the Forgot Password link
    Then User should navigate to the page containing "/forgot-password"

  @TC14
  Scenario: Navigate to Initialize Account page
    When User clicks the Initialize Account link
    Then User should navigate to the page containing "registerdotLab.jsp"

  @TC15
  Scenario: Dismiss error alert banner
    When User clicks the login button without entering credentials
    And User closes the error alert banner
    Then the error alert banner should not be displayed

  @TC16
  Scenario: Switching tabs clears validation errors
    When User clicks the login button without entering credentials
    And User selects Patient Portal
    Then validation errors should be cleared

  @TC17
  Scenario: Submit with both fields empty on Patient Portal
    When User selects Patient Portal
    And User clicks the login button without entering credentials
    Then required-field validation should appear for both fields "Please enter your username" and "Please enter your password"

  @TC18
  Scenario: Show and hide password on Patient Portal
    When User selects Patient Portal
    And User enters a password "SecretPass@123"
    And User clicks the password visibility icon
    Then the password should be visible
    When User clicks the password visibility icon again
    Then the password should be hidden

  @TC19
  Scenario: Submit login using the Enter key
    When User enters valid staff credentials "hospvalue01" and "testlogin001" and presses Enter
    Then User should navigate to staff dashboard "HOSPVALUE01"
