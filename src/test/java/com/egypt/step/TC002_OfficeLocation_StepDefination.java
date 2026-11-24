package com.egypt.step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.egypt.page.OfficeLocationPage;
import com.egypt.utills.BaseClass;

import io.cucumber.java.en.*;

public class TC002_OfficeLocation_StepDefination extends BaseClass {

	OfficeLocationPage officeLocationPage;

	// Data entered in the current scenario (used for later verifications)
	Map<String, String> enteredData = new HashMap<>();
	String searchedFacility;
	String searchedCity;
	int recordCountBefore = -1;

	private OfficeLocationPage page() {
		if (officeLocationPage == null) {
			officeLocationPage = new OfficeLocationPage(driver);
		}
		return officeLocationPage;
	}

	private Map<String, String> validData(String facilityName) {
		Map<String, String> data = new HashMap<>();
		data.put("facilityName", facilityName);
		data.put("firstName", "John");
		data.put("lastName", "Smith");
		data.put("address1", "10 Main Street");
		data.put("city", "Cairo");
		data.put("email", "john.smith@example.com");
		return data;
	}

	private void enterData(Map<String, String> data) {
		enteredData = new HashMap<>(data);
		page().enterLocationDetails(enteredData);
	}

	private int searchCount(String facilityName) {
		page().openSearchLocation();
		page().enterFacilityName(facilityName);
		page().clickSearchAgain();
		int count = 0;
		for (WebElement row : page().getResultRows()) {
			if (row.getText().contains(facilityName)) {
				count++;
			}
		}
		return count;
	}

	// ---------------- Navigation ----------------

	@When("User navigates to Practice Details")
	public void user_navigates_to_practice_details() {
		page().clickPracticeDetails();
	}

	@When("User opens Office Location")
	public void user_opens_office_location() {
		page().clickOfficeLocation();
	}

	@When("User opens Create Location page")
	public void user_opens_create_location_page() {
		page().openCreateLocation();
		Assert.assertTrue(page().isOnCreateLocationPage(), "Create Location page is not displayed");
	}

	@When("User opens Office Location search")
	public void user_opens_office_location_search() {
		page().openSearchLocation();
	}

	// ---------------- Create form ----------------

	@When("User leaves all mandatory fields empty")
	public void user_leaves_all_mandatory_fields_empty() {
		page().clearMandatoryFields();
	}

	@When("User enters the following location details:")
	public void user_enters_the_following_location_details(Map<String, String> data) {
		enterData(data);
	}

	@When("User enters the following mandatory location details:")
	public void user_enters_the_following_mandatory_location_details(Map<String, String> data) {
		enterData(data);
	}

	@When("User enters all mandatory location details:")
	public void user_enters_all_mandatory_location_details(Map<String, String> data) {
		enterData(data);
	}

	@When("User enters valid mandatory location details")
	public void user_enters_valid_mandatory_location_details() {
		enterData(validData("Automation Clinic " + System.currentTimeMillis()));
	}

	@When("User enters valid mandatory location details with Facility Name {string}")
	public void user_enters_valid_mandatory_location_details_with_facility_name(String facilityName) {
		recordCountBefore = searchCount(facilityName);
		page().openCreateLocation();
		enterData(validData(facilityName));
	}

	@When("User completes all other mandatory fields with valid data")
	public void user_completes_all_other_mandatory_fields_with_valid_data() {
		enterData(validData("Automation Clinic " + System.currentTimeMillis()));
	}

	@When("User completes remaining mandatory fields with valid data")
	public void user_completes_remaining_mandatory_fields_with_valid_data() {
		// Text fields already filled with spaces; email is the remaining mandatory field
		page().enterEmail("john.smith@example.com");
	}

	@When("User completes all mandatory fields with valid data")
	public void user_completes_all_mandatory_fields_with_valid_data() {
		enterData(validData("Automation Clinic " + System.currentTimeMillis()));
	}

	@When("User enters spaces only in mandatory text fields")
	public void user_enters_spaces_only_in_mandatory_text_fields() {
		Map<String, String> data = new HashMap<>();
		data.put("facilityName", "   ");
		data.put("firstName", "   ");
		data.put("lastName", "   ");
		data.put("address1", "   ");
		data.put("city", "   ");
		data.put("email", "");
		enterData(data);
	}

	@When("User leaves optional fields blank")
	public void user_leaves_optional_fields_blank() {
		// Optional fields are not touched - intentionally left blank
	}

	@When("User enters Day Phone {string}")
	public void user_enters_day_phone(String phone) {
		page().enterDayPhone(phone);
	}

	@When("User enters discount {string} in the {string} field")
	public void user_enters_discount_in_the_field(String discount, String field) {
		page().enterDiscount(field, discount);
	}

	@When("User enters license Issue Date {string}")
	public void user_enters_license_issue_date(String date) {
		page().enterLicenseIssueDate(date);
	}

	@When("User enters license Expiry Date {string}")
	public void user_enters_license_expiry_date(String date) {
		page().enterLicenseExpiryDate(date);
	}

	// ---------------- Buttons ----------------

	@When("User clicks Continue")
	public void user_clicks_continue() {
		page().clickContinue();
	}

	@When("User clicks Create Location")
	public void user_clicks_create_location() {
		page().clickCreateLocationBtn();
	}

	@When("User double-clicks Create Location")
	public void user_double_clicks_create_location() {
		page().doubleClickCreateLocationBtn();
	}

	@When("User clicks Back to Edit")
	public void user_clicks_back_to_edit() {
		page().clickBackToEdit();
	}

	@When("User clicks Search Again")
	public void user_clicks_search_again() {
		page().clickSearchAgain();
	}

	// ---------------- Search / View / Modify ----------------

	@When("User searches using Facility Name {string}")
	public void user_searches_using_facility_name(String facilityName) {
		searchedFacility = facilityName;
		page().enterFacilityName(facilityName);
	}

	@When("User enters Facility Name {string}")
	public void user_enters_facility_name(String facilityName) {
		searchedFacility = facilityName;
		page().enterFacilityName(facilityName);
	}

	@When("User enters City {string}")
	public void user_enters_city(String city) {
		searchedCity = city;
		page().enterCity(city);
	}

	@When("User selects Governorate {string}")
	public void user_selects_governorate(String governorate) {
		page().selectGovernorate(governorate);
	}

	@When("User searches for Facility Name {string}")
	public void user_searches_for_facility_name(String facilityName) {
		searchedFacility = facilityName;
		page().openSearchLocation();
		page().enterFacilityName(facilityName);
		page().clickSearchAgain();
		Assert.assertFalse(page().getResultRows().isEmpty(), "No location found for: " + facilityName);
	}

	@When("User opens the matching location for modification")
	public void user_opens_the_matching_location_for_modification() {
		page().clickModify();
	}

	@When("User updates the City to {string}")
	public void user_updates_the_city_to(String city) {
		page().enterCity(city);
	}

	@When("User saves the location changes")
	public void user_saves_the_location_changes() {
		page().clickSave();
	}

	@When("User clicks View for the matching location")
	public void user_clicks_view_for_the_matching_location() {
		page().clickView();
	}

	@When("User opens the location using View")
	public void user_opens_the_location_using_view() {
		page().clickView();
	}

	// ---------------- Verifications ----------------

	@Then("Mandatory field validation messages should be displayed")
	public void mandatory_field_validation_messages_should_be_displayed() {
		Assert.assertTrue(page().isValidationDisplayed(), "Mandatory validation messages not displayed");
	}

	@Then("User should remain on Create Location page")
	public void user_should_remain_on_create_location_page() {
		Assert.assertTrue(page().isOnCreateLocationPage(), "User navigated away from Create Location page");
	}

	@Then("Email validation message should be displayed")
	public void email_validation_message_should_be_displayed() {
		Assert.assertTrue(page().isValidationDisplayed(), "Email validation message not displayed");
	}

	@Then("Confirmation page should display the entered location details")
	public void confirmation_page_should_display_the_entered_location_details() {
		Assert.assertTrue(page().isConfirmationPageDisplayed(), "Confirmation page not displayed");
		String text = page().getPageText();
		for (Map.Entry<String, String> entry : enteredData.entrySet()) {
			Assert.assertTrue(text.contains(entry.getValue()),
					entry.getKey() + " not shown on confirmation page: " + entry.getValue());
		}
	}

	@Then("Location should be created successfully")
	public void location_should_be_created_successfully() {
		Assert.assertTrue(page().getSuccessMessage().toLowerCase().contains("success"),
				"Location creation success message not displayed");
	}

	@Then("Location ID should be displayed")
	public void location_id_should_be_displayed() {
		Assert.assertTrue(page().getLocationIdText().matches("(?s).*Location ID.*\\d+.*"),
				"Location ID not displayed");
	}

	@Then("Previously entered location details should be retained")
	public void previously_entered_location_details_should_be_retained() {
		for (Map.Entry<String, String> entry : enteredData.entrySet()) {
			Assert.assertEquals(page().getFieldValue(entry.getKey()), entry.getValue(),
					entry.getKey() + " was not retained");
		}
	}

	@Then("Matching location records for {string} should be displayed")
	public void matching_location_records_for_should_be_displayed(String facilityName) {
		List<WebElement> rows = page().getResultRows();
		Assert.assertFalse(rows.isEmpty(), "No search results displayed");
		for (WebElement row : rows) {
			Assert.assertTrue(row.getText().contains(facilityName), "Non-matching row: " + row.getText());
		}
	}

	@Then("The updated City {string} should be displayed for the location")
	public void the_updated_city_should_be_displayed_for_the_location(String city) {
		Assert.assertTrue(page().getPageText().contains(city), "Updated city not displayed: " + city);
	}

	@Then("Search results should match the entered criteria")
	public void search_results_should_match_the_entered_criteria() {
		List<WebElement> rows = page().getResultRows();
		Assert.assertFalse(rows.isEmpty(), "No search results displayed");
		for (WebElement row : rows) {
			Assert.assertTrue(row.getText().contains(searchedFacility), "Facility mismatch: " + row.getText());
			Assert.assertTrue(row.getText().contains(searchedCity), "City mismatch: " + row.getText());
		}
	}

	@Then("No matching location records should be displayed")
	public void no_matching_location_records_should_be_displayed() {
		Assert.assertTrue(page().isNoRecordsDisplayed(), "Records displayed for non-existing facility");
	}

	@Then("Location ID, Facility Name, Status and address should be displayed correctly")
	public void location_id_facility_name_status_and_address_should_be_displayed_correctly() {
		String text = page().getPageText();
		Assert.assertTrue(text.contains("Location ID"), "Location ID not displayed");
		Assert.assertTrue(text.contains(searchedFacility), "Facility Name not displayed");
		Assert.assertTrue(text.contains("Status"), "Status not displayed");
		Assert.assertTrue(text.contains("Address"), "Address not displayed");
	}

	private void verifyTab(String tabName) {
		page().clickTab(tabName);
		Assert.assertFalse(page().getPageText().trim().isEmpty(), tabName + " tab has no information");
	}

	@Then("Location Details tab should display the saved location information")
	public void location_details_tab_should_display_the_saved_location_information() {
		verifyTab("Location Details");
		Assert.assertTrue(page().getPageText().contains(searchedFacility), "Facility Name not in Location Details tab");
	}

	@Then("Contact Person tab should display the saved contact information")
	public void contact_person_tab_should_display_the_saved_contact_information() {
		verifyTab("Contact Person");
	}

	@Then("Facility Licenses tab should display the saved license information")
	public void facility_licenses_tab_should_display_the_saved_license_information() {
		verifyTab("Facility Licenses");
	}

	@Then("Discounts tab should display the saved discount information")
	public void discounts_tab_should_display_the_saved_discount_information() {
		verifyTab("Discounts");
	}

	@Then("Duplicate facility should be rejected according to the application rule")
	public void duplicate_facility_should_be_rejected_according_to_the_application_rule() {
		Assert.assertTrue(page().isValidationDisplayed()
				|| page().getPageText().toLowerCase().contains("already exist"),
				"Duplicate facility was not rejected");
	}

	@Then("A second record with Facility Name {string} should not be created")
	public void a_second_record_with_facility_name_should_not_be_created(String facilityName) {
		Assert.assertTrue(searchCount(facilityName) <= 1, "Duplicate record created for: " + facilityName);
	}

	@Then("Phone number validation should be displayed")
	public void phone_number_validation_should_be_displayed() {
		Assert.assertTrue(page().isValidationDisplayed(), "Phone number validation not displayed");
	}

	@Then("Spaces-only values should fail mandatory validation")
	public void spaces_only_values_should_fail_mandatory_validation() {
		Assert.assertTrue(page().isValidationDisplayed(), "Spaces-only values were accepted");
		Assert.assertTrue(page().isOnCreateLocationPage(), "User navigated away from Create Location page");
	}

	@Then("Discount validation should be displayed")
	public void discount_validation_should_be_displayed() {
		Assert.assertTrue(page().isValidationDisplayed(), "Discount validation not displayed");
	}

	@Then("License date range validation should be displayed")
	public void license_date_range_validation_should_be_displayed() {
		Assert.assertTrue(page().isValidationDisplayed(), "License date range validation not displayed");
	}

	@Then("Only one location record should exist with Facility Name {string}")
	public void only_one_location_record_should_exist_with_facility_name(String facilityName) {
		int after = searchCount(facilityName);
		Assert.assertTrue(after - Math.max(recordCountBefore, 0) <= 1,
				"Duplicate submission created more than one record for: " + facilityName);
	}
}
