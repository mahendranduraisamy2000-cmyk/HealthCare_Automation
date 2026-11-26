@ClaimICD10
Feature: Billing Details - Claim ICD10 Submission
  As an authorized staff user
  I want to create, validate, and submit ICD10 claims under Billing Details
  So that patient diagnosis and service billing details are recorded accurately

  Background:
    Given User launches the application login page
    When User enters valid staff credentials "${staff.username}" and "${staff.password}"
    And User clicks Authorized Login
    Then User should navigate to staff dashboard "${staff.displayName}"

  # ==================== Positive Scenarios ====================

  @Positive @Critical @CLM001
  Scenario Outline: Create and submit a Claim ICD10 with patient, file ref no, ICD code, and service details
    When User navigates to Billing Details and opens Claim ICD10
    Then Claim Details ICD10 page should be displayed
    When User searches and selects a patient using "<patientSearch>"
    And User enters File Ref No "<fileRefNo>"
    And User selects mandatory physician and location assignment
    And User chooses ICD code "<icdCode>" from the ICD menu
    And User provides service details with From Date "<fromDate>", To Date "<toDate>", CPT code "<cptCode>", Diagnosis Pointer <diagPtr>, and Unit "<unit>"
    And User submits the Claim ICD10 form
    Then Claim ICD10 should be submitted successfully

    Examples:
      | patientSearch | fileRefNo      | icdCode | fromDate   | toDate     | cptCode | diagPtr | unit |
      | a             | REF-ICD10-0001 | J20.1   | 11/25/2026 | 11/25/2026 | 1124F   | 1       | 1    |

  @Positive @High @CLM002
  Scenario Outline: Create and submit a Claim ICD10 with multiple ICD codes and multiple service units
    When User navigates to Billing Details and opens Claim ICD10
    And User searches and selects a patient using "<patientSearch>"
    And User enters File Ref No "<fileRefNo>"
    And User selects mandatory physician and location assignment
    And User chooses ICD code "<primaryIcd>" in diagnosis slot 1 from the ICD menu
    And User chooses ICD code "<secondaryIcd>" in diagnosis slot 2 from the ICD menu
    And User provides service details with From Date "<fromDate>", To Date "<toDate>", POS "<pos>", CPT code "<cptCode>", Diagnosis Pointer 1, and Unit "<unit>"
    And User also selects Diagnosis Pointer 2 in Service Details
    Then Claim total charges should be greater than 0
    When User submits the Claim ICD10 form
    Then Claim ICD10 should be submitted successfully

    Examples:
      | patientSearch | fileRefNo      | primaryIcd | secondaryIcd | fromDate   | toDate     | pos | cptCode | unit |
      | a             | REF-ICD10-0002 | J20.1      | J20.0        | 11/25/2026 | 11/25/2026 | 11  | 1124F   | 2    |

  @Positive @Medium @CLM003
  Scenario: Select ICD and CPT codes via alphabet filter and verify charges auto-populate
    When User navigates to Billing Details and opens Claim ICD10
    And User chooses ICD code "A" from the ICD menu
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "11", CPT code "A", Diagnosis Pointer 1, and Unit "1"
    Then Diagnosis code 1 and Row 1 CPT code should be populated
    And Claim total charges should be greater than 0

  @Positive @Medium @CLM004
  Scenario: Clear button resets entered Claim ICD10 form fields
    When User navigates to Billing Details and opens Claim ICD10
    And User enters File Ref No "REF-CLEAR-001"
    And User chooses ICD code "J20.1" from the ICD menu
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "11", CPT code "1124F", Diagnosis Pointer 1, and Unit "1"
    And User clicks Clear on Claim ICD10 page
    Then Claim ICD10 entered fields should be cleared

  @Positive @Medium @CLM005
  Scenario: Check Rule validates a filled Claim ICD10 form
    When User navigates to Billing Details and opens Claim ICD10
    And User searches and selects a patient using "a"
    And User enters File Ref No "REF-RULE-001"
    And User selects mandatory physician and location assignment
    And User chooses ICD code "J20.1" from the ICD menu
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "11", CPT code "1124F", Diagnosis Pointer 1, and Unit "1"
    And User clicks Check Rule on Claim ICD10 page
    Then Claim rule check feedback should be displayed

  # ==================== Negative Scenarios ====================

  @Negative @Critical @CLM006
  Scenario: Submit Claim ICD10 with all mandatory fields empty
    When User navigates to Billing Details and opens Claim ICD10
    And User submits the Claim ICD10 form
    Then Claim ICD10 validation error should be displayed
    And User should remain on Claim Details ICD10 page

  @Negative @High @CLM007
  Scenario: Submit Claim ICD10 without selecting a patient
    When User navigates to Billing Details and opens Claim ICD10
    And User enters File Ref No "REF-NOPAT-001"
    And User selects mandatory physician and location assignment
    And User chooses ICD code "J20.1" from the ICD menu
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "11", CPT code "1124F", Diagnosis Pointer 1, and Unit "1"
    And User submits the Claim ICD10 form
    Then Claim ICD10 validation error should be displayed
    And User should remain on Claim Details ICD10 page

  @Negative @High @CLM008
  Scenario: Submit Claim ICD10 without File Ref No
    When User navigates to Billing Details and opens Claim ICD10
    And User searches and selects a patient using "a"
    And User clears File Ref No
    And User selects mandatory physician and location assignment
    And User chooses ICD code "J20.1" from the ICD menu
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "11", CPT code "1124F", Diagnosis Pointer 1, and Unit "1"
    And User submits the Claim ICD10 form
    Then Claim ICD10 validation error should be displayed
    And User should remain on Claim Details ICD10 page

  @Negative @High @CLM009
  Scenario: Submit Claim ICD10 without selecting an ICD diagnosis code
    When User navigates to Billing Details and opens Claim ICD10
    And User searches and selects a patient using "a"
    And User enters File Ref No "REF-NOICD-001"
    And User selects mandatory physician and location assignment
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "11", CPT code "1124F", Diagnosis Pointer 1, and Unit "1"
    And User submits the Claim ICD10 form
    Then Claim ICD10 validation error should be displayed
    And User should remain on Claim Details ICD10 page

  @Negative @High @CLM010
  Scenario: Submit Claim ICD10 without Service Details
    When User navigates to Billing Details and opens Claim ICD10
    And User searches and selects a patient using "a"
    And User enters File Ref No "REF-NOSVC-001"
    And User selects mandatory physician and location assignment
    And User chooses ICD code "J20.1" from the ICD menu
    And User submits the Claim ICD10 form
    Then Claim ICD10 validation error should be displayed
    And User should remain on Claim Details ICD10 page

  @Negative @High @CLM011
  Scenario: Reject Inpatient POS 21 submission when Admission Date is missing
    When User navigates to Billing Details and opens Claim ICD10
    And User searches and selects a patient using "a"
    And User enters File Ref No "REF-POS21-001"
    And User selects mandatory physician and location assignment
    And User chooses ICD code "J20.1" from the ICD menu
    And User provides service details with From Date "11/25/2026", To Date "11/25/2026", POS "21", CPT code "1124F", Diagnosis Pointer 1, and Unit "1"
    And User submits the Claim ICD10 form
    Then Claim ICD10 validation message should contain "date of admission"
    And User should remain on Claim Details ICD10 page

  @Negative @Medium @CLM012
  Scenario: Search for a non-existent ICD code in the ICD lookup menu
    When User navigates to Billing Details and opens Claim ICD10
    And User searches for non-existent ICD code "ZZZZ999" in the ICD menu
    Then No matching ICD code records should be displayed
