package com.egypt.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.egypt.utills.BaseClass;

public class LoginPage extends BaseClass {

    private static final String BRAND_HEADING = "//h1[contains(@class,'brand-title')]";
    private static final String SIGN_IN_HEADING = "//h2[normalize-space()='Sign In']";
    private static final String STAFF_TAB = "//div[@role='tab' and contains(@id,'-tab-staff')]";
    private static final String PATIENT_TAB = "//div[@role='tab' and contains(@id,'-tab-patient')]";
    private static final String USERNAME = "//input[@id='loginNm']";
    private static final String PASS = "//input[@id='password']";
    private static final String PASSWORD_EYE_ICON = "//span[contains(@class,'ant-input-password-icon')]";
    private static final String REMEMBER_DEVICE_CHECKBOX = "//input[contains(@class,'ant-checkbox-input')]";
    private static final String FORGOT_PASSWORD_LINK = "//a[normalize-space()='Forgot Password?']";
    private static final String INITIALIZE_ACCOUNT_LINK = "//a[normalize-space()='Initialize Account']";
    private static final String LOGIN_BTN = "//button[@type='submit']";
    private static final String ERROR_ALERT = "//div[@role='alert' and contains(@class,'ant-alert-error')]";
    private static final String ERROR_ALERT_CLOSE_BTN = "//button[contains(@class,'ant-alert-close-icon')]";
    private static final String FIELD_VALIDATION_ERROR = "//div[contains(@class,'ant-form-item-explain-error')]";
    private static final String USERNAME_REQUIRED_ERROR = "//input[@id='loginNm']/ancestor::div[contains(@class,'ant-form-item-control')]//div[contains(@class,'ant-form-item-explain-error')]";
    private static final String PASSWORD_REQUIRED_ERROR = "//input[@id='password']/ancestor::div[contains(@class,'ant-form-item-control')]//div[contains(@class,'ant-form-item-explain-error')]";
    private static final String DESBOARD_MESSAGE1 = "//span[text()='HOSPVALUE01']";
    private static final String DESBOARD_MESSAGE2 = "//span[text()='Office Manager']";

    private WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is NULL. Initialize driver before creating LoginPage."
            );
        }

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = BRAND_HEADING)
    private WebElement brandHeading;

    @FindBy(xpath = SIGN_IN_HEADING)
    private WebElement signInHeading;

    @FindBy(xpath = STAFF_TAB)
    private WebElement staffTab;

    @FindBy(xpath = PATIENT_TAB)
    private WebElement patientTab;

    @FindBy(xpath = USERNAME)
    private WebElement userName;

    @FindBy(xpath = PASS)
    private WebElement pass;

    @FindBy(xpath = PASSWORD_EYE_ICON)
    private WebElement passwordEyeIcon;

    @FindBy(xpath = REMEMBER_DEVICE_CHECKBOX)
    private WebElement rememberDeviceCheckbox;

    @FindBy(xpath = FORGOT_PASSWORD_LINK)
    private WebElement forgotPasswordLink;

    @FindBy(xpath = INITIALIZE_ACCOUNT_LINK)
    private WebElement initializeAccountLink;

    @FindBy(xpath = LOGIN_BTN)
    private WebElement login_Btn;

    @FindBy(xpath = ERROR_ALERT)
    private WebElement errorAlert;

    @FindBy(xpath = ERROR_ALERT_CLOSE_BTN)
    private WebElement errorAlertCloseBtn;

    @FindBy(xpath = USERNAME_REQUIRED_ERROR)
    private WebElement usernameRequiredError;

    @FindBy(xpath = PASSWORD_REQUIRED_ERROR)
    private WebElement passwordRequiredError;

    @FindBy(xpath = DESBOARD_MESSAGE1)
    private WebElement desnoardMessage1;

    @FindBy(xpath = DESBOARD_MESSAGE2)
    private WebElement desnoardMessage2;

    public boolean areLoginPageElementsDisplayed() {
        webdriverWait(20, userName);
        webdriverWait(20, pass);
        webdriverWait(20, login_Btn);
        return displayedMethod(brandHeading)
            && displayedMethod(signInHeading)
            && displayedMethod(staffTab)
            && displayedMethod(patientTab)
            && displayedMethod(userName)
            && displayedMethod(pass)
            && displayedMethod(passwordEyeIcon)
            && displayedMethod(forgotPasswordLink)
            && displayedMethod(initializeAccountLink)
            && displayedMethod(login_Btn);
    }

    public void selectStaffTab() {
        elementClick(staffTab);
    }

    public void selectPatientPortalTab() {
        elementClick(patientTab);
    }

    public String getUsernamePlaceholder() {
        webdriverWait(10, userName);
        return getDomProperty(userName, "placeholder");
    }

    public void enterUsername(String user) {
        elementSendKeys(userName, user);
    }

    public void enterPassword(String password) {
        elementSendKeys(pass, password);
    }

    public void userNameAndPass(String user, String password)
            throws InterruptedException {

        elementSendKeys(userName, user);
        elementSendKeys(pass, password);
    }

    public void enterCredentialsAndPressEnter(String user, String password) {
        elementSendKeys(userName, user);
        sendKeysAndEnter(pass, password);
    }

    public void clickBtn() {
        webdriverWait(10, login_Btn);
        scrollDownAndScrollUp(false, login_Btn);
        elementClick(login_Btn);
    }

    public void clickPasswordVisibilityIcon() {
        elementClick(passwordEyeIcon);
    }

    public String getPasswordFieldType() {
        webdriverWait(10, pass);
        return getDomProperty(pass, "type");
    }

    public boolean isRememberDeviceSelected() {
        webdriverWait(10, userName);
        return selectedMethod(rememberDeviceCheckbox);
    }

    public void clickRememberDeviceCheckbox() {
        webdriverWait(10, userName);
        scrollDownAndScrollUp(false, rememberDeviceCheckbox);
        jsClick(rememberDeviceCheckbox);
    }

    public void clickForgotPasswordLink() {
        webdriverWait(10, forgotPasswordLink);
        scrollDownAndScrollUp(false, forgotPasswordLink);
        elementClick(forgotPasswordLink);
    }

    public void clickInitializeAccountLink() {
        webdriverWait(10, initializeAccountLink);
        scrollDownAndScrollUp(false, initializeAccountLink);
        elementClick(initializeAccountLink);
    }

    public String getErrorAlertMessage() {
        webdriverWait(25, errorAlert);
        return elementGetText(errorAlert);
    }

    public void closeErrorAlert() {
        webdriverWait(10, errorAlertCloseBtn);
        elementClick(errorAlertCloseBtn);
    }

    public boolean isErrorAlertDisplayed() {
        List<WebElement> alerts = driver.findElements(By.xpath(ERROR_ALERT));
        for (WebElement alert : alerts) {
            if (displayedMethod(alert)) {
                return true;
            }
        }
        return false;
    }

    public int getVisibleFieldValidationErrorCount() {
        List<WebElement> errors = driver.findElements(By.xpath(FIELD_VALIDATION_ERROR));
        int count = 0;
        for (WebElement err : errors) {
            if (displayedMethod(err) && !err.getText().trim().isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public String getUsernameRequiredError() {
        webdriverWait(10, usernameRequiredError);
        return elementGetText(usernameRequiredError);
    }

    public String getPasswordRequiredError() {
        webdriverWait(10, passwordRequiredError);
        return elementGetText(passwordRequiredError);
    }

    public boolean isLoginPageDisplayed() {
        webdriverWait(10, login_Btn);
        return getApplnUrl().contains("/login") && displayedMethod(login_Btn);
    }

    public String suicessMessage() {
        webdriverWait(20, desnoardMessage1);
        return elementGetText(desnoardMessage1);
    }
}
