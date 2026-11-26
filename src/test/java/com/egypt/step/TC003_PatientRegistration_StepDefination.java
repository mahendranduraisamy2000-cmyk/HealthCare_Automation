package com.egypt.step;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.egypt.page.PatientRegistrationPage;
import com.egypt.utills.BaseClass;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

public class TC003_PatientRegistration_StepDefination extends BaseClass {

	PatientRegistrationPage patientRegPage;
	Map<String, String> enteredOptionalDemographics;

	private PatientRegistrationPage page() {
		if (patientRegPage == null) {
			patientRegPage = new PatientRegistrationPage(driver);
		}
		return patientRegPage;
	}

	// ---------- Search Patient (/patient/search-register) ----------
	@When("User navigates to Patient Registration page")
	public void user_navigates_to_patient_registration_page() {
		patientRegPage = new PatientRegistrationPage(driver);
		patientRegPage.navigateToPatientRegistration();
	}

	@Then("all Search Patient page elements should be displayed")
	public void all_search_patient_page_elements_should_be_displayed() {
		Assert.assertTrue(
			page().areSearchPatientElementsDisplayed(),
			"Expected all Search Patient page fields and buttons to be displayed."
		);
		Assert.assertTrue(
			getApplnUrl().contains("/patient/search-register"),
			"Expected URL to contain /patient/search-register, found: " + getApplnUrl()
		);
	}

	@When("User clicks Search on Search Patient page")
	public void user_clicks_search_on_search_patient_page() {
		page().clickSearch();
	}

	@Then("Search Patient warning message {string} should be displayed")
	public void search_patient_warning_message_should_be_displayed(String expectedMessage) {
		String actualMessage = page().getToastMessageText();
		Assert.assertTrue(
			actualMessage.contains(expectedMessage),
			"Expected warning '" + expectedMessage + "', found: " + actualMessage
		);
	}

	@When("User enters search NID {string}")
	public void user_enters_search_nid(String nid) {
		page().enterSearchNid(nid);
	}

	@When("User enters search Phone {string}")
	public void user_enters_search_phone(String phone) {
		page().enterSearchPhone(phone);
	}

	@Then("Search Patient field validation error should be displayed")
	public void search_patient_field_validation_error_should_be_displayed() {
		List<String> errors = page().getFormValidationMessages();
		Assert.assertFalse(errors.isEmpty(), "Expected at least one validation error on Search Patient form.");
	}

	@When("User enters Search Patient criteria {string}, {string}, {string}, and {string}")
	public void user_enters_search_patient_criteria(String pid, String firstName, String lastName, String username) {
		page().enterSearchPatientId(pid);
		page().enterSearchFirstName(firstName);
		page().enterSearchLastName(lastName);
		page().enterSearchUsername(username);
	}

	@When("User clicks Clear on Search Patient page")
	public void user_clicks_clear_on_search_patient_page() {
		page().clickClear();
	}

	@Then("all Search Patient fields should be cleared")
	public void all_search_patient_fields_should_be_cleared() {
		Assert.assertEquals(page().getSearchFieldValue("pid"), "");
		Assert.assertEquals(page().getSearchFieldValue("pfnm"), "");
		Assert.assertEquals(page().getSearchFieldValue("plnm"), "");
		Assert.assertEquals(page().getSearchFieldValue("username"), "");
	}

	@When("User selects Sports Type {string} on Search Patient page")
	public void user_selects_sports_type_on_search_patient_page(String sport) {
		page().selectSportsType(sport);
	}

	@When("User selects Club \\/ Team Name {string} on Search Patient page")
	public void user_selects_club_team_name_on_search_patient_page(String club) {
		page().selectClubTeamName(club);
	}

	@Then("Sports Type {string} and Club \\/ Team Name {string} should be selected")
	public void sports_type_and_club_team_name_should_be_selected(String expectedSport, String expectedClub) {
		Assert.assertTrue(
			page().getSelectedSportsType().contains(expectedSport),
			"Expected Sports Type '" + expectedSport + "', found: " + page().getSelectedSportsType()
		);
		Assert.assertTrue(
			page().getSelectedClubTeamName().contains(expectedClub),
			"Expected Club '" + expectedClub + "', found: " + page().getSelectedClubTeamName()
		);
	}

	@When("User enters search First Name {string}")
	public void user_enters_search_first_name(String firstName) {
		page().enterSearchFirstName(firstName);
	}

	@Then("Search Patient no records message {string} should be displayed")
	public void search_patient_no_records_message_should_be_displayed(String expectedMsg) {
		String actualMsg = page().getNoRecordsMessageText();
		Assert.assertTrue(
			actualMsg.contains(expectedMsg),
			"Expected no-records message '" + expectedMsg + "', found: " + actualMsg
		);
	}

	@When("User clicks Go Back on Search Patient page")
	public void user_clicks_go_back_on_search_patient_page() {
		page().clickGoBack();
	}

	@Then("User should leave the Search Patient page")
	public void user_should_leave_the_search_patient_page() {
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.not(ExpectedConditions.urlContains("/patient/search-register")));
		Assert.assertFalse(
			getApplnUrl().contains("/patient/search-register"),
			"Expected user to leave /patient/search-register, found: " + getApplnUrl()
		);
	}

	// ---------- New Patient Registration (/patient/registration) ----------
	@When("User clicks New patient Registration button")
	public void user_clicks_new_patient_registration_button() {
		page().clickNewPatientRegistration();
	}

	@Then("all New Patient Registration form elements should be displayed with default selections")
	public void all_new_patient_registration_form_elements_should_be_displayed_with_default_selections() {
		Assert.assertTrue(
			getApplnUrl().contains("/patient/registration"),
			"Expected URL to contain /patient/registration, found: " + getApplnUrl()
		);
		Assert.assertTrue(
			page().areNewPatientRegistrationElementsDisplayed(),
			"Expected all New Patient Registration form elements to be displayed."
		);
		Assert.assertTrue(
			page().areDefaultSelectionsValid(),
			"Expected default Prefix (Mr), Sex (Male), WhatsApp (Yes), Insured (Self), and VIP (No) selections."
		);
	}

	@When("User clicks Submit on Patient Registration form")
	public void user_clicks_submit_on_patient_registration_form() {
		page().clickSubmit();
	}

	@Then("mandatory validation messages should appear on Patient Registration form")
	public void mandatory_validation_messages_should_appear_on_patient_registration_form() {
		List<String> errors = page().getFormValidationMessages();
		Assert.assertTrue(errors.contains("Please specify First Name"), "Missing First Name error. Found: " + errors);
		Assert.assertTrue(errors.contains("Please specify Last Name"), "Missing Last Name error. Found: " + errors);
		Assert.assertTrue(errors.contains("Specify DOB"), "Missing DOB error. Found: " + errors);
		Assert.assertTrue(errors.contains("Please specify User Name"), "Missing Username error. Found: " + errors);
		Assert.assertTrue(errors.contains("Password is required"), "Missing Password error. Found: " + errors);
		Assert.assertTrue(errors.contains("Location is required"), "Missing Location error. Found: " + errors);
		Assert.assertTrue(errors.contains("Physician is required"), "Missing Physician error. Found: " + errors);
	}

	@When("User enters Patient Date of Birth {string}")
	public void user_enters_patient_date_of_birth(String dob) {
		page().enterRegDob(dob);
	}

	@Then("Patient Age field should be disabled and auto-calculated")
	public void patient_age_field_should_be_disabled_and_auto_calculated() {
		Assert.assertTrue(page().isAgeFieldDisabled(), "Expected Age field to be disabled.");
		String age = page().getCalculatedAge();
		Assert.assertFalse(age == null || age.trim().isEmpty(), "Expected Age to be auto-calculated.");
		Assert.assertTrue(Integer.parseInt(age.trim()) > 0, "Expected positive Age value, found: " + age);
	}

	@When("User enters Patient Username {string} and Password {string}")
	public void user_enters_patient_username_and_password(String username, String password) {
		page().enterRegUsername(username);
		page().enterRegPassword(password);
	}

	@Then("Patient Registration validation messages {string} and {string} should be displayed")
	public void patient_registration_validation_messages_should_be_displayed(String expectedErr1, String expectedErr2) {
		List<String> errors = page().getFormValidationMessages();
		Assert.assertTrue(errors.contains(expectedErr1), "Missing '" + expectedErr1 + "'. Found: " + errors);
		Assert.assertTrue(errors.contains(expectedErr2), "Missing '" + expectedErr2 + "'. Found: " + errors);
	}

	@When("User enters Patient NID {string} and Cell Phone {string}")
	public void user_enters_patient_nid_and_cell_phone(String nid, String cellPhone) {
		if (nid.length() >= 14) {
			page().waitForCountryFormatReady();
		}
		page().enterRegNid(nid);
		page().enterRegCellPhone(cellPhone);
	}

	@Then("Patient Registration NID and Cell Phone validation errors should be displayed")
	public void patient_registration_nid_and_cell_phone_validation_errors_should_be_displayed() {
		List<String> errors = page().getFormValidationMessages();
		boolean hasNidError = errors.stream().anyMatch(e -> e.toLowerCase().contains("nid") || e.toLowerCase().contains("id") || e.toLowerCase().contains("digit"));
		boolean hasPhoneError = errors.stream().anyMatch(e -> e.toLowerCase().contains("cell") || e.toLowerCase().contains("phone") || e.toLowerCase().contains("digit"));
		Assert.assertTrue(hasNidError, "Expected NID format error. Found: " + errors);
		Assert.assertTrue(hasPhoneError, "Expected Cell Phone format error. Found: " + errors);
	}

	@When("User enters Patient basic details {string}, {string}, {string}, and {string}")
	public void user_enters_patient_basic_details(String firstName, String lastName, String username, String email) {
		page().enterRegFirstName(firstName);
		page().enterRegLastName(lastName);
		page().enterRegUsername(username);
		page().enterRegEmail(email);
	}

	@When("User clicks Clear on Patient Registration form")
	public void user_clicks_clear_on_patient_registration_form() {
		page().clickClear();
	}

	@Then("New Patient Registration fields should be cleared")
	public void new_patient_registration_fields_should_be_cleared() {
		Assert.assertEquals(page().getRegFieldValue("firstName"), "");
		Assert.assertEquals(page().getRegFieldValue("lastName"), "");
		Assert.assertEquals(page().getRegFieldValue("username"), "");
		Assert.assertEquals(page().getRegFieldValue("email"), "");
	}

	@When("User clicks Cancel on Patient Registration form")
	public void user_clicks_cancel_on_patient_registration_form() {
		page().clickCancel();
	}

	@Then("User should navigate to Patient Search page {string}")
	public void user_should_navigate_to_patient_search_page(String expectedPath) {
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.urlContains(expectedPath));
		Assert.assertTrue(
			getApplnUrl().contains(expectedPath),
			"Expected URL to contain '" + expectedPath + "', found: " + getApplnUrl()
		);
	}

	@When("User fills valid mandatory Patient Registration details:")
	public void user_fills_valid_mandatory_patient_registration_details(DataTable dataTable) {
		Map<String, String> data = dataTable.asMap(String.class, String.class);
		String uniqueSuffix = String.valueOf(System.currentTimeMillis() % 1000000L);
		String uniqueNid = "29901011" + String.format("%06d", Long.parseLong(uniqueSuffix));
		String uniqueUsername = "pat" + uniqueSuffix;

		page().waitForCountryFormatReady();
		page().enterRegFirstName(data.get("firstName"));
		page().enterRegLastName(data.get("lastName"));
		page().enterRegDob(data.get("dob"));
		page().enterRegNid(uniqueNid);
		page().enterRegCellPhone(data.get("cellPhone"));
		page().enterRegUsername(uniqueUsername);
		page().enterRegPassword(data.get("password"));
		if (data.containsKey("email")) {
			page().enterRegEmail(data.get("email"));
		}
	}

	@When("User selects first available Location and Physician on Patient Registration form")
	public void user_selects_first_available_location_and_physician() {
		page().selectFirstAvailableLocationAndPhysician();
	}

	@Then("Patient should be registered successfully with a generated Patient ID")
	public void patient_should_be_registered_successfully_with_a_generated_patient_id() {
		String successText = page().getRegistrationSuccessText();
		Assert.assertTrue(
			successText.contains("Information Saved Successfully"),
			"Expected 'Information Saved Successfully', found: " + successText
		);
		Assert.assertTrue(
			successText.contains("Patient ID:"),
			"Expected generated Patient ID in success banner, found: " + successText
		);
	}

	// ---------- Select Patient (/patient/search) ----------
	@When("User navigates to Select Patient page")
	public void user_navigates_to_select_patient_page() {
		patientRegPage = new PatientRegistrationPage(driver);
		patientRegPage.navigateToSelectPatient();
	}

	@Then("all Select Patient page elements should be displayed with default selections")
	public void all_select_patient_page_elements_should_be_displayed_with_default_selections() {
		Assert.assertTrue(
			getApplnUrl().contains("/patient/search"),
			"Expected URL to contain /patient/search, found: " + getApplnUrl()
		);
		Assert.assertTrue(
			page().areSelectPatientElementsDisplayed(),
			"Expected all Select Patient page elements to be displayed."
		);
		Assert.assertTrue(
			page().areSelectPatientDefaultsValid(),
			"Expected default Order By (Last Name), Select By (Regular Search), and disabled Club dropdown."
		);
	}

	@When("User enters Select Patient National ID {string}")
	public void user_enters_select_patient_national_id(String nid) {
		page().enterSearchNid(nid);
	}

	@When("User clicks Search on Select Patient page")
	public void user_clicks_search_on_select_patient_page() {
		page().clickSearch();
	}

	@When("User enters Select Patient criteria {string}, {string}, {string}, {string}, and {string}")
	public void user_enters_select_patient_criteria(String pid, String firstName, String lastName, String username, String medNo) {
		page().enterSearchPatientId(pid);
		page().enterSelectPatientFirstName(firstName);
		page().enterSelectPatientLastName(lastName);
		page().enterSelectPatientUsername(username);
		page().enterSelectPatientMedNo(medNo);
	}

	@When("User clicks Clear on Select Patient page")
	public void user_clicks_clear_on_select_patient_page() {
		page().clickClear();
	}

	@Then("all Select Patient fields should be cleared")
	public void all_select_patient_fields_should_be_cleared() {
		Assert.assertEquals(page().getSelectPatientFieldValue("pid"), "");
		Assert.assertEquals(page().getSelectPatientFieldValue("firstName"), "");
		Assert.assertEquals(page().getSelectPatientFieldValue("lastName"), "");
		Assert.assertEquals(page().getSelectPatientFieldValue("usrname"), "");
		Assert.assertEquals(page().getSelectPatientFieldValue("medno"), "");
	}

	@When("User selects Order By {string} on Select Patient page")
	public void user_selects_order_by_on_select_patient_page(String orderBy) {
		page().selectOrderBy(orderBy);
	}

	@Then("Order By {string} should be selected on Select Patient page")
	public void order_by_should_be_selected_on_select_patient_page(String expectedOrderBy) {
		Assert.assertTrue(
			page().getSelectedOrderBy().contains(expectedOrderBy),
			"Expected Order By '" + expectedOrderBy + "', found: " + page().getSelectedOrderBy()
		);
	}

	@Then("Club \\/ Team Name dropdown should be disabled by default on Select Patient page")
	public void club_team_name_dropdown_should_be_disabled_by_default_on_select_patient_page() {
		Assert.assertTrue(
			page().isSelectPatientClubDisabled(),
			"Expected Club / Team Name dropdown to be disabled before Sports Type is chosen."
		);
	}

	@When("User selects Sports Type {string} on Select Patient page")
	public void user_selects_sports_type_on_select_patient_page(String sport) {
		page().selectSportsType(sport);
	}

	@When("User selects Club \\/ Team Name {string} on Select Patient page")
	public void user_selects_club_team_name_on_select_patient_page(String club) {
		page().selectPatientClubTeamName(club);
	}

	@Then("Select Patient Sports Type {string} and Club \\/ Team Name {string} should be selected")
	public void select_patient_sports_type_and_club_team_name_should_be_selected(String expectedSport, String expectedClub) {
		Assert.assertTrue(
			page().getSelectedSportsType().contains(expectedSport),
			"Expected Sports Type '" + expectedSport + "', found: " + page().getSelectedSportsType()
		);
		Assert.assertTrue(
			page().getSelectedPatientClubTeamName().contains(expectedClub),
			"Expected Club '" + expectedClub + "', found: " + page().getSelectedPatientClubTeamName()
		);
	}

	@Then("Select By {string} should be selected by default")
	public void select_by_should_be_selected_by_default(String expectedOption) {
		Assert.assertTrue(
			page().isSelectBySelected(expectedOption),
			"Expected Select By '" + expectedOption + "' to be selected by default."
		);
	}

	@When("User selects Select By {string} on Select Patient page")
	public void user_selects_select_by_on_select_patient_page(String option) {
		page().selectSelectByOption(option);
	}

	@Then("Select By {string} should be selected")
	public void select_by_should_be_selected(String expectedOption) {
		Assert.assertTrue(
			page().isSelectBySelected(expectedOption),
			"Expected Select By '" + expectedOption + "' to be selected."
		);
	}

	@When("User enters Select Patient First Name {string}")
	public void user_enters_select_patient_first_name(String firstName) {
		page().enterSelectPatientFirstName(firstName);
	}

	@Then("Select Patient search results should show {string}")
	public void select_patient_search_results_should_show(String expectedMsg) {
		String actualMsg = page().getSelectPatientNoResultsText();
		Assert.assertTrue(
			actualMsg.contains(expectedMsg),
			"Expected '" + expectedMsg + "', found: " + actualMsg
		);
	}

	@When("User clicks New patient button on Select Patient page")
	public void user_clicks_new_patient_button_on_select_patient_page() {
		page().clickNewPatientButton();
	}

	@When("User clicks Cancel on Select Patient page")
	public void user_clicks_cancel_on_select_patient_page() {
		page().clickCancel();
	}

	@Then("User should leave the Select Patient page")
	public void user_should_leave_the_select_patient_page() {
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.not(ExpectedConditions.urlContains("/patient/search")));
		Assert.assertFalse(
			getApplnUrl().contains("/patient/search"),
			"Expected user to leave /patient/search, found: " + getApplnUrl()
		);
	}

	// ---------- Additional Patient Registration (/patient/registration) Steps ----------
	@When("User enters Patient Registration password {string}")
	public void user_enters_patient_registration_password(String password) {
		page().enterRegPassword(password);
	}

	@When("User clicks Patient Registration password visibility icon")
	public void user_clicks_patient_registration_password_visibility_icon() {
		page().clickRegPasswordVisibilityIcon();
	}

	@When("User clicks Patient Registration password visibility icon again")
	public void user_clicks_patient_registration_password_visibility_icon_again() {
		page().clickRegPasswordVisibilityIcon();
	}

	@Then("Patient Registration password should be visible")
	public void patient_registration_password_should_be_visible() {
		Assert.assertEquals(page().getRegPasswordInputType(), "text");
	}

	@Then("Patient Registration password should be hidden")
	public void patient_registration_password_should_be_hidden() {
		Assert.assertEquals(page().getRegPasswordInputType(), "password");
	}

	@Then("Practice field should be read-only with value {string}")
	public void practice_field_should_be_read_only_with_value(String expectedPractice) {
		Assert.assertEquals(page().getPracticeFieldValue(), expectedPractice);
		Assert.assertTrue(page().isPracticeFieldReadOnly(), "Expected Practice field to be read-only.");
	}

	@Then("Physician dropdown should be disabled before Location is selected")
	public void physician_dropdown_should_be_disabled_before_location_is_selected() {
		Assert.assertTrue(
			page().isPhysicianDropdownDisabled(),
			"Expected Physician/Provider dropdown to be disabled before selecting Location."
		);
	}

	@When("User selects first available Location on Patient Registration form")
	public void user_selects_first_available_location_on_patient_registration_form() {
		page().selectFirstAvailableLocation();
	}

	@Then("Physician dropdown should become enabled on Patient Registration form")
	public void physician_dropdown_should_become_enabled_on_patient_registration_form() {
		new WebDriverWait(driver, Duration.ofSeconds(20))
			.until(d -> !page().isPhysicianDropdownDisabled());
		Assert.assertFalse(
			page().isPhysicianDropdownDisabled(),
			"Expected Physician/Provider dropdown to become enabled after selecting Location."
		);
	}

	@When("User selects Prefix {string}, Suffix {string}, and Sex {string} on Patient Registration form")
	public void user_selects_prefix_suffix_and_sex(String prefix, String suffix, String sex) {
		page().selectPrefix(prefix);
		page().selectSuffix(suffix);
		page().selectSex(sex);
	}

	@Then("Prefix {string}, Suffix {string}, and Sex {string} should be selected")
	public void prefix_suffix_and_sex_should_be_selected(String prefix, String suffix, String sex) {
		Assert.assertTrue(page().getSelectedPrefix().contains(prefix), "Expected Prefix " + prefix + ", found: " + page().getSelectedPrefix());
		Assert.assertTrue(page().getSelectedSuffix().contains(suffix), "Expected Suffix " + suffix + ", found: " + page().getSelectedSuffix());
		Assert.assertTrue(page().getSelectedSex().contains(sex), "Expected Sex " + sex + ", found: " + page().getSelectedSex());
	}

	@Then("Patient NID should be formatted as {string} and Cell Phone as {string}")
	public void patient_nid_and_cell_phone_should_be_formatted(String expectedNid, String expectedCell) {
		Assert.assertEquals(page().getRegNidValue(), expectedNid);
		Assert.assertEquals(page().getRegCellPhoneValue(), expectedCell);
	}

	@When("User sets WhatsApp to {string}, Insured to {string}, and VIP to {string}")
	public void user_sets_whatsapp_insured_and_vip(String whatsapp, String insured, String vip) {
		page().selectWhatsAppOption(whatsapp);
		page().selectInsuredOption(insured);
		page().selectVipOption(vip);
	}

	@Then("WhatsApp {string}, Insured {string}, and VIP {string} should be selected")
	public void whatsapp_insured_and_vip_should_be_selected(String whatsapp, String insured, String vip) {
		Assert.assertTrue(page().isWhatsAppOptionSelected(whatsapp), "Expected WhatsApp=" + whatsapp);
		Assert.assertTrue(page().isInsuredOptionSelected(insured), "Expected Insured=" + insured);
		Assert.assertTrue(page().isVipOptionSelected(vip), "Expected VIP=" + vip);
	}

	@When("User clicks Back on Patient Registration form")
	public void user_clicks_back_on_patient_registration_form() {
		page().clickBack();
	}

	@When("User fills optional Patient Registration demographics:")
	public void user_fills_optional_patient_registration_demographics(DataTable dataTable) {
		enteredOptionalDemographics = dataTable.asMap(String.class, String.class);
		page().enterRegMiddleName(enteredOptionalDemographics.get("middleName"));
		page().enterRegFatherName(enteredOptionalDemographics.get("fatherName"));
		page().enterRegMotherName(enteredOptionalDemographics.get("motherName"));
		page().enterRegMedNo(enteredOptionalDemographics.get("medno"));
		page().enterRegAddress1(enteredOptionalDemographics.get("address1"));
		page().enterRegAddress2(enteredOptionalDemographics.get("address2"));
		page().enterRegCity(enteredOptionalDemographics.get("city"));
		page().selectRegGovernorate(enteredOptionalDemographics.get("governorate"));
		page().enterRegZip(enteredOptionalDemographics.get("zip"));
		page().enterRegExt(enteredOptionalDemographics.get("ext"));
	}

	@Then("optional Patient Registration demographics should retain entered values")
	public void optional_patient_registration_demographics_should_retain_entered_values() {
		for (Map.Entry<String, String> entry : enteredOptionalDemographics.entrySet()) {
			String actual = page().getOptionalDemographicValue(entry.getKey());
			Assert.assertTrue(
				actual.toLowerCase().contains(entry.getValue().toLowerCase()),
				"Field " + entry.getKey() + " expected '" + entry.getValue() + "', found: " + actual
			);
		}
	}

	@When("User enters Patient Eve Phone {string}")
	public void user_enters_patient_eve_phone(String evePhone) {
		page().enterRegEvePhone(evePhone);
	}

	@When("User enters Patient Postal Code {string}")
	public void user_enters_patient_postal_code(String postalCode) {
		page().enterRegPostalCode(postalCode);
	}

	@Then("Patient Registration validation error containing {string} should be displayed")
	public void patient_registration_validation_error_containing_should_be_displayed(String expectedSubstring) {
		List<String> errors = page().getFormValidationMessages();
		boolean matched = errors.stream().anyMatch(e -> e.contains(expectedSubstring));
		Assert.assertTrue(
			matched,
			"Expected validation error containing '" + expectedSubstring + "', found: " + errors
		);
	}

	@When("User clicks Patient Search breadcrumb link")
	public void user_clicks_patient_search_breadcrumb_link() {
		page().clickPatientSearchBreadcrumb();
	}

	@When("User opens Patient Date of Birth picker")
	public void user_opens_patient_date_of_birth_picker() {
		page().openRegDobPicker();
	}

	@Then("future dates should be disabled in Date of Birth picker")
	public void future_dates_should_be_disabled_in_date_of_birth_picker() {
		int disabledCount = page().getDisabledFutureDateCellCount();
		Assert.assertTrue(disabledCount > 0, "Expected future date cells to be disabled in DOB picker.");
	}

	@When("User enters oversized values in User Name, Password, Medical Record No, and Ext")
	public void user_enters_oversized_values_in_maxlength_fields() {
		page().enterRegUsername("abcdefghijklmnopqrstuvwx");
		page().enterRegPassword("123456789012345678901234");
		page().enterRegMedNo("MRN123456789012345");
		page().enterRegExt("123456789");
	}

	@Then("User Name, Password, Medical Record No, and Ext should be limited to {int}, {int}, {int}, and {int} characters")
	public void maxlength_fields_should_be_limited(int maxUser, int maxPass, int maxMed, int maxExt) {
		Assert.assertEquals(page().getRegFieldValue("username").length(), maxUser);
		Assert.assertEquals(page().getRegPasswordValue().length(), maxPass);
		Assert.assertEquals(page().getOptionalDemographicValue("medno").length(), maxMed);
		Assert.assertEquals(page().getOptionalDemographicValue("ext").length(), maxExt);
	}

	@When("User selects Governorate {string} on Patient Registration form")
	public void user_selects_governorate_on_patient_registration_form(String governorate) {
		page().selectRegGovernorate(governorate);
	}

	@When("User selects Country {string} on Patient Registration form")
	public void user_selects_country_on_patient_registration_form(String country) {
		page().selectRegCountry(country);
	}

	@Then("Country {string} should be selected and Governorate should be reset")
	public void country_should_be_selected_and_governorate_should_be_reset(String expectedCountry) {
		Assert.assertTrue(
			page().getSelectedRegCountry().contains(expectedCountry),
			"Expected Country '" + expectedCountry + "', found: " + page().getSelectedRegCountry()
		);
		Assert.assertFalse(
			page().getSelectedRegGovernorate().toLowerCase().contains("cairo"),
			"Expected Governorate to reset after Country change, found: " + page().getSelectedRegGovernorate()
		);
	}

	@Then("Patient Registration form should return to default selections")
	public void patient_registration_form_should_return_to_default_selections() {
		Assert.assertTrue(
			page().areDefaultSelectionsValid(),
			"Expected Clear to reset Prefix (Mr), Sex (Male), WhatsApp (Yes), Insured (Self), and VIP (No)."
		);
	}

	@When("User clicks Admit to Bed button on registration success banner")
	public void user_clicks_admit_to_bed_button_on_registration_success_banner() {
		page().clickAdmitToBedButton();
	}

	// ---------- Existing Patient Search & Legacy Parity Verification Steps ----------
	@Then("Search Patient results table should display patient {string} with Add to Practice action")
	public void search_patient_results_table_should_display_patient_with_add_to_practice_action(String expectedName) {
		String rowText = page().getFirstSearchResultRowText();
		Assert.assertTrue(
			rowText.contains(expectedName),
			"Expected Search Patient results row to contain '" + expectedName + "', found: " + rowText
		);
		Assert.assertTrue(
			page().isAddToPracticeButtonDisplayed(),
			"Expected 'Add to Practice' button to be displayed for matched patient row."
		);
	}

	@Then("Select Patient results table should display patient {string} with all row actions")
	public void select_patient_results_table_should_display_patient_with_all_row_actions(String expectedName) {
		String rowText = page().getFirstSearchResultRowText();
		Assert.assertTrue(
			rowText.contains(expectedName),
			"Expected Select Patient results row to contain '" + expectedName + "', found: " + rowText
		);
		Assert.assertTrue(
			page().areSelectPatientRowActionsDisplayed(),
			"Expected Select Patient row to show Appt., Demo., Sports Details, Copay, S.Bill, Trans., Print, View, and Change actions. Row text: " + rowText
		);
	}

	@When("User selects Is Patient Insured option {string} on Patient Registration form")
	public void user_selects_is_patient_insured_option(String option) {
		page().selectInsuredOption(option);
	}

	@Then("legacy Insurance Details fields should be displayed on Patient Registration form")
	public void legacy_insurance_details_fields_should_be_displayed() {
		int visibleCount = page().getVisibleInsuranceFieldCount();
		Assert.assertTrue(
			visibleCount >= 6,
			"[LEGACY PARITY BUG] Selecting 'Is Patient Insured? = Insured' should reveal the Insurance Details section (Relation, Primary Insurance*, Group No*, Policy No*, Eff From Date*, Eff End Date*, Copay, Policy Type*) as in legacy patientregistration.jsp, but found " + visibleCount + " insurance fields."
		);
	}

	@When("User selects WhatsApp No option {string} on Patient Registration form")
	public void user_selects_whatsapp_no_option(String option) {
		page().selectWhatsAppOption(option);
	}

	@Then("separate WhatsApp Number input field should be displayed as in legacy")
	public void separate_whatsapp_number_input_field_should_be_displayed() {
		Assert.assertTrue(
			page().isSeparateWhatsAppNumberInputDisplayed(),
			"[LEGACY PARITY BUG] Selecting 'WhatsApp No = No' should reveal a separate WhatsApp Number input field (country dial code + whatsappNo) as in legacy patientregistration.jsp, but no WhatsApp number field was rendered."
		);
	}

	@Then("minimum length validation errors for Username and Password should be displayed as in legacy")
	public void minimum_length_validation_errors_for_username_and_password_should_be_displayed() {
		List<String> errors = page().getFormValidationMessages();
		boolean hasUserMinErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("user") && (e.contains("6") || e.toLowerCase().contains("minimum") || e.toLowerCase().contains("length")));
		boolean hasPassMinErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("password") && (e.contains("6") || e.toLowerCase().contains("minimum") || e.toLowerCase().contains("length")));
		Assert.assertTrue(
			hasUserMinErr && hasPassMinErr,
			"[LEGACY PARITY BUG] Legacy patientregistration.js requires minimum 6 characters for both User Name and Password, but migrated form accepted 'ab' and '123' without min-length validation errors. Actual errors: " + errors
		);
	}

	@When("User enters Patient Email {string} on Patient Registration form")
	public void user_enters_patient_email(String email) {
		page().enterRegEmail(email);
	}

	@Then("invalid Email validation error should be displayed as in legacy")
	public void invalid_email_validation_error_should_be_displayed() {
		List<String> errors = page().getFormValidationMessages();
		boolean hasEmailErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("email"));
		Assert.assertTrue(
			hasEmailErr,
			"[LEGACY PARITY BUG] Legacy patientregistration.js rejects invalid Email Id ('Specify valid Email Id'), but migrated form accepted 'bad-email' with no email validation error. Actual errors: " + errors
		);
	}

	@When("User enters whitespace-only First Name and Last Name on Patient Registration form")
	public void user_enters_whitespace_only_first_name_and_last_name() {
		page().enterRegFirstName("   ");
		page().enterRegLastName("   ");
	}

	@Then("whitespace-only First Name and Last Name validation errors should be displayed as in legacy")
	public void whitespace_only_first_name_and_last_name_validation_errors_should_be_displayed() {
		List<String> errors = page().getFormValidationMessages();
		boolean hasFirstErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("first name"));
		boolean hasLastErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("last name"));
		Assert.assertTrue(
			hasFirstErr && hasLastErr,
			"[LEGACY PARITY BUG] Legacy patientregistration.js rejects whitespace-only First Name and Last Name ('Only spaces are not allowed'), but migrated form accepted spaces with no errors. Actual errors: " + errors
		);
	}

	@When("User enters invalid Middle Name {string} and Medical Record No {string} on Patient Registration form")
	public void user_enters_invalid_middle_name_and_medno(String middleName, String medNo) {
		page().enterRegMiddleName(middleName);
		page().enterRegMedNo(medNo);
	}

	@Then("Middle Name and Ext.Med.Rec.No format validation errors should be displayed as in legacy")
	public void middle_name_and_medno_format_validation_errors_should_be_displayed() {
		List<String> errors = page().getFormValidationMessages();
		boolean hasMiddleErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("middle"));
		boolean hasMedErr = errors.stream().anyMatch(e -> e.toLowerCase().contains("med") || e.toLowerCase().contains("record") || e.toLowerCase().contains("special"));
		Assert.assertTrue(
			hasMiddleErr && hasMedErr,
			"[LEGACY PARITY BUG] Legacy patientregistration.js rejects digits in Middle Name and special characters in Ext.Med.Rec.No, but migrated form accepted '123' and 'MRN@#!' with no errors. Actual errors: " + errors
		);
	}

	@Then("Select Patient first row Date of Birth should match {string} without off-by-one shift")
	public void select_patient_first_row_dob_should_match(String expectedDob) {
		String actualDob = page().getSelectPatientFirstRowDob();
		Assert.assertEquals(
			actualDob,
			expectedDob,
			"[LEGACY PARITY BUG] Date of Birth timezone off-by-one bug: Patient registered with DOB 15/05/1995 displays as '" + actualDob + "' instead of '" + expectedDob + "'."
		);
	}

	@Then("header practice banner should display practice name {string} instead of numeric Practice ID")
	public void header_practice_banner_should_display_practice_name(String expectedPracticeName) {
		String actualBanner = page().getHeaderPracticeBannerText();
		Assert.assertTrue(
			actualBanner.contains(expectedPracticeName),
			"[LEGACY PARITY BUG] Legacy header displays '" + expectedPracticeName + "--HOSPVALUE01', but migrated header displays numeric ID: '" + actualBanner + "'."
		);
	}
}
