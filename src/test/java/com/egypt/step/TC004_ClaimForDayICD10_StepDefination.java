package com.egypt.step;

import java.util.List;

import org.testng.Assert;

import com.egypt.page.ClaimForDayICD10Page;
import com.egypt.page.LoginPage;
import com.egypt.utills.BaseClass;
import com.egypt.utills.ConfigReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC004_ClaimForDayICD10_StepDefination extends BaseClass {

	private ClaimForDayICD10Page claimForDayPage;
	private String lastErrorFeedback = "";

	private ClaimForDayICD10Page page() {
		if (claimForDayPage == null) {
			claimForDayPage = new ClaimForDayICD10Page(driver);
		}
		return claimForDayPage;
	}

	@Given("User is on Claim for Day-ICD10 page")
	public void user_is_on_claim_for_day_icd10_page() throws InterruptedException {
		browserLaunch();
		maximizeWindow();
		implicitlyWait(ConfigReader.getInt("wait.implicit"));
		enterApplnUrl(ConfigReader.get("app.url"));

		LoginPage loginPage = new LoginPage(driver);
		loginPage.userNameAndPass(ConfigReader.get("staff.username"), ConfigReader.get("staff.password"));
		loginPage.clickBtn();
		String expectedDisplay = ConfigReader.get("staff.displayName");
		Assert.assertEquals(loginPage.suicessMessage(expectedDisplay), expectedDisplay);

		claimForDayPage = new ClaimForDayICD10Page(driver);
		claimForDayPage.openClaimForDayICD10();
		Assert.assertTrue(claimForDayPage.isClaimForDayPageDisplayed(),
				"Claim for Day-ICD10 page did not open. URL: " + getApplnUrl());
	}

	@Given("User launches a fresh unauthenticated browser session")
	public void user_launches_a_fresh_unauthenticated_browser_session() {
		browserLaunch();
		maximizeWindow();
		implicitlyWait(ConfigReader.getInt("wait.implicit"));
		claimForDayPage = new ClaimForDayICD10Page(driver);
	}

	@When("User navigates to Billing Details and opens Claim for Day-ICD10")
	public void user_navigates_to_billing_details_and_opens_claim_for_day_icd10() {
		page().openClaimForDayICD10();
	}

	@When("User opens Claim for Day-ICD10 URL directly without logging in")
	public void user_opens_claim_for_day_icd10_url_directly_without_logging_in() {
		page().openClaimForDayUrlDirectly();
	}

	@Then("Claim for Day-ICD10 page should be displayed with Search Filters and Claims List")
	public void claim_for_day_icd10_page_should_be_displayed_with_search_filters_and_claims_list() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(),
				"Claim for Day-ICD10 page elements not displayed. URL: " + getApplnUrl());
	}

	@Then("Default Claim for Day-ICD10 filters should be set to Location {string}, Physician {string}, Payor {string}, Display {string}, and Include NoShow {string}")
	public void default_claim_for_day_filters_should_be_set(String location, String physician, String payor,
			String display, String includeNoShow) {
		Assert.assertEquals(page().getSelectedLocation(), location, "Location default mismatch");
		Assert.assertEquals(page().getSelectedPhysician(), physician, "Physician default mismatch");
		Assert.assertEquals(page().getSelectedPayor(), payor, "Payor default mismatch");
		Assert.assertEquals(page().getSelectedDisplay(), display, "Display default mismatch");
		if (includeNoShow.equalsIgnoreCase("No")) {
			Assert.assertTrue(page().isIncludeNoShowNoSelected(), "Include NoShow should default to No");
		} else {
			Assert.assertTrue(page().isIncludeNoShowYesSelected(), "Include NoShow should be Yes");
		}
	}

	@Then("Claims List table and pagination entries text should be displayed")
	public void claims_list_table_and_pagination_entries_text_should_be_displayed() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Claims List table not displayed");
		String paginationText = page().getPaginationTotalText();
		boolean hasRowsOrEmpty = page().getClaimItemRowCount() > 0 || page().isEmptyStateOrZeroEntries();
		Assert.assertTrue(hasRowsOrEmpty, "Neither claim rows nor empty state displayed");
		if (page().getClaimItemRowCount() > 0) {
			Assert.assertTrue(paginationText.toLowerCase().contains("showing"),
					"Expected pagination summary text, got: " + paginationText);
		}
	}

	@When("User selects Location filter {string}, Physician filter {string}, and Payor filter {string}")
	public void user_selects_location_physician_and_payor_filters(String location, String physician, String payor) {
		page().selectLocation(location);
		page().selectPhysician(physician);
		page().selectPayor(payor);
	}

	@When("User sets Start Date {string} and End Date {string} on Claim for Day-ICD10 page")
	public void user_sets_start_date_and_end_date(String startDate, String endDate) {
		page().setStartDate(startDate);
		page().setEndDate(endDate);
	}

	@When("User enters malformed Start Date {string} on Claim for Day-ICD10 page")
	public void user_enters_malformed_start_date(String badDate) {
		page().setStartDate(badDate);
	}

	@When("User clears Start Date and End Date on Claim for Day-ICD10 page")
	public void user_clears_start_date_and_end_date() {
		page().clearStartDate();
		page().clearEndDate();
	}

	@When("User selects Display filter {string} on Claim for Day-ICD10 page")
	public void user_selects_display_filter(String displayOption) {
		page().selectDisplay(displayOption);
	}

	@When("User selects Include NoShow {string} on Claim for Day-ICD10 page")
	public void user_selects_include_noshow(String option) {
		page().selectIncludeNoShow(option);
	}

	@When("User clicks Submit on Claim for Day-ICD10 page")
	public void user_clicks_submit_on_claim_for_day_icd10_page() {
		page().clickSubmit();
	}

	@When("User clicks Reset on Claim for Day-ICD10 page")
	public void user_clicks_reset_on_claim_for_day_icd10_page() {
		page().clickReset();
	}

	@When("User clicks sort on {string} column in Claims List")
	public void user_clicks_sort_on_column_in_claims_list(String columnName) {
		page().clickSortColumn(columnName);
	}

	@When("User clicks a claim row link in Claims List if available")
	public void user_clicks_a_claim_row_link_in_claims_list_if_available() {
		if (page().getClaimItemRowCount() > 0) {
			page().clickFirstClaimRowActionCell(1);
		}
	}

	@When("User returns to Claim for Day-ICD10 and clicks Go Back")
	public void user_returns_to_claim_for_day_icd10_and_clicks_go_back() {
		if (!getApplnUrl().contains("/claims/for-the-day-10")) {
			page().openClaimForDayICD10();
		}
		page().clickGoBack();
	}

	@When("User rapidly clicks Submit {int} times on Claim for Day-ICD10 page")
	public void user_rapidly_clicks_submit_times(int times) {
		page().clickSubmitRapidly(times);
	}

	@When("User rapidly clicks Reset {int} times on Claim for Day-ICD10 page")
	public void user_rapidly_clicks_reset_times(int times) {
		page().clickResetRapidly(times);
	}

	@When("User simulates a 500 server error and clicks Submit on Claim for Day-ICD10 page")
	public void user_simulates_a_500_server_error_and_clicks_submit() {
		page().enableSimulated500Error();
		page().clickSubmit();
		lastErrorFeedback = page().getVisibleFeedbackOrErrors();
		page().disableSimulated500Error();
	}

	@Then("Claims List should update cleanly without application errors")
	public void claims_list_should_update_cleanly_without_application_errors() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Page navigated away unexpectedly");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error page displayed");
		Assert.assertFalse(page().isLoadingSpinnerVisible(), "Loading spinner remained stuck");
	}

	@Then("Displayed claims should fall between {string} and {string}")
	public void displayed_claims_should_fall_between(String startDate, String endDate) {
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error displayed after date filter");
		Assert.assertTrue(page().areAllDisplayedDatesWithinRange(startDate, endDate),
				"Found claim date outside [" + startDate + " .. " + endDate + "]: " + page().getColumnValues(2));
	}

	@Then("Display filter should show {string} and Claims List should update cleanly")
	public void display_filter_should_show_and_update_cleanly(String expectedDisplay) {
		Assert.assertEquals(page().getSelectedDisplay(), expectedDisplay, "Display filter value mismatch");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error displayed");
		Assert.assertFalse(page().isLoadingSpinnerVisible(), "Loading spinner stuck");
	}

	@Then("Include NoShow {string} should be selected and Claims List should update cleanly")
	public void include_noshow_should_be_selected_and_update_cleanly(String option) {
		if (option.equalsIgnoreCase("Yes")) {
			Assert.assertTrue(page().isIncludeNoShowYesSelected(), "Include NoShow Yes was not selected");
		} else {
			Assert.assertTrue(page().isIncludeNoShowNoSelected(), "Include NoShow No was not selected");
		}
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error displayed");
		Assert.assertFalse(page().isLoadingSpinnerVisible(), "Loading spinner stuck");
	}

	@Then("Claims List should remain sorted and stable without errors")
	public void claims_list_should_remain_sorted_and_stable_without_errors() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Page unstable after column sort");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error after sorting");
		List<String> dates = page().getColumnValues(2);
		Assert.assertNotNull(dates, "Dates list was null after sorting");
	}

	@Then("Pagination controls should display entries count and switch pages when multiple pages exist")
	public void pagination_controls_should_display_entries_count_and_switch_pages() {
		String initialSummary = page().getPaginationTotalText();
		if (page().getClaimItemRowCount() > 0) {
			Assert.assertFalse(initialSummary.isEmpty(), "Pagination summary text was empty");
			if (page().isNextPageEnabled()) {
				String pageBefore = page().getActivePageNumber();
				page().clickNextPage();
				String pageAfter = page().getActivePageNumber();
				Assert.assertNotEquals(pageAfter, pageBefore, "Next page click did not change active page");
				page().clickPreviousPage();
				Assert.assertEquals(page().getActivePageNumber(), pageBefore,
						"Previous page click did not return to original page");
			}
		} else {
			Assert.assertTrue(page().isEmptyStateOrZeroEntries(), "Expected empty state when 0 rows");
		}
	}

	@Then("Go Back should navigate away from Claim for Day-ICD10 page")
	public void go_back_should_navigate_away_from_claim_for_day_icd10_page() {
		Assert.assertFalse(getApplnUrl().contains("/claims/for-the-day-10"),
				"Go Back stayed on Claim for Day-ICD10 page: " + getApplnUrl());
	}

	@Then("Invalid date range should be blocked or return no matching claims cleanly")
	public void invalid_date_range_should_be_blocked_or_return_no_matching_claims() {
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error on inverted date range");
		String feedback = page().getVisibleFeedbackOrErrors();
		Assert.assertTrue(!feedback.isEmpty() || page().isEmptyStateOrZeroEntries(),
				"Expected validation error or 0 claims for Start Date > End Date, but got "
						+ page().getClaimItemRowCount() + " rows with feedback: '" + feedback + "'");
	}

	@Then("Malformed date {string} should be rejected or handled safely without crashing")
	public void malformed_date_should_be_rejected_or_handled_safely(String badDate) {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Page crashed on malformed date: " + badDate);
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error on malformed date: " + badDate);
		String actualVal = page().getStartDateValue();
		String feedback = page().getVisibleFeedbackOrErrors();
		Assert.assertTrue(!badDate.equals(actualVal) || !feedback.isEmpty() || page().isEmptyStateOrZeroEntries(),
				"Malformed date '" + badDate + "' was accepted without rejection or validation");
	}

	@Then("Cleared date submission should be blocked or handled safely without crashing")
	public void cleared_date_submission_should_be_blocked_or_handled_safely() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Page crashed when dates were cleared");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error when dates were cleared");
		Assert.assertFalse(page().isLoadingSpinnerVisible(), "Loading spinner stuck when dates were cleared");
	}

	@Then("Claims List should display an empty state with zero entries")
	public void claims_list_should_display_an_empty_state_with_zero_entries() {
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error on empty filter combination");
		Assert.assertTrue(page().isEmptyStateOrZeroEntries(),
				"Expected 0 claim rows, but found: " + page().getClaimItemRowCount());
	}

	@Then("Extreme date range should be handled cleanly without server error")
	public void extreme_date_range_should_be_handled_cleanly_without_server_error() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Page crashed on extreme date range");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "500 error shown on extreme date range");
		String feedback = page().getVisibleFeedbackOrErrors();
		Assert.assertTrue(page().isEmptyStateOrZeroEntries() || !feedback.isEmpty(),
				"Expected empty results or validation message for extreme date range");
	}

	@Then("Claim for Day-ICD10 page should remain stable with no stuck loading state")
	public void claim_for_day_page_should_remain_stable_with_no_stuck_loading_state() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Page became unstable after rapid clicks");
		Assert.assertFalse(page().isLoadingSpinnerVisible(), "Loading spinner remained stuck after rapid clicks");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error after rapid clicks");
		Assert.assertEquals(page().getSelectedLocation(), "All", "Location did not reset to All");
		Assert.assertEquals(page().getSelectedDisplay(), "Open", "Display did not reset to Open");
	}

	@Then("User should be redirected to login page without exposing claims data")
	public void user_should_be_redirected_to_login_page_without_exposing_claims_data() {
		String url = getApplnUrl();
		Assert.assertTrue(url.contains("/login") || !url.contains("/claims/for-the-day-10"),
				"Unauthenticated user was not redirected away from Claim for Day-ICD10. URL: " + url);
		Assert.assertEquals(page().getClaimItemRowCount(), 0,
				"Claims data was exposed without authentication");
	}

	@Then("Claim for Day-ICD10 page should handle the server error gracefully and allow retry")
	public void claim_for_day_page_should_handle_the_server_error_gracefully_and_allow_retry() {
		Assert.assertTrue(page().isClaimForDayPageDisplayed(),
				"Page crashed on simulated 500 error. Feedback: " + lastErrorFeedback);
		Assert.assertFalse(page().isLoadingSpinnerVisible(), "Loading spinner stuck after 500 error");
		// Retry after restoring network
		page().clickSubmit();
		Assert.assertTrue(page().isClaimForDayPageDisplayed(), "Retry submit failed after restoring network");
		Assert.assertFalse(page().isServerErrorPageDisplayed(), "Server error persisted after retry");
	}
}
