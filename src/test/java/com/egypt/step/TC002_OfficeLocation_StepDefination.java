package com.egypt.step;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.egypt.page.OfficeLocationPage;
import com.egypt.utills.BaseClass;
import com.egypt.utills.ConfigReader;
import com.egypt.utills.TestDataGenerator;

import io.cucumber.java.en.*;

public class TC002_OfficeLocation_StepDefination extends BaseClass {

	private static final Pattern TOKEN = Pattern.compile("\\$\\{(random|created)\\.([^}]+)}");

	OfficeLocationPage officeLocationPage;

	// Data entered in the current scenario (used for later verifications)
	Map<String, String> enteredData = new HashMap<>();
	// ${random.xxx} values generated once per scenario so every step sees the same value
	Map<String, String> randomData = new HashMap<>();
	String createdFacility;
	String searchedFacility;
	String searchedCity;
	int recordCountBefore = -1;

	private OfficeLocationPage page() {
		if (officeLocationPage == null) {
			officeLocationPage = new OfficeLocationPage(driver);
		}
		return officeLocationPage;
	}

	// ---------------- Test data helpers ----------------

	private String random(String key) {
		return randomData.computeIfAbsent(key, k -> {
			switch (k) {
			case "facilityName": return TestDataGenerator.facilityName();
			case "firstName": return TestDataGenerator.firstName();
			case "lastName": return TestDataGenerator.lastName();
			case "address1": return TestDataGenerator.address1();
			case "dayPhone": return TestDataGenerator.egyptPhone();
			case "postalCode": return TestDataGenerator.postalCode();
			case "email": return TestDataGenerator.email(random("firstName"), random("lastName"));
			default: throw new IllegalArgumentException("Unknown random data key: " + k);
			}
		});
	}

	/** Resolves ${random.x}, ${created.facilityName} and ${config.key} placeholders. */
	private String val(String text) {
		if (text == null) {
			return null;
		}
		Matcher m = TOKEN.matcher(text);
		StringBuilder sb = new StringBuilder();
		while (m.find()) {
			String value = "random".equals(m.group(1)) ? random(m.group(2)) : createdFacility;
			Assert.assertNotNull(value, "No value available for " + m.group());
			m.appendReplacement(sb, Matcher.quoteReplacement(value));
		}
		m.appendTail(sb);
		return ConfigReader.resolve(sb.toString());
	}

	private Map<String, String> val(Map<String, String> data) {
		Map<String, String> resolved = new LinkedHashMap<>();
		data.forEach((k, v) -> resolved.put(k, val(v)));
		return resolved;
	}

	/** Realistic valid data (Faker) for every mandatory field. */
	private Map<String, String> validData(String facilityName) {
		Map<String, String> data = new LinkedHashMap<>();
		String first = TestDataGenerator.firstName();
		String last = TestDataGenerator.lastName();
		data.put("facilityName", facilityName);
		data.put("firstName", first);
		data.put("lastName", last);
		data.put("address1", TestDataGenerator.address1());
		data.put("city", ConfigReader.get("location.city"));
		data.put("governorate", ConfigReader.get("location.governorate"));
		data.put("dayPhone", TestDataGenerator.egyptPhone());
		data.put("email", TestDataGenerator.email(first, last));
		return data;
	}

	/** Valid data for every mandatory field except the given ones. */
	private Map<String, String> validDataExcept(String... skipKeys) {
		Map<String, String> data = validData(TestDataGenerator.facilityName());
		for (String key : skipKeys) {
			data.remove(key);
		}
		return data;
	}

	/** Table data from the feature file + Faker Day Phone / config Governorate (mandatory in the app). */
	private void enterData(Map<String, String> data) {
		enteredData = new LinkedHashMap<>(val(data));
		enteredData.putIfAbsent("dayPhone", TestDataGenerator.egyptPhone());
		enteredData.putIfAbsent("governorate", ConfigReader.get("location.governorate"));
		page().enterLocationDetails(enteredData);
	}

	private void search(String facilityName) {
		page().openSearchLocation();
		page().enterSearchFacilityName(facilityName);
		page().clickSearchAgain();
	}

	private int searchCount(String facilityName) {
		search(facilityName);
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
		enterData(validData(TestDataGenerator.facilityName()));
	}

	@When("User enters valid mandatory location details with Facility Name {string}")
	public void user_enters_valid_mandatory_location_details_with_facility_name(String facilityName) {
		String name = val(facilityName);
		recordCountBefore = searchCount(name);
		page().openCreateLocation();
		enterData(validData(name));
	}

	@When("User completes all other mandatory fields with valid data")
	public void user_completes_all_other_mandatory_fields_with_valid_data() {
		// Day Phone was already entered by the previous step - do not overwrite it
		page().enterLocationDetails(validDataExcept("dayPhone"));
	}

	@When("User completes remaining mandatory fields with valid data")
	public void user_completes_remaining_mandatory_fields_with_valid_data() {
		// Text fields already contain spaces; fill only the non-text mandatory fields
		Map<String, String> data = new LinkedHashMap<>();
		data.put("dayPhone", TestDataGenerator.egyptPhone());
		data.put("governorate", ConfigReader.get("location.governorate"));
		page().enterLocationDetails(data);
	}

	@When("User completes all mandatory fields with valid data")
	public void user_completes_all_mandatory_fields_with_valid_data() {
		enterData(validData(TestDataGenerator.facilityName()));
	}

	@When("User enters spaces only in mandatory text fields")
	public void user_enters_spaces_only_in_mandatory_text_fields() {
		Map<String, String> data = new LinkedHashMap<>();
		data.put("facilityName", "   ");
		data.put("firstName", "   ");
		data.put("lastName", "   ");
		data.put("address1", "   ");
		data.put("city", "   ");
		enteredData = data;
		page().enterLocationDetails(data);
	}

	@When("User leaves optional fields blank")
	public void user_leaves_optional_fields_blank() {
		// Optional fields are not touched - intentionally left blank
	}

	@When("User enters Day Phone {string}")
	public void user_enters_day_phone(String phone) {
		page().enterDayPhone(val(phone));
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

	@When("User creates a new Office Location")
	public void user_creates_a_new_office_location() {
		page().openCreateLocation();
		createdFacility = TestDataGenerator.facilityName();
		enterData(validData(createdFacility));
		page().clickContinue();
		Assert.assertTrue(page().isConfirmationPageDisplayed(), "Confirm page not displayed while creating test data");
		page().clickCreateLocationBtn();
		Assert.assertTrue(page().isSuccessDisplayed(), "Test data location was not created: " + createdFacility);
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
		searchedFacility = val(facilityName);
		page().enterSearchFacilityName(searchedFacility);
	}

	@When("User enters Facility Name {string}")
	public void user_enters_facility_name(String facilityName) {
		searchedFacility = val(facilityName);
		page().enterSearchFacilityName(searchedFacility);
	}

	@When("User enters City {string}")
	public void user_enters_city(String city) {
		searchedCity = val(city);
		page().enterSearchCity(searchedCity);
	}

	@When("User selects Governorate {string}")
	public void user_selects_governorate(String governorate) {
		page().selectSearchGovernorate(val(governorate));
	}

	@When("User searches for Facility Name {string}")
	public void user_searches_for_facility_name(String facilityName) {
		searchedFacility = val(facilityName);
		search(searchedFacility);
		Assert.assertFalse(page().getResultRows().isEmpty(), "No location found for: " + searchedFacility);
	}

	@When("User opens the matching location for modification")
	public void user_opens_the_matching_location_for_modification() {
		page().clickEdit();
	}

	@When("User updates the City to {string}")
	public void user_updates_the_city_to(String city) {
		page().enterCity(val(city));
	}

	@When("User saves the location changes")
	public void user_saves_the_location_changes() {
		page().clickSubmit();
		Assert.assertFalse(page().isValidationDisplayed(),
				"Modify could not be saved - validation shown: Day Phone='" + page().getFieldValue("dayPhone")
						+ "' error='" + page().getFieldError("dayPhone") + "'");
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
		Assert.assertTrue(page().isOnCreateLocationPage(),
				"User navigated away from Create Location page. Active step: " + page().getActiveStep());
	}

	@Then("Email validation message should be displayed")
	public void email_validation_message_should_be_displayed() {
		Assert.assertTrue(page().isFieldErrorDisplayed("email"), "Email validation message not displayed");
	}

	@Then("Confirmation page should display the entered location details")
	public void confirmation_page_should_display_the_entered_location_details() {
		Assert.assertTrue(page().isConfirmationPageDisplayed(), "Confirmation page not displayed");
		for (String key : List.of("facilityName", "firstName", "lastName", "address1", "city", "email")) {
			if (enteredData.containsKey(key)) {
				Assert.assertEquals(page().getFieldValue(key), enteredData.get(key), key + " mismatch on Confirm page");
			}
		}
	}

	@Then("Location should be created successfully")
	public void location_should_be_created_successfully() {
		Assert.assertTrue(page().isSuccessDisplayed(),
				"Location was not created. Page messages: " + page().getAlertMessages());
	}

	@Then("Location ID should be displayed")
	public void location_id_should_be_displayed() {
		String text = page().getLocationIdText();
		Assert.assertTrue(text.matches("Location ID:\\s*\\d+.*"), "Location ID not displayed: " + text);
		if (enteredData.containsKey("facilityName")) {
			Assert.assertTrue(text.contains(enteredData.get("facilityName")), "Location ID text has wrong facility: " + text);
		}
	}

	@Then("Previously entered location details should be retained")
	public void previously_entered_location_details_should_be_retained() {
		for (String key : List.of("facilityName", "firstName", "lastName", "address1", "city", "email")) {
			Assert.assertEquals(page().getFieldValue(key), enteredData.get(key), key + " was not retained");
		}
	}

	@Then("Matching location records for {string} should be displayed")
	public void matching_location_records_for_should_be_displayed(String facilityName) {
		String name = val(facilityName);
		List<WebElement> rows = page().getResultRows();
		Assert.assertFalse(rows.isEmpty(), "No search results displayed for: " + name);
		for (WebElement row : rows) {
			Assert.assertTrue(row.getText().contains(name), "Non-matching row: " + row.getText());
		}
	}

	@Then("The updated City {string} should be displayed for the location")
	public void the_updated_city_should_be_displayed_for_the_location(String city) {
		String expected = val(city);
		search(searchedFacility);
		List<WebElement> rows = page().getResultRows();
		Assert.assertFalse(rows.isEmpty(), "Modified location not found: " + searchedFacility);
		Assert.assertTrue(rows.get(0).getText().contains(expected),
				"Updated city not saved. Row: " + rows.get(0).getText());
	}

	@Then("Search results should match the entered criteria")
	public void search_results_should_match_the_entered_criteria() {
		List<WebElement> rows = page().getResultRows();
		Assert.assertFalse(rows.isEmpty(), "No search results for Facility='" + searchedFacility + "', City='"
				+ searchedCity + "' + Governorate filter (same record is found when searching by Facility Name only)");
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
		Assert.assertTrue(page().isViewPageDisplayed(), "View page not displayed");
		String text = page().getPageText();
		Assert.assertTrue(text.matches("(?s).*Location ID\\s*\\d+.*"), "Location ID not displayed");
		Assert.assertTrue(text.contains(searchedFacility), "Facility Name not displayed");
		Assert.assertTrue(text.matches("(?s).*Status\\s*(Active|Inactive).*"), "Status not displayed");
		Assert.assertTrue(text.contains("Address 1"), "Address not displayed");
	}

	private String verifyTab(String tabName) {
		page().clickTab(tabName);
		String text = page().getActiveTabText();
		Assert.assertFalse(text.trim().isEmpty(), tabName + " tab has no information");
		return text;
	}

	@Then("Location Details tab should display the saved location information")
	public void location_details_tab_should_display_the_saved_location_information() {
		String text = verifyTab("Location Details");
		Assert.assertTrue(text.contains(searchedFacility), "Facility Name not in Location Details tab");
	}

	@Then("Contact Person tab should display the saved contact information")
	public void contact_person_tab_should_display_the_saved_contact_information() {
		String text = verifyTab("Contact Person");
		Assert.assertTrue(text.contains("First Name") && text.contains("Last Name"), "Contact details not shown");
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
		boolean created = page().isSuccessDisplayed();
		Assert.assertFalse(created, "Duplicate facility '" + enteredData.get("facilityName") + "' was created");
	}

	@Then("A second record with Facility Name {string} should not be created")
	public void a_second_record_with_facility_name_should_not_be_created(String facilityName) {
		String name = val(facilityName);
		Assert.assertTrue(searchCount(name) <= 1, "Duplicate record created for: " + name);
	}

	@Then("Phone number validation should be displayed")
	public void phone_number_validation_should_be_displayed() {
		Assert.assertTrue(page().isFieldErrorDisplayed("dayPhone"), "Phone number validation not displayed");
	}

	@Then("Spaces-only values should fail mandatory validation")
	public void spaces_only_values_should_fail_mandatory_validation() {
		Assert.assertTrue(page().isValidationDisplayed(), "Spaces-only values were accepted");
		Assert.assertTrue(page().isOnCreateLocationPage(), "User navigated away from Create Location page");
	}

	@Then("Discount validation should be displayed")
	public void discount_validation_should_be_displayed() {
		String local = page().getFieldValue("localMedicineDiscount");
		String imported = page().getFieldValue("importedMedicineDiscount");
		boolean errorShown = page().isFieldErrorDisplayed("localMedicineDiscount")
				|| page().isFieldErrorDisplayed("importedMedicineDiscount");
		boolean stillOnForm = page().isOnCreateLocationPage();
		Assert.assertTrue(errorShown && stillOnForm,
				"Invalid discount was accepted without validation (Local='" + local + "', Imported='" + imported
						+ "', active step='" + page().getActiveStep() + "')");
	}

	@Then("Non-numeric discount should be rejected")
	public void non_numeric_discount_should_be_rejected() {
		String local = page().getFieldValue("localMedicineDiscount");
		String imported = page().getFieldValue("importedMedicineDiscount");
		boolean blockedByInput = local.matches("\\d*(\\.\\d+)?") && imported.matches("\\d*(\\.\\d+)?");
		boolean errorShown = page().isFieldErrorDisplayed("localMedicineDiscount")
				|| page().isFieldErrorDisplayed("importedMedicineDiscount");
		Assert.assertTrue(blockedByInput || errorShown,
				"Non-numeric discount accepted (Local='" + local + "', Imported='" + imported + "')");
	}

	@Then("License date range validation should be displayed")
	public void license_date_range_validation_should_be_displayed() {
		Assert.assertTrue(page().isValidationDisplayed(), "License date range validation not displayed");
	}

	@Then("Only one location record should exist with Facility Name {string}")
	public void only_one_location_record_should_exist_with_facility_name(String facilityName) {
		String name = val(facilityName);
		int after = searchCount(name);
		Assert.assertTrue(after - Math.max(recordCountBefore, 0) <= 1,
				"Duplicate submission created more than one record for: " + name);
	}

	// ==================== Additional negative / boundary scenarios ====================

	@When("User enters valid data in all mandatory fields except {string}")
	public void user_enters_valid_data_in_all_mandatory_fields_except(String field) {
		Map<String, String> data = validDataExcept(field);
		enteredData = data;
		page().enterLocationDetails(data);
	}

	@When("User enters Email {string}")
	public void user_enters_email(String value) {
		page().enterEmail(value);
	}

	@When("User enters Postal Code {string}")
	public void user_enters_postal_code(String value) {
		page().enterPostalCode(value);
	}

	@When("User enters Facility Name value {string}")
	public void user_enters_facility_name_value(String value) {
		page().enterFacilityName(value);
	}

	@When("User enters {int} characters in {string}")
	public void user_enters_characters_in(int length, String field) {
		String chars = field.equals("postalCode") ? "1234567890" : "ABCDEFGHIJ";
		StringBuilder value = new StringBuilder();
		while (value.length() < length) {
			value.append(chars);
		}
		page().enterField(field, value.substring(0, length));
	}

	@When("User clicks Clear")
	public void user_clicks_clear() {
		page().clickClear();
	}

	@When("User clicks Back to Search")
	public void user_clicks_back_to_search() {
		page().clickBackToSearch();
	}

	@Then("Field validation should be displayed for {string}")
	public void field_validation_should_be_displayed_for(String field) {
		Assert.assertTrue(page().isFieldErrorDisplayed(field), "No validation message displayed for field: " + field);
	}

	@Then("No validation error should be displayed for {string}")
	public void no_validation_error_should_be_displayed_for(String field) {
		Assert.assertFalse(page().isFieldErrorDisplayed(field),
				"Unexpected validation for " + field + ": " + page().getFieldError(field));
	}

	@Then("{string} should contain at most {int} characters")
	public void should_contain_at_most_characters(String field, int maxLength) {
		int actual = page().getFieldValue(field).length();
		Assert.assertTrue(actual <= maxLength,
				field + " accepted " + actual + " characters, maximum allowed is " + maxLength);
	}

	@Then("Postal Code should be rejected")
	public void postal_code_should_be_rejected() {
		String value = page().getFieldValue("postalCode");
		boolean blockedByInput = !value.matches(".*\\D.*");
		Assert.assertTrue(blockedByInput || page().isFieldErrorDisplayed("postalCode"),
				"Non-numeric Postal Code was accepted: " + value);
	}

	@Then("All mandatory text fields should be empty")
	public void all_mandatory_text_fields_should_be_empty() {
		for (String field : List.of("facilityName", "firstName", "lastName", "address1", "city")) {
			Assert.assertEquals(page().getFieldValue(field), "", field + " was not cleared");
		}
		Assert.assertEquals(page().getFieldValue("dayPhone").replace("+20", "").trim(), "", "Day Phone was not cleared");
		Assert.assertEquals(page().getSelectedGovernorate(), "", "Governorate was not cleared");
	}

	@Then("{string} field should be read-only")
	public void field_should_be_read_only(String field) {
		Assert.assertTrue(page().isReadOnly(field), field + " field is editable");
	}

	@Then("Office Location search page should be displayed")
	public void office_location_search_page_should_be_displayed() {
		Assert.assertTrue(page().isSearchPageDisplayed(), "Search page not displayed. URL: " + getApplnUrl());
	}

	@Then("No browser alert should be triggered")
	public void no_browser_alert_should_be_triggered() {
		boolean alert = page().isAlertPresent();
		if (alert) {
			alertDriverAccept();
		}
		Assert.assertFalse(alert, "Script in Facility Name was executed (alert shown)");
	}

	@Then("Facility Name input should be either rejected or shown as plain text {string}")
	public void facility_name_input_should_be_either_rejected_or_shown_as_plain_text(String value) {
		boolean rejected = page().isFieldErrorDisplayed("facilityName");
		boolean shownAsText = page().getFieldValue("facilityName").equals(value) || page().getPageText().contains(value);
		Assert.assertTrue(rejected || shownAsText, "Script input was neither rejected nor displayed as plain text");
	}
}
