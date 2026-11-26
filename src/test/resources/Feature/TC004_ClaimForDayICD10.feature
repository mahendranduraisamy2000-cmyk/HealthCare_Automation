@ClaimForDayICD10
Feature: Billing Details - Claim for Day-ICD10
  As an authorized staff user
  I want to search, filter, sort, paginate, reset, and validate daily ICD10 claims
  So that daily billing statuses and unbilled services are monitored accurately

  # ==================== Positive Scenarios ====================

  @Positive @Critical @POS_01
  Scenario: POS_01 - Navigate to Claim for Day-ICD10 page
    Given User launches the application login page
    When User enters valid staff credentials "${staff.username}" and "${staff.password}"
    And User clicks Authorized Login
    Then User should navigate to staff dashboard "${staff.displayName}"
    When User navigates to Billing Details and opens Claim for Day-ICD10
    Then Claim for Day-ICD10 page should be displayed with Search Filters and Claims List

  @Positive @High @POS_02
  Scenario: POS_02 - Default page load and filter state
    Given User is on Claim for Day-ICD10 page
    Then Default Claim for Day-ICD10 filters should be set to Location "All", Physician "All", Payor "All", Display "Open", and Include NoShow "No"
    And Claims List table and pagination entries text should be displayed

  @Positive @High @POS_03
  Scenario: POS_03 - Filter by Location, Physician, and Payor
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "01/01/2020" and End Date "11/26/2026" on Claim for Day-ICD10 page
    And User selects Location filter "", Physician filter "", and Payor filter ""
    And User clicks Submit on Claim for Day-ICD10 page
    Then Claims List should update cleanly without application errors

  @Positive @High @POS_04
  Scenario Outline: POS_04 - Filter by valid Start Date and End Date range
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "<startDate>" and End Date "<endDate>" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Displayed claims should fall between "<startDate>" and "<endDate>"

    Examples:
      | startDate  | endDate    |
      | 01/01/2020 | 11/26/2026 |

  @Positive @High @POS_05
  Scenario Outline: POS_05 - Single-day search
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "<singleDate>" and End Date "<singleDate>" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Displayed claims should fall between "<singleDate>" and "<singleDate>"

    Examples:
      | singleDate |
      | 09/22/2026 |

  @Positive @Medium @POS_06
  Scenario: POS_06 - Toggle Display status filter
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "01/01/2020" and End Date "11/26/2026" on Claim for Day-ICD10 page
    And User selects Display filter "All" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Display filter should show "All" and Claims List should update cleanly

  @Positive @Medium @POS_07
  Scenario: POS_07 - Toggle Include NoShow radio button
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "01/01/2020" and End Date "11/26/2026" on Claim for Day-ICD10 page
    And User selects Include NoShow "Yes" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Include NoShow "Yes" should be selected and Claims List should update cleanly
    When User selects Include NoShow "No" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Include NoShow "No" should be selected and Claims List should update cleanly

  @Positive @Medium @POS_08
  Scenario: POS_08 - Reset filters to default
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "01/01/2020" and End Date "11/26/2026" on Claim for Day-ICD10 page
    And User selects Location filter "", Physician filter "", and Payor filter ""
    And User selects Display filter "All" on Claim for Day-ICD10 page
    And User selects Include NoShow "Yes" on Claim for Day-ICD10 page
    And User clicks Reset on Claim for Day-ICD10 page
    Then Default Claim for Day-ICD10 filters should be set to Location "All", Physician "All", Payor "All", Display "Open", and Include NoShow "No"

  @Positive @Medium @POS_09
  Scenario: POS_09 - Column sorting in Claims List
    Given User is on Claim for Day-ICD10 page
    When User selects Display filter "All" on Claim for Day-ICD10 page
    And User sets Start Date "01/01/2020" and End Date "11/26/2026" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    And User clicks sort on "DATE" column in Claims List
    And User clicks sort on "TIME" column in Claims List
    Then Claims List should remain sorted and stable without errors

  @Positive @Medium @POS_10
  Scenario: POS_10 - Pagination controls
    Given User is on Claim for Day-ICD10 page
    When User selects Display filter "All" on Claim for Day-ICD10 page
    And User sets Start Date "01/01/2020" and End Date "11/26/2026" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Pagination controls should display entries count and switch pages when multiple pages exist

  @Positive @Medium @POS_11
  Scenario: POS_11 - Action links and Go Back button
    Given User is on Claim for Day-ICD10 page
    When User clicks a claim row link in Claims List if available
    And User returns to Claim for Day-ICD10 and clicks Go Back
    Then Go Back should navigate away from Claim for Day-ICD10 page

  # ==================== Negative Scenarios ====================

  @Negative @High @NEG_01
  Scenario: NEG_01 - Start Date after End Date
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "11/26/2026" and End Date "08/26/2026" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Invalid date range should be blocked or return no matching claims cleanly

  @Negative @High @NEG_02
  Scenario Outline: NEG_02 - Invalid or malformed date input
    Given User is on Claim for Day-ICD10 page
    When User enters malformed Start Date "<badDate>" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Malformed date "<badDate>" should be rejected or handled safely without crashing

    Examples:
      | badDate    |
      | 99/99/2026 |
      | 02/30/2026 |

  @Negative @High @NEG_03
  Scenario: NEG_03 - Blank or cleared date fields
    Given User is on Claim for Day-ICD10 page
    When User clears Start Date and End Date on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Cleared date submission should be blocked or handled safely without crashing

  @Negative @Medium @NEG_04
  Scenario: NEG_04 - Filter combination with no matching claims
    Given User is on Claim for Day-ICD10 page
    When User selects Location filter "", Physician filter "", and Payor filter ""
    And User sets Start Date "01/01/2000" and End Date "01/02/2000" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Claims List should display an empty state with zero entries

  @Negative @Medium @NEG_05
  Scenario Outline: NEG_05 - Extreme past or future dates
    Given User is on Claim for Day-ICD10 page
    When User sets Start Date "<startDate>" and End Date "<endDate>" on Claim for Day-ICD10 page
    And User clicks Submit on Claim for Day-ICD10 page
    Then Extreme date range should be handled cleanly without server error

    Examples:
      | startDate  | endDate    |
      | 01/01/1900 | 01/02/1900 |
      | 01/01/2099 | 01/02/2099 |

  @Negative @Medium @NEG_06
  Scenario: NEG_06 - Rapid repeated Submit or Reset clicks
    Given User is on Claim for Day-ICD10 page
    When User rapidly clicks Submit 3 times on Claim for Day-ICD10 page
    And User rapidly clicks Reset 3 times on Claim for Day-ICD10 page
    Then Claim for Day-ICD10 page should remain stable with no stuck loading state

  @Negative @Critical @NEG_07
  Scenario: NEG_07 - Direct URL access without authentication
    Given User launches a fresh unauthenticated browser session
    When User opens Claim for Day-ICD10 URL directly without logging in
    Then User should be redirected to login page without exposing claims data

  @Negative @High @NEG_08
  Scenario: NEG_08 - Network failure or 500 error on submit
    Given User is on Claim for Day-ICD10 page
    When User simulates a 500 server error and clicks Submit on Claim for Day-ICD10 page
    Then Claim for Day-ICD10 page should handle the server error gracefully and allow retry
