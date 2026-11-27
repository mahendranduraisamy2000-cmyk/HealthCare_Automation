@SearchPatient
Feature: Search Patient - High Priority Automation

  As an application user
  I want to search patients
  So that I can retrieve accurate patient information

  Background:
    Given user launches the application
    And user logs in with valid credentials
    And user navigates to the Select Patient page

  @TC001 @Smoke
  Scenario: Verify Select Patient page opens
    Then Select Patient page should be displayed
    And all search fields should be visible

  @TC002 @Smoke
  Scenario Outline: Search using valid Patient ID
    When user enters Patient ID "<patientId>"
    And user clicks Search button
    Then matching patient "<patientName>" should be displayed

    Examples:
      | patientId | patientName  |
      |1000354477 | karthi       |
      

  @TC003 @Smoke
  Scenario Outline: Search using valid National ID
    When user enters National ID "<nationalId>"
    And user clicks Search button
    Then matching patient "<patientName>" should be displayed

    Examples:
      | nationalId         | patientName  |
      | 3-433423-42-3545-4 |  karthi k  |
     

  @TC004
  Scenario Outline: Search using First Name
    When user enters First Name "<firstName>"
    And user clicks Search button
    Then matching First Name records "<firstName>" should be displayed

    Examples:
      | firstName  |
      | karthi     |
     

  @TC005
  Scenario Outline: Search using Last Name
    When user enters Last Name "<lastName>"
    And user clicks Search button
    Then matching Last Name records "<lastName>" should be displayed

    Examples:
      | lastName |
      |  reddy   |
      

  @TC006 @Medium
  Scenario Outline: Search patient using Username
    When user enters Username "<username>"
    And user clicks Search button
    Then matching patient "<expectedPatient>" should be displayed

    Examples:
      | username     | expectedPatient |
      | karthi01     |   karthi  |
   

  @TC007 @Medium
  Scenario Outline: Search patient using Date of Birth
    When user enters Date of Birth "<dob>"
    And user clicks Search button
    Then patients with Date of Birth "<dob>" should be displayed

    Examples:
      | dob        |
      | 08-25-2020 |
      

  @TC008 @Smoke
  Scenario Outline: Search using multiple criteria
    When user enters First Name "<firstName>"
    And user enters Last Name "<lastName>"
    And user enters Date of Birth "<dob>"
    And user clicks Search button
    Then patient matching all criteria should be displayed

    Examples:
      | firstName | lastName |   dob        |
      | karthi    | reddy    |  08-25-2020  |

  @TC009 @Medium
  Scenario Outline: Search patient using Sports Type
    When user selects Sports Type "<sport>"
    And user clicks Search button
    Then patients associated with "<sport>" should be displayed

    Examples:
      | sport      |
      | Cricket    |
 

  @TC010 @Medium
  Scenario Outline: Search patient using Club or Team Name
    When user selects Sports Type "<sport>"
    And user selects Club or Team Name "<team>"
    And user clicks Search button
    Then patients associated with team "<team>" should be displayed

    Examples:
      | sport    | team              |
      | Cricket  | Cricket Team one  |

  @TC011
  Scenario: Verify Regular Search
    When user selects Regular Search radio button
    And user enters Patient ID "1000354477"
    And user clicks Search button
    Then Regular Search results should be displayed

  @TC012
  Scenario: Verify Statement Search
    When user selects Statement radio button
    And user enters Patient ID "1000354477"
    And user clicks Search button
    Then Statement search should execute successfully

  @TC013
  Scenario: Verify Clear button
    When user enters Patient ID "1000354477"
    And user enters First Name "karthi"
    And user enters Last Name "reddy"
    And user clicks Clear button
    Then all search fields should be cleared

  @TC014 @Negative
  Scenario: Search using invalid Patient ID
    When user enters Patient ID "1000987477"
    And user clicks Search button
    Then no matching patient should be displayed

  @TC015 @Negative @High
  Scenario: Search with invalid National ID
    When user enters National ID "3-411423-42-3545-4"
    And user clicks Search button
    Then no matching patient should be displayed
    And appropriate validation message should appear

  @TC016 @Negative @Medium
  Scenario: Search using non-existing First Name
    When user enters First Name "Arul"
    And user clicks Search button
    Then no matching patient records should be displayed

  @TC017 @Negative @Medium
  Scenario: Search using non-existing Last Name
    When user enters Last Name "praveen"
    And user clicks Search button
    Then no matching patient records should be displayed

  @TC018 @Negative @Medium
  Scenario Outline: Search using special characters in Patient ID
    When user enters Patient ID "<patientId>"
    And user clicks Search button
    Then invalid Patient ID should be rejected or handled correctly

    Examples:
      | patientId |
      | @@@###    |
    
  @TC019 @Negative @Medium
  Scenario Outline: Search using special characters in First Name
    When user enters First Name "<firstName>"
    And user clicks Search button
    Then invalid First Name characters should be handled correctly

    Examples:
      | firstName |
      | @@@       |
    

  @TC020 @Negative @High
  Scenario Outline: Search with invalid Date of Birth
    When user enters Date of Birth "<dob>"
    And user clicks Search button
    Then invalid Date of Birth should not be accepted
    And appropriate validation should be displayed

    Examples:
      | dob         |
      | 379-89-2222 |

  @TC021 @Negative
  Scenario: Search using mismatching patient details
    When user enters Patient ID "1000354477"
    And user enters First Name "David"
    And user clicks Search button
    Then no incorrect patient record should be returned

  @TC022 @Negative
  Scenario: Search without search criteria
    When user clicks Search button without entering criteria
    Then configured empty search validation should be displayed

  @TC023 @Negative @Medium
  Scenario: Search using invalid Username
    When user enters Username "tillvalue10"
    And user clicks Search button
    Then no matching patient should be displayed

  @TC024 @Medium
  Scenario: Search using leading and trailing spaces
    When user enters First Name "  karthi  "
    And user clicks Search button
    Then matching patient records should not be displayed

  @TC025 @Low
  Scenario: Search using uppercase First Name
    When user enters First Name "KARTHI"
    And user clicks Search button
    Then search results should follow case sensitivity rules

  @TC026 @Low
  Scenario: Search using lowercase First Name
    When user enters First Name "karthi"
    And user clicks Search button
    Then search results should follow case sensitivity rules

  @TC027 @Medium
  Scenario Outline: Search using partial First Name
    When user enters First Name "<partialName>"
    And user clicks Search button
    Then matching records should be displayed if partial search is supported

    Examples:
      | partialName |
      | kart         |

  @TC028 @Medium
  Scenario Outline: Search using partial Last Name
    When user enters Last Name "<partialName>"
    And user clicks Search button
    Then matching records should be displayed if partial search is supported

    Examples:
      | partialName |
      | red     |

  @TC029 @High
  Scenario: Verify multiple matching patient records
    When user enters First Name "karthi"
    And user clicks Search button
    Then all matching patient records should be displayed
    And duplicate patient records should not be displayed

  @TC030 @Medium
  Scenario: Verify repeated Search button clicks
    When user enters Patient ID "1000354477"
    And user clicks Search button multiple times quickly
    Then consistent search results should be displayed
    And duplicate results should not be generated

  @TC031
  Scenario: Search after clearing previous criteria
    When user enters Patient ID "1000354477"
    And user clicks Clear button
    And user enters Patient ID "1000346629"
    And user clicks Search button
    Then only patient "1000346629" should be displayed

  @TC032 @Boundary @High
  Scenario: Verify minimum allowed Patient ID length
    When user enters Patient ID "100077"
    And user clicks Search button
    Then application should accept the minimum allowed length

  @TC033 @Boundary @High
  Scenario: Verify maximum allowed Patient ID length
    When user enters Patient ID "1000354477"
    And user clicks Search button
    Then application should accept the maximum allowed length

  @TC034 @Boundary @Medium
  Scenario: Verify maximum First Name length
    When user enters First Name "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    And user clicks Search button
    Then application should accept the maximum supported length

  @TC035 @Boundary
  Scenario: Verify future DOB restriction
    When user attempts to select future DOB "25-11-2029"
    Then future DOB should be restricted

  @TC036 @Boundary @High
  Scenario Outline: Verify minimum and maximum National ID length
    When user enters National ID "<nationalId>"
    And user clicks Search button
    Then National ID should be validated according to configured length

    Examples:
      | nationalId      |
      | 3-433423         |
      | 3-433423-42-3545-4 |
