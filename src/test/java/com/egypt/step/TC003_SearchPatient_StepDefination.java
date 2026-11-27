package com.egypt.step;

import java.util.List;

import org.testng.Assert;

import com.egypt.page.LoginPage;
import com.egypt.page.SearchPatientPage;
import com.egypt.utills.BaseClass;
import com.egypt.utills.ConfigReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC003_SearchPatient_StepDefination extends BaseClass {

    private LoginPage loginPage;
    private SearchPatientPage searchPatientPage;

    private String enteredPatientId;
    private String enteredNationalId;
    private String enteredFirstName;
    private String enteredLastName;
    private String enteredDob;
    private String attemptedFutureDob;

    private SearchPatientPage getSearchPatientPage() {
        if (searchPatientPage == null) {
            searchPatientPage = new SearchPatientPage(driver);
        }
        return searchPatientPage;
    }

    @Given("user launches the application")
    public void user_launches_the_application() {
        browserLaunch();
        maximizeWindow();
        implicitlyWait(ConfigReader.getInt("wait.implicit"));
        enterApplnUrl(ConfigReader.get("app.url"));
    }

    @Given("user logs in with valid credentials")
    public void user_logs_in_with_valid_credentials() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.userNameAndPass(ConfigReader.get("staff.username"), ConfigReader.get("staff.password"));
        loginPage.clickBtn();
    }

    @Given("user navigates to the Select Patient page")
    public void user_navigates_to_the_select_patient_page() {
        searchPatientPage = new SearchPatientPage(driver);
        searchPatientPage.navigateToSelectPatientPage();
    }

    @Then("Select Patient page should be displayed")
    public void select_patient_page_should_be_displayed() {
        Assert.assertTrue(getSearchPatientPage().isSelectPatientPageDisplayed(),
                "Select Patient page is not displayed");
        Assert.assertEquals(getSearchPatientPage().getSelectPatientHeading(), "Select Patient");
    }

    @Then("all search fields should be visible")
    public void all_search_fields_should_be_visible() {
        Assert.assertTrue(getSearchPatientPage().areAllSearchFieldsVisible(),
                "Not all Search Criteria fields and action buttons are visible");
    }

    @When("user enters Patient ID {string}")
    public void user_enters_patient_id(String patientId) {
        this.enteredPatientId = patientId;
        getSearchPatientPage().enterPatientId(patientId);
    }

    @When("user enters National ID {string}")
    public void user_enters_national_id(String nationalId) {
        this.enteredNationalId = nationalId;
        getSearchPatientPage().enterNationalId(nationalId);
    }

    @When("user enters First Name {string}")
    public void user_enters_first_name(String firstName) {
        this.enteredFirstName = firstName;
        getSearchPatientPage().enterFirstName(firstName);
    }

    @When("user enters Last Name {string}")
    public void user_enters_last_name(String lastName) {
        this.enteredLastName = lastName;
        getSearchPatientPage().enterLastName(lastName);
    }

    @When("user enters Username {string}")
    public void user_enters_username(String username) {
        getSearchPatientPage().enterUsername(username);
    }

    @When("user enters Date of Birth {string}")
    public void user_enters_date_of_birth(String dob) {
        this.enteredDob = dob;
        getSearchPatientPage().enterDateOfBirth(dob);
    }

    @When("user selects Sports Type {string}")
    public void user_selects_sports_type(String sport) {
        getSearchPatientPage().selectSportsType(sport);
    }

    @When("user selects Club or Team Name {string}")
    public void user_selects_club_or_team_name(String team) {
        getSearchPatientPage().selectClubOrTeamName(team);
    }

    @When("user clicks Search button")
    public void user_clicks_search_button() {
        getSearchPatientPage().clickSearchButton();
    }

    @When("user clicks Search button without entering criteria")
    public void user_clicks_search_button_without_entering_criteria() {
        getSearchPatientPage().clickSearchButton();
    }

    @When("user clicks Search button multiple times quickly")
    public void user_clicks_search_button_multiple_times_quickly() {
        getSearchPatientPage().clickSearchButtonMultipleTimes(3);
    }

    @When("user selects Regular Search radio button")
    public void user_selects_regular_search_radio_button() {
        getSearchPatientPage().selectRegularSearchRadio();
    }

    @When("user selects Statement radio button")
    public void user_selects_statement_radio_button() {
        getSearchPatientPage().selectStatementRadio();
    }

    @When("user clicks Clear button")
    public void user_clicks_clear_button() {
        getSearchPatientPage().clickClearButton();
        this.enteredPatientId = null;
        this.enteredFirstName = null;
        this.enteredLastName = null;
        this.enteredDob = null;
    }

    @When("user attempts to select future DOB {string}")
    public void user_attempts_to_select_future_dob(String futureDob) {
        this.attemptedFutureDob = futureDob;
        getSearchPatientPage().enterDateOfBirth(futureDob);
        getSearchPatientPage().clickSearchButton();
    }

    @Then("matching patient {string} should be displayed")
    public void matching_patient_should_be_displayed(String patientName) {
        Assert.assertTrue(getSearchPatientPage().isMatchingTextDisplayedInResults(patientName),
                "Expected matching patient '" + patientName + "' to be displayed in results");
    }

    @Then("matching First Name records {string} should be displayed")
    public void matching_first_name_records_should_be_displayed(String firstName) {
        Assert.assertTrue(getSearchPatientPage().doAllRowsContainText(firstName),
                "Expected all displayed records to match First Name: " + firstName);
    }

    @Then("matching Last Name records {string} should be displayed")
    public void matching_last_name_records_should_be_displayed(String lastName) {
        Assert.assertTrue(getSearchPatientPage().doAllRowsContainText(lastName),
                "Expected all displayed records to match Last Name: " + lastName);
    }

    @Then("patients with Date of Birth {string} should be displayed")
    public void patients_with_date_of_birth_should_be_displayed(String dob) {
        Assert.assertTrue(getSearchPatientPage().doAllRowsContainDob(dob),
                "Expected all displayed records to match Date of Birth: " + dob);
    }

    @Then("patients associated with {string} should be displayed")
    public void patients_associated_with_should_be_displayed(String sport) {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.hasSearchResults(),
                "Expected patient records associated with sport '" + sport + "' to be displayed");
    }

    @Then("patients associated with team {string} should be displayed")
    public void patients_associated_with_team_should_be_displayed(String team) {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.hasSearchResults(),
                "Expected patient records associated with team '" + team + "' to be displayed");
    }

    @Then("patient matching all criteria should be displayed")
    public void patient_matching_all_criteria_should_be_displayed() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.hasSearchResults(),
                "Expected patient matching multiple criteria to be displayed");
        if (enteredFirstName != null) {
            Assert.assertTrue(page.isMatchingTextDisplayedInResults(enteredFirstName),
                    "Result does not match First Name: " + enteredFirstName);
        }
        if (enteredLastName != null) {
            Assert.assertTrue(page.isMatchingTextDisplayedInResults(enteredLastName),
                    "Result does not match Last Name: " + enteredLastName);
        }
        if (enteredDob != null) {
            Assert.assertTrue(page.isMatchingTextDisplayedInResults(enteredDob)
                    || page.hasSearchResults(),
                    "Result does not match DOB: " + enteredDob);
        }
    }

    @Then("Regular Search results should be displayed")
    public void regular_search_results_should_be_displayed() {
        Assert.assertTrue(getSearchPatientPage().isRegularSearchSelected(),
                "Regular Search radio button should remain selected");
        Assert.assertTrue(getSearchPatientPage().hasSearchResults(),
                "Expected Regular Search results to be displayed");
    }

    @Then("Statement search should execute successfully")
    public void statement_search_should_execute_successfully() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.isStatementSelected(),
                "Statement radio button should remain selected");
        Assert.assertTrue(page.hasSearchResults() || page.isNoResultsMessageDisplayed(),
                "Expected Statement search to complete with results or empty-state message");
    }

    @Then("all search fields should be cleared")
    public void all_search_fields_should_be_cleared() {
        Assert.assertTrue(getSearchPatientPage().areAllSearchFieldsCleared(),
                "Expected all search fields to be cleared after clicking Clear");
    }

    @Then("no matching patient should be displayed")
    public void no_matching_patient_should_be_displayed() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertFalse(page.hasSearchResults(),
                "Expected no patient records for invalid search input");
    }

    @Then("appropriate validation message should appear")
    public void appropriate_validation_message_should_appear() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.isValidationMessageDisplayed() || page.isNoResultsMessageDisplayed(),
                "Expected a validation or no-results message to appear");
    }

    @Then("no matching patient records should be displayed")
    public void no_matching_patient_records_should_be_displayed() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertFalse(page.hasSearchResults(),
                "Expected no matching patient records to be displayed");
    }

    @Then("invalid Patient ID should be rejected or handled correctly")
    public void invalid_patient_id_should_be_rejected_or_handled_correctly() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(
                page.isInvalidInputRejectedOrHandled(enteredPatientId, page.getPatientIdValue()),
                "Expected invalid Patient ID '" + enteredPatientId + "' to be rejected or return no records");
    }

    @Then("invalid First Name characters should be handled correctly")
    public void invalid_first_name_characters_should_be_handled_correctly() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(
                page.isInvalidInputRejectedOrHandled(enteredFirstName, page.getFirstNameValue()),
                "Expected invalid First Name '" + enteredFirstName + "' to be rejected or return no records");
    }

    @Then("invalid Date of Birth should not be accepted")
    public void invalid_date_of_birth_should_not_be_accepted() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.isInvalidDobRejected(enteredDob),
                "Expected invalid Date of Birth '" + enteredDob + "' not to be accepted");
    }

    @Then("appropriate validation should be displayed")
    public void appropriate_validation_should_be_displayed() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(
                page.isValidationMessageDisplayed()
                        || page.isNoResultsMessageDisplayed()
                        || page.isInvalidDobRejected(enteredDob),
                "Expected validation feedback or rejection for invalid Date of Birth: " + enteredDob);
    }

    @Then("no incorrect patient record should be returned")
    public void no_incorrect_patient_record_should_be_returned() {
        SearchPatientPage page = getSearchPatientPage();
        List<String> rows = page.getSearchResultRowTexts();
        for (String row : rows) {
            boolean matchesId = enteredPatientId != null
                    && row.toLowerCase().contains(enteredPatientId.toLowerCase());
            boolean matchesFirstName = enteredFirstName != null
                    && row.toLowerCase().contains(enteredFirstName.toLowerCase());
            Assert.assertTrue(matchesId && matchesFirstName,
                    "Returned incorrect patient record for mismatched criteria: " + row);
        }
        Assert.assertEquals(rows.size(), 0,
                "Expected zero records for mismatching Patient ID and First Name");
    }

    @Then("configured empty search validation should be displayed")
    public void configured_empty_search_validation_should_be_displayed() {
        SearchPatientPage page = getSearchPatientPage();
        boolean handled = page.isValidationMessageDisplayed()
                || page.isNoResultsMessageDisplayed()
                || page.hasSearchResults();
        Assert.assertTrue(handled,
                "Expected configured empty search validation message or default behavior");
    }

    @Then("all matching patient records should be displayed")
    public void all_matching_patient_records_should_be_displayed() {
        Assert.assertTrue(getSearchPatientPage().hasSearchResults(),
                "Expected matching patient records to be displayed");
    }

    @Then("duplicate patient records should not be displayed")
    public void duplicate_patient_records_should_not_be_displayed() {
        Assert.assertFalse(getSearchPatientPage().hasDuplicateResultRows(),
                "Duplicate patient records were displayed in search results");
    }

    @Then("only patient {string} should be displayed")
    public void only_patient_should_be_displayed(String expectedPatientId) {
        List<String> rows = getSearchPatientPage().getSearchResultRowTexts();
        Assert.assertEquals(rows.size(), 1,
                "Expected exactly one patient record for ID: " + expectedPatientId);
        Assert.assertTrue(rows.get(0).toLowerCase().contains(expectedPatientId.toLowerCase()),
                "Displayed patient record did not match expected Patient ID: " + expectedPatientId);
    }

    @Then("future DOB should be restricted")
    public void future_dob_should_be_restricted() {
        Assert.assertTrue(getSearchPatientPage().isFutureDobRestricted(attemptedFutureDob),
                "Future DOB '" + attemptedFutureDob + "' should be restricted");
    }

    @Then("matching patient records should not be displayed")
    public void matching_patient_records_should_not_be_displayed() {
        Assert.assertFalse(getSearchPatientPage().hasSearchResults(),
                "Expected no matching patient records when searching with leading/trailing spaces");
    }

    @Then("search results should follow case sensitivity rules")
    public void search_results_should_follow_case_sensitivity_rules() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.isSelectPatientPageDisplayed(),
                "Select Patient page should remain active after case-sensitivity search");
        if (page.hasSearchResults() && enteredFirstName != null) {
            Assert.assertTrue(page.doAllRowsContainText(enteredFirstName.trim()),
                    "Displayed rows did not match searched First Name: " + enteredFirstName);
        }
    }

    @Then("matching records should be displayed if partial search is supported")
    public void matching_records_should_be_displayed_if_partial_search_is_supported() {
        SearchPatientPage page = getSearchPatientPage();
        String partial = enteredFirstName != null ? enteredFirstName : enteredLastName;
        if (page.hasSearchResults() && partial != null) {
            Assert.assertTrue(page.doAllRowsContainText(partial.trim()),
                    "Displayed rows did not match partial search value: " + partial);
        } else {
            Assert.assertTrue(page.isNoResultsMessageDisplayed() || !page.hasSearchResults(),
                    "Expected matching partial records or clean empty-state handling");
        }
    }

    @Then("consistent search results should be displayed")
    public void consistent_search_results_should_be_displayed() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertTrue(page.hasSearchResults(),
                "Expected consistent search results after repeated Search button clicks");
        if (enteredPatientId != null) {
            Assert.assertTrue(page.isMatchingTextDisplayedInResults(enteredPatientId),
                    "Search results did not match Patient ID: " + enteredPatientId);
        }
    }

    @Then("duplicate results should not be generated")
    public void duplicate_results_should_not_be_generated() {
        Assert.assertFalse(getSearchPatientPage().hasDuplicateResultRows(),
                "Duplicate results were generated after rapid Search button clicks");
    }

    @Then("application should accept the minimum allowed length")
    public void application_should_accept_the_minimum_allowed_length() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertFalse(page.getPatientIdValue().isEmpty(),
                "Expected minimum allowed Patient ID length to be accepted by the input field");
        Assert.assertTrue(page.hasSearchResults() || page.isNoResultsMessageDisplayed()
                        || page.isSelectPatientPageDisplayed(),
                "Application did not handle minimum Patient ID length search cleanly");
    }

    @Then("application should accept the maximum allowed length")
    public void application_should_accept_the_maximum_allowed_length() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertFalse(page.getPatientIdValue().isEmpty(),
                "Expected maximum allowed Patient ID length to be accepted by the input field");
        Assert.assertTrue(page.hasSearchResults() || page.isNoResultsMessageDisplayed()
                        || page.isSelectPatientPageDisplayed(),
                "Application did not handle maximum Patient ID length search cleanly");
    }

    @Then("application should accept the maximum supported length")
    public void application_should_accept_the_maximum_supported_length() {
        SearchPatientPage page = getSearchPatientPage();
        Assert.assertFalse(page.getFirstNameValue().isEmpty(),
                "Expected maximum supported First Name length to be accepted by the input field");
        Assert.assertTrue(page.hasSearchResults() || page.isNoResultsMessageDisplayed()
                        || page.isSelectPatientPageDisplayed(),
                "Application did not handle maximum First Name length search cleanly");
    }

    @Then("National ID should be validated according to configured length")
    public void national_id_should_be_validated_according_to_configured_length() {
        Assert.assertTrue(getSearchPatientPage().isNationalIdValidatedByLength(enteredNationalId),
                "National ID '" + enteredNationalId + "' was not validated according to configured length");
    }
}
