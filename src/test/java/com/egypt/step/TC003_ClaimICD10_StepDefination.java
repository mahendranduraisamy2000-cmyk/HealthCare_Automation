package com.egypt.step;

import org.testng.Assert;

import com.egypt.page.ClaimICD10Page;
import com.egypt.utills.BaseClass;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC003_ClaimICD10_StepDefination extends BaseClass {

	private ClaimICD10Page claimICD10Page;
	private String lastFeedback = "";

	private ClaimICD10Page page() {
		if (claimICD10Page == null) {
			claimICD10Page = new ClaimICD10Page(driver);
		}
		return claimICD10Page;
	}

	@When("User navigates to Billing Details and opens Claim ICD10")
	public void user_navigates_to_billing_details_and_opens_claim_icd10() {
		page().clickBillingDetailsMenu();
		page().clickClaimICD10Menu();
	}

	@Then("Claim Details ICD10 page should be displayed")
	public void claim_details_icd10_page_should_be_displayed() {
		Assert.assertTrue(page().isClaimICD10PageDisplayed(),
				"Claim Details (ICD10) page is not displayed. Current URL: " + getApplnUrl());
	}

	@When("User searches and selects a patient using {string}")
	public void user_searches_and_selects_a_patient_using(String patientSearch) {
		page().searchAndSelectPatient(patientSearch);
		String selectedPatientId = page().getSelectedPatientId();
		Assert.assertTrue(selectedPatientId != null && !selectedPatientId.trim().isEmpty(),
				"Patient ID was not populated after selecting a patient");
	}

	@When("User enters File Ref No {string}")
	public void user_enters_file_ref_no(String fileRefNo) {
		page().enterFileRefNo(fileRefNo);
		Assert.assertEquals(page().getFileRefNo(), fileRefNo, "File Ref No was not entered correctly");
	}

	@When("User clears File Ref No")
	public void user_clears_file_ref_no() {
		page().clearFileRefNo();
		Assert.assertEquals(page().getFileRefNo(), "", "File Ref No was not cleared");
	}

	@When("User selects mandatory physician and location assignment")
	public void user_selects_mandatory_physician_and_location_assignment() {
		page().selectMandatoryPhysiciansAndLocation("", "", "");
	}

	@When("User chooses ICD code {string} from the ICD menu")
	public void user_chooses_icd_code_from_the_icd_menu(String icdCode) {
		page().chooseICDCodeFromMenu(1, icdCode);
		String selectedCode = page().getSelectedDiagnosisCode(1);
		Assert.assertTrue(selectedCode != null && !selectedCode.trim().isEmpty(),
				"ICD-10 Diagnosis Code was not populated from the ICD menu");
	}

	@When("User chooses ICD code {string} in diagnosis slot {int} from the ICD menu")
	public void user_chooses_icd_code_in_diagnosis_slot_from_the_icd_menu(String icdCode, int slot) {
		page().chooseICDCodeFromMenu(slot, icdCode);
		String selectedCode = page().getSelectedDiagnosisCode(slot);
		Assert.assertTrue(selectedCode != null && !selectedCode.trim().isEmpty(),
				"ICD-10 Diagnosis Code was not populated in slot " + slot);
	}

	@When("User searches for non-existent ICD code {string} in the ICD menu")
	public void user_searches_for_non_existent_icd_code_in_the_icd_menu(String icdCode) {
		page().searchICDCodeInModal(icdCode);
	}

	@When("User provides service details with From Date {string}, To Date {string}, CPT code {string}, Diagnosis Pointer {int}, and Unit {string}")
	public void user_provides_service_details(String fromDate, String toDate, String cptCode, int diagPtr,
			String unit) {
		user_provides_service_details_with_pos(fromDate, toDate, "11", cptCode, diagPtr, unit);
	}

	@When("User provides service details with From Date {string}, To Date {string}, POS {string}, CPT code {string}, Diagnosis Pointer {int}, and Unit {string}")
	public void user_provides_service_details_with_pos(String fromDate, String toDate, String pos, String cptCode,
			int diagPtr, String unit) {
		page().enterServiceDates(fromDate, toDate);
		page().enterServicePos(pos);
		page().chooseServiceCPTFromMenu(cptCode);
		page().selectDiagnosisPointer(diagPtr);
		page().enterServiceChargesAndUnit("", unit);
		String selectedCpt = page().getRow1CptCode();
		Assert.assertTrue(selectedCpt != null && !selectedCpt.trim().isEmpty(),
				"Service CPT code was not populated in Service Details row 1");
	}

	@When("User also selects Diagnosis Pointer {int} in Service Details")
	public void user_also_selects_diagnosis_pointer_in_service_details(int diagPtr) {
		page().selectDiagnosisPointer(diagPtr);
	}

	@When("User clicks Clear on Claim ICD10 page")
	public void user_clicks_clear_on_claim_icd10_page() {
		page().clickClear();
	}

	@When("User clicks Check Rule on Claim ICD10 page")
	public void user_clicks_check_rule_on_claim_icd10_page() {
		page().clickCheckRule();
		lastFeedback = page().waitForSubmissionFeedback();
	}

	@When("User submits the Claim ICD10 form")
	public void user_submits_the_claim_icd10_form() {
		page().clickSubmit();
	}

	@Then("Claim ICD10 should be submitted successfully")
	public void claim_icd10_should_be_submitted_successfully() {
		lastFeedback = page().waitForSubmissionFeedbackProceedingIfDuplicate();
		Assert.assertFalse(page().hasValidationErrors(),
				"Claim ICD10 form displayed validation errors: " + page().getAllValidationErrors());
		Assert.assertTrue(page().isSubmissionSuccess(lastFeedback),
				"Claim ICD10 submission confirmation was not displayed. Feedback: '" + lastFeedback
						+ "', URL: " + getApplnUrl());
	}

	@Then("Diagnosis code 1 and Row 1 CPT code should be populated")
	public void diagnosis_code_1_and_row_1_cpt_code_should_be_populated() {
		Assert.assertFalse(page().getSelectedDiagnosisCode(1).trim().isEmpty(),
				"Diagnosis Code 1 was not populated");
		Assert.assertFalse(page().getRow1CptCode().trim().isEmpty(),
				"Row 1 CPT code was not populated");
	}

	@Then("Claim total charges should be greater than 0")
	public void claim_total_charges_should_be_greater_than_0() {
		double total = page().getClaimTotalCharges();
		Assert.assertTrue(total > 0.0,
				"Expected Claim Charges(EGP) to be > 0, but was: " + total);
	}

	@Then("Claim ICD10 entered fields should be cleared")
	public void claim_icd10_entered_fields_should_be_cleared() {
		waitForSeconds();
		Assert.assertEquals(page().getFileRefNo(), "", "File Ref No was not cleared");
		Assert.assertEquals(page().getSelectedDiagnosisCode(1), "", "Diagnosis Code 1 was not cleared");
		Assert.assertEquals(page().getRow1CptCode(), "", "Row 1 CPT code was not cleared");
	}

	@Then("Claim rule check feedback should be displayed")
	public void claim_rule_check_feedback_should_be_displayed() {
		Assert.assertTrue(page().isClaimICD10PageDisplayed(),
				"Check Rule navigated away unexpectedly. URL: " + getApplnUrl());
		Assert.assertFalse(lastFeedback == null || lastFeedback.trim().isEmpty(),
				"No Check Rule feedback message or dialog was displayed");
	}

	@Then("Claim ICD10 validation error should be displayed")
	public void claim_icd10_validation_error_should_be_displayed() {
		lastFeedback = page().waitForSubmissionFeedback();
		Assert.assertTrue(page().isValidationOrWarningDisplayed(lastFeedback),
				"Expected validation error or warning, but got feedback: '" + lastFeedback + "'");
	}

	@Then("Claim ICD10 validation message should contain {string}")
	public void claim_icd10_validation_message_should_contain(String expectedText) {
		lastFeedback = page().waitForSubmissionFeedback();
		Assert.assertTrue(lastFeedback.toLowerCase().contains(expectedText.toLowerCase()),
				"Expected validation message containing '" + expectedText + "', but got: '" + lastFeedback + "'");
	}

	@Then("User should remain on Claim Details ICD10 page")
	public void user_should_remain_on_claim_details_icd10_page() {
		Assert.assertTrue(page().isClaimICD10PageDisplayed(),
				"User navigated away from Claim Details (ICD10) page. Current URL: " + getApplnUrl());
	}

	@Then("No matching ICD code records should be displayed")
	public void no_matching_icd_code_records_should_be_displayed() {
		boolean empty = page().isLookupModalNoRecordsDisplayed();
		page().closeActiveModalIfOpen();
		Assert.assertTrue(empty, "Expected no matching ICD code records for non-existent code");
	}
}
