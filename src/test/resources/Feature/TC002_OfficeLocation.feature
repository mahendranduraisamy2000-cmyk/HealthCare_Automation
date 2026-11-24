@OfficeLocation
Feature: Office Location Management
  As an authorized staff user
  I want to create, search, view and modify office locations
  So that office location records are maintained correctly

  Background:
    Given User launches the application login page
    When User enters valid staff credentials "hospvalue01" and "testlogin001"
    And User clicks Authorized Login
    Then User should navigate to staff dashboard "HOSPVALUE01"

  @Critical @ATC001
  Scenario: Validate mandatory fields
    When User navigates to Practice Details
    And User opens Office Location
    And User opens Create Location page
    And User leaves all mandatory fields empty
    And User clicks Continue
    Then Mandatory field validation messages should be displayed
    And User should remain on Create Location page

  @High @ATC002
  Scenario Outline: Validate invalid email address
    When User opens Create Location page
    And User enters the following location details:
      | facilityName | <facilityName> |
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | address1     | <address1>     |
      | city         | <city>         |
      | email        | <email>        |
    And User clicks Continue
    Then Email validation message should be displayed

    Examples:
      | facilityName | firstName | lastName | address1       | city  | email         |
      | Auto Clinic  | John      | Smith    | 10 Main Street | Cairo | invalid-email |

  @Critical @ATC003
  Scenario Outline: Create a new Office Location
    When User opens Create Location page
    And User enters the following mandatory location details:
      | facilityName | <facilityName> |
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | address1     | <address1>     |
      | city         | <city>         |
      | email        | <email>        |
    And User clicks Continue
    Then Confirmation page should display the entered location details
    When User clicks Create Location
    Then Location should be created successfully
    And Location ID should be displayed

    Examples:
      | facilityName      | firstName | lastName | address1       | city  | email                  |
      | Automation Clinic | John      | Smith    | 10 Main Street | Cairo | john.smith@example.com |

  @High @ATC004
  Scenario: Verify Back to Edit retains entered details
    When User opens Create Location page
    And User enters valid mandatory location details
    And User clicks Continue
    And User clicks Back to Edit
    Then Previously entered location details should be retained

  @Critical @ATC005
  Scenario Outline: Search for an existing Office Location
    When User navigates to Practice Details
    And User opens Office Location search
    And User searches using Facility Name "<facilityName>"
    And User clicks Search Again
    Then Matching location records for "<facilityName>" should be displayed

    Examples:
      | facilityName      |
      | Automation Clinic |

  @Critical @ATC006
  Scenario Outline: Modify an existing Office Location
    When User searches for Facility Name "<facilityName>"
    And User opens the matching location for modification
    And User updates the City to "<updatedCity>"
    And User saves the location changes
    Then The updated City "<updatedCity>" should be displayed for the location

    Examples:
      | facilityName      | updatedCity |
      | Automation Clinic | Giza        |

  @High @ATC007
  Scenario Outline: Search using multiple criteria
    When User opens Office Location search
    And User enters Facility Name "<facilityName>"
    And User enters City "<city>"
    And User selects Governorate "<governorate>"
    And User clicks Search Again
    Then Search results should match the entered criteria

    Examples:
      | facilityName      | city  | governorate |
      | Automation Clinic | Cairo | Cairo       |

  @High @ATC008
  Scenario Outline: Search when no matching location exists
    When User opens Office Location search
    And User searches using Facility Name "<facilityName>"
    And User clicks Search Again
    Then No matching location records should be displayed

    Examples:
      | facilityName                   |
      | Location_Does_Not_Exist_987654 |

  @High @ATC009
  Scenario Outline: View an existing location
    When User searches for Facility Name "<facilityName>"
    And User clicks View for the matching location
    Then Location ID, Facility Name, Status and address should be displayed correctly

    Examples:
      | facilityName      |
      | Automation Clinic |

  @High @ATC010
  Scenario Outline: Verify saved location details across tabs
    When User searches for Facility Name "<facilityName>"
    And User opens the location using View
    Then Location Details tab should display the saved location information
    And Contact Person tab should display the saved contact information
    And Facility Licenses tab should display the saved license information
    And Discounts tab should display the saved discount information

    Examples:
      | facilityName      |
      | Automation Clinic |

  @Critical @ATC011
  Scenario Outline: Prevent duplicate facility creation
    When User opens Create Location page
    And User enters the following mandatory location details:
      | facilityName | <facilityName> |
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | address1     | <address1>     |
      | city         | <city>         |
      | email        | <email>        |
    And User clicks Continue
    And User clicks Create Location
    Then Duplicate facility should be rejected according to the application rule
    And A second record with Facility Name "<facilityName>" should not be created

    Examples:
      | facilityName      | firstName | lastName | address1       | city  | email                |
      | Automation Clinic | Jane      | Doe      | 20 Main Street | Cairo | jane.doe@example.com |

  @High @ATC012
  Scenario Outline: Validate invalid phone number
    When User opens Create Location page
    And User enters Day Phone "<phone>"
    And User completes all other mandatory fields with valid data
    And User clicks Continue
    Then Phone number validation should be displayed

    Examples:
      | phone    |
      | 123      |
      | ABC12345 |

  @High @ATC013
  Scenario: Treat spaces-only mandatory fields as empty
    When User opens Create Location page
    And User enters spaces only in mandatory text fields
    And User completes remaining mandatory fields with valid data
    And User clicks Continue
    Then Spaces-only values should fail mandatory validation

  @High @ATC014
  Scenario Outline: Reject discount values outside the permitted range
    When User opens Create Location page
    And User enters discount "<discount>" in the "<discountField>" field
    And User completes all mandatory fields with valid data
    And User clicks Continue
    Then Discount validation should be displayed

    Examples:
      | discountField     | discount |
      | Local Medicine    | -1       |
      | Imported Medicine | 101      |

  @High @ATC015
  Scenario: Reject expiry date earlier than issue date
    When User opens Create Location page
    And User enters valid mandatory location details
    And User enters license Issue Date "2026-12-31"
    And User enters license Expiry Date "2026-01-01"
    And User clicks Continue
    Then License date range validation should be displayed

  @Critical @ATC016
  Scenario Outline: Prevent duplicate submission when Create Location is clicked twice
    When User opens Create Location page
    And User enters valid mandatory location details with Facility Name "<facilityName>"
    And User clicks Continue
    And User double-clicks Create Location
    Then Only one location record should exist with Facility Name "<facilityName>"

    Examples:
      | facilityName            |
      | DoubleSubmit Clinic 001 |

  @High @ATC017
  Scenario Outline: Create location with optional fields left blank
    When User opens Create Location page
    And User enters all mandatory location details:
      | facilityName | <facilityName> |
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | address1     | <address1>     |
      | city         | <city>         |
      | email        | <email>        |
    And User leaves optional fields blank
    And User clicks Continue
    And User clicks Create Location
    Then Location should be created successfully

    Examples:
      | facilityName         | firstName | lastName | address1       | city  | email                  |
      | OptionalBlank Clinic | Mary      | Jones    | 30 Main Street | Cairo | mary.jones@example.com |
