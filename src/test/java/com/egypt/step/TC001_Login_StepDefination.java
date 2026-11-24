package com.egypt.step;

import org.testng.Assert;

import com.egypt.page.LoginPage;
import static com.egypt.utills.ConfigReader.resolve;

import com.egypt.utills.BaseClass;
import com.egypt.utills.ConfigReader;

import io.cucumber.java.en.*;

public class TC001_Login_StepDefination extends BaseClass {

	LoginPage loginPage;
	static String invalidPasswordError;
	String patientTabPlaceholder;
	String staffTabPlaceholder;

	@Given("User launches the application login page")
	public void user_launches_the_application_login_page() {
		browserLaunch();
		maximizeWindow();
		implicitlyWait(ConfigReader.getInt("wait.implicit"));
		enterApplnUrl(ConfigReader.get("app.url"));
		loginPage = new LoginPage(driver);
	}

	@Then("the login page elements are displayed")
	public void the_login_page_elements_are_displayed() {
		loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.areLoginPageElementsDisplayed());
	}

	@When("User enters valid staff credentials {string} and {string}")
	public void user_enters_valid_staff_credentials_and(String user, String pass) throws InterruptedException {
		loginPage = new LoginPage(driver);
		loginPage.userNameAndPass(resolve(user), resolve(pass));
	}

	@When("User clicks Authorized Login")
	public void user_clicks_authorized_login() {
		loginPage = new LoginPage(driver);
		loginPage.clickBtn();
	}

	@Then("User should navigate to staff dashboard {string}")
	public void user_should_navigate_to_staff_dashboard(String expectedUser) {
		loginPage = new LoginPage(driver);
		String expected = resolve(expectedUser);
		Assert.assertEquals(loginPage.suicessMessage(expected), expected);
	}

	@When("User selects Patient Portal")
	public void user_selects_patient_portal() {
		loginPage = new LoginPage(driver);
		loginPage.selectPatientPortalTab();
	}

	@When("User enters valid patient credentials {string} and {string}")
	public void user_enters_valid_patient_credentials_and(String user, String pass) throws InterruptedException {
		loginPage = new LoginPage(driver);
		loginPage.userNameAndPass(resolve(user), resolve(pass));
	}

	@When("User clicks Patient Login")
	public void user_clicks_patient_login() {
		loginPage = new LoginPage(driver);
		loginPage.clickBtn();
	}

	@Then("User should navigate to patient dashboard")
	public void user_should_navigate_to_patient_dashboard() {
		waitForSeconds();
		Assert.assertFalse(getApplnUrl().contains("/login"));
	}

	@When("User enters a valid username {string} and an incorrect password {string}")
	public void user_enters_a_valid_username_and_an_incorrect_password(String user, String pass) throws InterruptedException {
		loginPage = new LoginPage(driver);
		loginPage.userNameAndPass(resolve(user), resolve(pass));
	}

	@When("User enters an unknown username {string} and an incorrect password {string}")
	public void user_enters_an_unknown_username_and_an_incorrect_password(String user, String pass) throws InterruptedException {
		loginPage = new LoginPage(driver);
		loginPage.userNameAndPass(resolve(user), resolve(pass));
	}

	@When("User clicks the login button")
	public void user_clicks_the_login_button() {
		loginPage = new LoginPage(driver);
		loginPage.clickBtn();
	}

	@Then("User should see a generic login error")
	public void user_should_see_a_generic_login_error() {
		loginPage = new LoginPage(driver);
		invalidPasswordError = loginPage.getErrorAlertMessage();
		Assert.assertFalse(invalidPasswordError.isEmpty());
		Assert.assertFalse(
			invalidPasswordError.contains("JDBCConnectionException") || invalidPasswordError.contains("EntityManager"),
			"Backend DB error displayed instead of generic login error: " + invalidPasswordError
		);
	}

	@Then("User should see the same generic login error")
	public void user_should_see_the_same_generic_login_error() {
		loginPage = new LoginPage(driver);
		String invalidUsernameError = loginPage.getErrorAlertMessage();
		Assert.assertFalse(invalidUsernameError.isEmpty());
		Assert.assertFalse(
			invalidUsernameError.contains("JDBCConnectionException") || invalidUsernameError.contains("EntityManager"),
			"Backend DB error displayed instead of generic login error: " + invalidUsernameError
		);
		if (invalidPasswordError != null) {
			Assert.assertEquals(invalidUsernameError, invalidPasswordError);
		}
	}

	@Then("User should remain on the login page")
	public void user_should_remain_on_the_login_page() {
		loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.isLoginPageDisplayed());
	}

	@When("User clicks the login button without entering credentials")
	public void user_clicks_the_login_button_without_entering_credentials() {
		loginPage = new LoginPage(driver);
		loginPage.clickBtn();
	}

	@Then("required-field validation should appear for both fields {string} and {string}")
	public void required_field_validation_should_appear_for_both_fields_and(String expectedUserErr, String expectedPassErr) {
		loginPage = new LoginPage(driver);
		Assert.assertEquals(loginPage.getUsernameRequiredError(), expectedUserErr);
		Assert.assertEquals(loginPage.getPasswordRequiredError(), expectedPassErr);
	}

	@When("User enters only a username {string} and clicks the login button")
	public void user_enters_only_a_username_and_clicks_the_login_button(String user) {
		loginPage = new LoginPage(driver);
		loginPage.enterUsername(resolve(user));
		loginPage.clickBtn();
	}

	@Then("required-field validation should appear for the password field {string}")
	public void required_field_validation_should_appear_for_the_password_field(String expectedPassErr) {
		loginPage = new LoginPage(driver);
		Assert.assertEquals(loginPage.getPasswordRequiredError(), expectedPassErr);
	}

	@When("User enters only a password {string} and clicks the login button")
	public void user_enters_only_a_password_and_clicks_the_login_button(String pass) {
		loginPage = new LoginPage(driver);
		loginPage.enterPassword(resolve(pass));
		loginPage.clickBtn();
	}

	@Then("required-field validation should appear for the username field {string}")
	public void required_field_validation_should_appear_for_the_username_field(String expectedUserErr) {
		loginPage = new LoginPage(driver);
		Assert.assertEquals(loginPage.getUsernameRequiredError(), expectedUserErr);
	}

	@When("User enters a password {string}")
	public void user_enters_a_password(String pass) {
		loginPage = new LoginPage(driver);
		loginPage.enterPassword(resolve(pass));
	}

	@When("User clicks the password visibility icon")
	public void user_clicks_the_password_visibility_icon() {
		loginPage = new LoginPage(driver);
		loginPage.clickPasswordVisibilityIcon();
	}

	@Then("the password should be visible")
	public void the_password_should_be_visible() {
		loginPage = new LoginPage(driver);
		Assert.assertEquals(loginPage.getPasswordFieldType(), "text");
	}

	@When("User clicks the password visibility icon again")
	public void user_clicks_the_password_visibility_icon_again() {
		loginPage = new LoginPage(driver);
		loginPage.clickPasswordVisibilityIcon();
	}

	@Then("the password should be hidden")
	public void the_password_should_be_hidden() {
		loginPage = new LoginPage(driver);
		Assert.assertEquals(loginPage.getPasswordFieldType(), "password");
	}

	@When("User switches between Staff \\/ Provider and Patient Portal")
	public void user_switches_between_staff_provider_and_patient_portal() {
		loginPage = new LoginPage(driver);
		loginPage.selectPatientPortalTab();
		patientTabPlaceholder = loginPage.getUsernamePlaceholder();
		loginPage.selectStaffTab();
		staffTabPlaceholder = loginPage.getUsernamePlaceholder();
	}

	@Then("the correct login form should be displayed for each tab {string} and {string}")
	public void the_correct_login_form_should_be_displayed_for_each_tab_and(String expectedPatientPlaceholder, String expectedStaffPlaceholder) {
		Assert.assertEquals(patientTabPlaceholder, expectedPatientPlaceholder);
		Assert.assertEquals(staffTabPlaceholder, expectedStaffPlaceholder);
	}

	@Then("the Remember device checkbox should be unchecked by default")
	public void the_remember_device_checkbox_should_be_unchecked_by_default() {
		loginPage = new LoginPage(driver);
		Assert.assertFalse(loginPage.isRememberDeviceSelected());
	}

	@When("User clicks the Remember device checkbox")
	public void user_clicks_the_remember_device_checkbox() {
		loginPage = new LoginPage(driver);
		loginPage.clickRememberDeviceCheckbox();
	}

	@Then("the Remember device checkbox should be checked")
	public void the_remember_device_checkbox_should_be_checked() {
		loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.isRememberDeviceSelected());
	}

	@When("User clicks the Remember device checkbox again")
	public void user_clicks_the_remember_device_checkbox_again() {
		loginPage = new LoginPage(driver);
		loginPage.clickRememberDeviceCheckbox();
	}

	@Then("the Remember device checkbox should be unchecked")
	public void the_remember_device_checkbox_should_be_unchecked() {
		loginPage = new LoginPage(driver);
		Assert.assertFalse(loginPage.isRememberDeviceSelected());
	}

	@When("User clicks the Forgot Password link")
	public void user_clicks_the_forgot_password_link() {
		loginPage = new LoginPage(driver);
		loginPage.clickForgotPasswordLink();
	}

	@When("User clicks the Initialize Account link")
	public void user_clicks_the_initialize_account_link() {
		loginPage = new LoginPage(driver);
		loginPage.clickInitializeAccountLink();
	}

	@Then("User should navigate to the page containing {string}")
	public void user_should_navigate_to_the_page_containing(String expectedUrlPart) {
		waitForSeconds();
		Assert.assertTrue(
			getApplnUrl().contains(expectedUrlPart),
			"Expected URL to contain '" + expectedUrlPart + "' but was: " + getApplnUrl()
		);
	}

	@When("User closes the error alert banner")
	public void user_closes_the_error_alert_banner() {
		loginPage = new LoginPage(driver);
		loginPage.closeErrorAlert();
		waitForSeconds();
	}

	@Then("the error alert banner should not be displayed")
	public void the_error_alert_banner_should_not_be_displayed() {
		loginPage = new LoginPage(driver);
		Assert.assertFalse(loginPage.isErrorAlertDisplayed());
	}

	@Then("validation errors should be cleared")
	public void validation_errors_should_be_cleared() {
		loginPage = new LoginPage(driver);
		waitForSeconds();
		Assert.assertFalse(loginPage.isErrorAlertDisplayed());
		Assert.assertEquals(loginPage.getVisibleFieldValidationErrorCount(), 0);
	}

	@When("User enters valid staff credentials {string} and {string} and presses Enter")
	public void user_enters_valid_staff_credentials_and_and_presses_enter(String user, String pass) {
		loginPage = new LoginPage(driver);
		loginPage.enterCredentialsAndPressEnter(resolve(user), resolve(pass));
	}

}
