@PatientRegistration
Feature: Patient Registration
  As an authorized staff user
  I want to search, select, and register patients
  So that patient records are accurately maintained in the registry

  Background:
    Given User launches the application login page
    When User enters valid staff credentials "hospvalue01" and "testlogin001"
    And User clicks Authorized Login
    Then User should navigate to staff dashboard "HOSPVALUE01"

  @PR001
  Scenario: Search Patient page loads with all elements
    When User navigates to Patient Registration page
    Then all Search Patient page elements should be displayed

  @PR002
  Scenario: Validate empty search warning on Search Patient page
    When User navigates to Patient Registration page
    And User clicks Search on Search Patient page
    Then Search Patient warning message "Enter Value for atleast one Column" should be displayed

  @PR003
  Scenario: Validate invalid NID format on Search Patient page
    When User navigates to Patient Registration page
    And User enters search NID "12345"
    And User clicks Search on Search Patient page
    Then Search Patient field validation error should be displayed

  @PR004
  Scenario: Validate invalid Phone format on Search Patient page
    When User navigates to Patient Registration page
    And User enters search Phone "123"
    And User clicks Search on Search Patient page
    Then Search Patient field validation error should be displayed

  @PR005
  Scenario: Clear button resets Search Patient fields
    When User navigates to Patient Registration page
    And User enters Search Patient criteria "1001", "Test", "Patient", and "testuser"
    And User clicks Clear on Search Patient page
    Then all Search Patient fields should be cleared

  @PR006
  Scenario: Sports Type and Club / Team Name cascading dropdowns
    When User navigates to Patient Registration page
    And User selects Sports Type "Basketball" on Search Patient page
    And User selects Club / Team Name "Basketball Team One" on Search Patient page
    Then Sports Type "Basketball" and Club / Team Name "Basketball Team One" should be selected

  @PR007
  Scenario: Search for a non-existent patient record
    When User navigates to Patient Registration page
    And User enters search First Name "NonExistentPatient987654"
    And User clicks Search on Search Patient page
    Then Search Patient no records message "No patient records matched the search criteria." should be displayed

  @PR008
  Scenario: Go Back button navigates away from Search Patient page
    When User navigates to Patient Registration page
    And User clicks Go Back on Search Patient page
    Then User should leave the Search Patient page

  @PR009
  Scenario: New Patient Registration page loads with default values
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    Then all New Patient Registration form elements should be displayed with default selections

  @PR010
  Scenario: Validate mandatory fields on New Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User clicks Submit on Patient Registration form
    Then mandatory validation messages should appear on Patient Registration form

  @PR011
  Scenario: Date of Birth auto-calculates Age on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Date of Birth "15/05/1995"
    Then Patient Age field should be disabled and auto-calculated

  @PR012
  Scenario: Spaces are not allowed in Patient Username and Password
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Username "test user" and Password "pass word"
    And User clicks Submit on Patient Registration form
    Then Patient Registration validation messages "Username cannot contain spaces" and "Password cannot contain spaces" should be displayed

  @PR013
  Scenario: Validate invalid NID and Cell Phone format on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient NID "12345" and Cell Phone "12345"
    And User clicks Submit on Patient Registration form
    Then Patient Registration NID and Cell Phone validation errors should be displayed

  @PR014
  Scenario: Clear button resets New Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient basic details "Ahmed", "Hassan", "ahmedpat01", and "ahmed@example.com"
    And User clicks Clear on Patient Registration form
    Then New Patient Registration fields should be cleared

  @PR015
  Scenario: Cancel button navigates away from Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User clicks Cancel on Patient Registration form
    Then User should navigate to Patient Search page "/patient/search"

  @PR016
  Scenario: Register a new patient with valid mandatory details
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User fills valid mandatory Patient Registration details:
      | firstName | AutoTest            |
      | lastName  | Patient             |
      | dob       | 15/05/1995          |
      | cellPhone | 1012345678          |
      | password  | TestPass123         |
      | email     | autopat@example.com |
    And User selects first available Location and Physician on Patient Registration form
    And User clicks Submit on Patient Registration form
    Then Patient should be registered successfully with a generated Patient ID

  @PR017
  Scenario: Select Patient page loads with all search criteria and defaults
    When User navigates to Select Patient page
    Then all Select Patient page elements should be displayed with default selections

  @PR018
  Scenario: Validate invalid National ID format on Select Patient page
    When User navigates to Select Patient page
    And User enters Select Patient National ID "12345"
    And User clicks Search on Select Patient page
    Then Search Patient field validation error should be displayed

  @PR019
  Scenario: Clear button resets Select Patient search criteria
    When User navigates to Select Patient page
    And User enters Select Patient criteria "1001", "Ahmed", "Ali", "ahmed01", and "MRN100"
    And User clicks Clear on Select Patient page
    Then all Select Patient fields should be cleared

  @PR020
  Scenario: Order By dropdown selects sort options on Select Patient page
    When User navigates to Select Patient page
    And User selects Order By "First Name" on Select Patient page
    Then Order By "First Name" should be selected on Select Patient page
    When User selects Order By "Patient ID" on Select Patient page
    Then Order By "Patient ID" should be selected on Select Patient page

  @PR021
  Scenario: Sports Type enables and populates Club / Team Name on Select Patient page
    When User navigates to Select Patient page
    Then Club / Team Name dropdown should be disabled by default on Select Patient page
    When User selects Sports Type "Basketball" on Select Patient page
    And User selects Club / Team Name "Basketball Team One" on Select Patient page
    Then Select Patient Sports Type "Basketball" and Club / Team Name "Basketball Team One" should be selected

  @PR022
  Scenario: Toggle Select By radio buttons on Select Patient page
    When User navigates to Select Patient page
    Then Select By "Regular Search" should be selected by default
    When User selects Select By "Statement" on Select Patient page
    Then Select By "Statement" should be selected
    When User selects Select By "Regular Search" on Select Patient page
    Then Select By "Regular Search" should be selected

  @PR023
  Scenario: Search for a non-existent patient on Select Patient page
    When User navigates to Select Patient page
    And User enters Select Patient First Name "NonExistentPatient987654"
    And User clicks Search on Select Patient page
    Then Select Patient search results should show "Your search did not yield any results"

  @PR024
  Scenario: New patient button navigates to Patient Demographics page
    When User navigates to Select Patient page
    And User clicks New patient button on Select Patient page
    Then User should navigate to Patient Search page "/patient/demographics"

  @PR025
  Scenario: Cancel button navigates back from Select Patient page
    When User navigates to Select Patient page
    And User clicks Cancel on Select Patient page
    Then User should leave the Select Patient page

  @PR026
  Scenario: Show and hide Password on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Registration password "TestPass123"
    Then Patient Registration password should be hidden
    When User clicks Patient Registration password visibility icon
    Then Patient Registration password should be visible
    When User clicks Patient Registration password visibility icon again
    Then Patient Registration password should be hidden

  @PR027
  Scenario: Practice is read-only and Location enables Physician dropdown
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    Then Practice field should be read-only with value "NEW VALUE HOSPITAL"
    And Physician dropdown should be disabled before Location is selected
    When User selects first available Location on Patient Registration form
    Then Physician dropdown should become enabled on Patient Registration form

  @PR028
  Scenario: Select Prefix, Suffix, and Sex dropdowns on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User selects Prefix "Dr", Suffix "JR", and Sex "Female" on Patient Registration form
    Then Prefix "Dr", Suffix "JR", and Sex "Female" should be selected

  @PR029
  Scenario: Auto-format National ID and Cell Phone on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient NID "29901011234567" and Cell Phone "1012345678"
    Then Patient NID should be formatted as "2-990101-12-3456-7" and Cell Phone as "+20 10 123 4567"

  @PR030
  Scenario: Toggle WhatsApp, Insured, and VIP radio options on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User sets WhatsApp to "No", Insured to "Insured", and VIP to "Yes"
    Then WhatsApp "No", Insured "Insured", and VIP "Yes" should be selected

  @PR031
  Scenario: Back button on Patient Registration form navigates to Patient Search
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User clicks Back on Patient Registration form
    Then User should navigate to Patient Search page "/patient/search"

  @PR032
  Scenario: Enter optional demographic and address fields on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User fills optional Patient Registration demographics:
      | middleName  | Mohamed       |
      | fatherName  | Ibrahim       |
      | motherName  | Fatima        |
      | medno       | MRN202601     |
      | address1    | 15 Nile St    |
      | address2    | Apt 4B        |
      | city        | Cairo         |
      | governorate | Cairo         |
      | zip         | 11511         |
      | ext         | 102           |
    Then optional Patient Registration demographics should retain entered values

  @PR033
  Scenario: Validate invalid Eve Phone format on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Eve Phone "123"
    And User clicks Submit on Patient Registration form
    Then Patient Registration validation error containing "Eve Phone# must match format" should be displayed

  @PR034
  Scenario: Validate invalid Postal Code format on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Postal Code "12"
    And User clicks Submit on Patient Registration form
    Then Patient Registration validation error containing "Postal Code must be 5 digits" should be displayed

  @PR035
  Scenario: Patient Search breadcrumb link navigates from Patient Registration page
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User clicks Patient Search breadcrumb link
    Then User should navigate to Patient Search page "/patient/search"

  @PR036
  Scenario: Date of Birth picker disables future dates on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    When User opens Patient Date of Birth picker
    Then future dates should be disabled in Date of Birth picker

  @PR037
  Scenario: Verify field maximum length restrictions on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters oversized values in User Name, Password, Medical Record No, and Ext
    Then User Name, Password, Medical Record No, and Ext should be limited to 16, 16, 11, and 5 characters

  @PR038
  Scenario: Changing Country resets Governorate on Patient Registration form
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User selects Governorate "Cairo" on Patient Registration form
    And User selects Country "United States" on Patient Registration form
    Then Country "United States" should be selected and Governorate should be reset

  @PR039
  Scenario: Clear button resets dropdowns and radio selections to default values
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User selects Prefix "Dr", Suffix "JR", and Sex "Female" on Patient Registration form
    And User sets WhatsApp to "No", Insured to "Insured", and VIP to "Yes"
    And User clicks Clear on Patient Registration form
    Then Patient Registration form should return to default selections

  @PR040
  Scenario: Admit to Bed button after Patient Registration navigates to Bed Allocation
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User fills valid mandatory Patient Registration details:
      | firstName | BedTest             |
      | lastName  | Patient             |
      | dob       | 10/08/1992          |
      | cellPhone | 1012345678          |
      | password  | TestPass123         |
      | email     | bedpat@example.com  |
    And User selects first available Location and Physician on Patient Registration form
    And User clicks Submit on Patient Registration form
    Then Patient should be registered successfully with a generated Patient ID
    When User clicks Admit to Bed button on registration success banner
    Then User should navigate to Patient Search page "/appointment/bed-allocate"

  @PR041
  Scenario: Search existing patient on Search Patient page and verify Add to Practice action
    When User navigates to Patient Registration page
    And User enters search First Name "AutoTest"
    And User clicks Search on Search Patient page
    Then Search Patient results table should display patient "AutoTest" with Add to Practice action

  @PR042
  Scenario: Search existing patient on Select Patient page and verify all row actions
    When User navigates to Select Patient page
    And User enters Select Patient First Name "AutoTest"
    And User clicks Search on Select Patient page
    Then Select Patient results table should display patient "AutoTest" with all row actions

  @PR043 @LegacyParityBug
  Scenario: Selecting Insured should display legacy Insurance Details section on Patient Registration
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User selects Is Patient Insured option "Insured" on Patient Registration form
    Then legacy Insurance Details fields should be displayed on Patient Registration form

  @PR044 @LegacyParityBug
  Scenario: Selecting WhatsApp No as No should display separate WhatsApp Number input as in legacy
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User selects WhatsApp No option "No" on Patient Registration form
    Then separate WhatsApp Number input field should be displayed as in legacy

  @PR045 @LegacyParityBug
  Scenario: Username and Password should enforce legacy minimum 6-character length
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Username "ab" and Password "123"
    And User clicks Submit on Patient Registration form
    Then minimum length validation errors for Username and Password should be displayed as in legacy

  @PR046 @LegacyParityBug
  Scenario: Email Id should reject invalid email format as in legacy
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters Patient Email "bad-email" on Patient Registration form
    And User clicks Submit on Patient Registration form
    Then invalid Email validation error should be displayed as in legacy

  @PR047 @LegacyParityBug
  Scenario: Whitespace-only First Name and Last Name should be rejected as in legacy
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters whitespace-only First Name and Last Name on Patient Registration form
    And User clicks Submit on Patient Registration form
    Then whitespace-only First Name and Last Name validation errors should be displayed as in legacy

  @PR048 @LegacyParityBug
  Scenario: Middle Name should reject digits and Ext.Med.Rec.No should reject special characters as in legacy
    When User navigates to Patient Registration page
    And User clicks New patient Registration button
    And User enters invalid Middle Name "123" and Medical Record No "MRN@#!" on Patient Registration form
    And User clicks Submit on Patient Registration form
    Then Middle Name and Ext.Med.Rec.No format validation errors should be displayed as in legacy

  @PR049 @LegacyParityBug
  Scenario: Registered patient Date of Birth should not shift back by one day in search results
    When User navigates to Select Patient page
    And User enters Select Patient First Name "AutoTest"
    And User clicks Search on Select Patient page
    Then Select Patient first row Date of Birth should match "05/15/1995" without off-by-one shift

  @PR050 @LegacyParityBug
  Scenario: Header practice banner should display Practice Name instead of numeric Practice ID as in legacy
    When User navigates to Patient Registration page
    Then header practice banner should display practice name "NEW VALUE HOSPITAL" instead of numeric Practice ID
